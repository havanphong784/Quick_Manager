package com.quickmanager.service;

import com.quickmanager.config.MailConfig;
import com.quickmanager.debug.AppLogger;
import com.quickmanager.model.CT_HoaDon;
import com.quickmanager.model.KhachHang;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailService {
    private static final Logger logger = AppLogger.getLogger(EmailService.class);
    private static final Locale DEFAULT_LOCALE = Locale.forLanguageTag("vi-VN");

    private EmailService() {
    }

    public static boolean sendInvoicePaidEmail(
            KhachHang khachHang,
            int maHoaDon,
            BigDecimal tongTien,
            BigDecimal giamGia,
            BigDecimal tienKhachDua,
            BigDecimal tienThoi,
            List<CT_HoaDon> chiTietHoaDon
    ) {
        if (!MailConfig.isEnabled()) {
            return false;
        }

        if (khachHang == null || khachHang.getEmail() == null || khachHang.getEmail().isBlank()) {
            return false;
        }

        String username = MailConfig.getUsername();
        String password = MailConfig.getPassword();
        String host = MailConfig.getHost();
        if (host.isBlank()) {
            logger.info("Mail host chua duoc cau hinh.");
            return false;
        }
        if (MailConfig.isAuth() && (username == null || username.isBlank() || password == null || password.isBlank())) {
            logger.info("Thieu thong tin dang nhap mail. Hay dung mail.auth.code hoac mail.username/mail.password.");
            return false;
        }

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", String.valueOf(MailConfig.getPort()));
            props.put("mail.smtp.auth", String.valueOf(MailConfig.isAuth()));
            props.put("mail.smtp.starttls.enable", String.valueOf(MailConfig.isStartTls()));

            Object session = createSession(props);
            Object message = createMessage(session);

            String fromAddress = MailConfig.getFromAddress();
            if (fromAddress == null || fromAddress.isBlank()) {
                fromAddress = username;
            }

            setFrom(message, fromAddress, MailConfig.getFromName());
            setTo(message, khachHang.getEmail());
            setSubject(message, "[Quick Manager] Hoa don #" + maHoaDon + " da thanh toan");
            setBody(message, buildInvoiceText(khachHang, maHoaDon, tongTien, giamGia, tienKhachDua, tienThoi, chiTietHoaDon));

            send(session, message, host, username, password);
            return true;
        } catch (ClassNotFoundException e) {
            logger.info("Thieu thu vien jakarta.mail. Vui long dong bo Maven de gui mail.");
            return false;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Gui mail that bai", e);
            return false;
        }
    }

    private static Object createSession(Properties props) throws Exception {
        Class<?> sessionClass = Class.forName("jakarta.mail.Session");
        Method getInstance = sessionClass.getMethod("getInstance", Properties.class);
        return getInstance.invoke(null, props);
    }

    private static Object createMessage(Object session) throws Exception {
        Class<?> sessionClass = Class.forName("jakarta.mail.Session");
        Class<?> mimeMessageClass = Class.forName("jakarta.mail.internet.MimeMessage");
        Constructor<?> ctor = mimeMessageClass.getConstructor(sessionClass);
        return ctor.newInstance(session);
    }

    private static void setFrom(Object message, String fromAddress, String fromName) throws Exception {
        Class<?> internetAddressClass = Class.forName("jakarta.mail.internet.InternetAddress");
        Constructor<?> ctor = internetAddressClass.getConstructor(String.class, String.class);
        Object from = ctor.newInstance(fromAddress, fromName);
        Method setFrom = message.getClass().getMethod("setFrom", Class.forName("jakarta.mail.Address"));
        setFrom.invoke(message, from);
    }

    private static void setTo(Object message, String recipientEmail) throws Exception {
        Class<?> internetAddressClass = Class.forName("jakarta.mail.internet.InternetAddress");
        Method parse = internetAddressClass.getMethod("parse", String.class);
        Object addresses = parse.invoke(null, recipientEmail);

        Class<?> recipientTypeClass = Class.forName("jakarta.mail.Message$RecipientType");
        Field toField = recipientTypeClass.getField("TO");
        Object toType = toField.get(null);

        Method setRecipients = message.getClass().getMethod(
                "setRecipients",
                recipientTypeClass,
                Class.forName("[Ljakarta.mail.Address;")
        );
        setRecipients.invoke(message, toType, addresses);
    }

    private static void setSubject(Object message, String subject) throws Exception {
        Method setSubject = message.getClass().getMethod("setSubject", String.class, String.class);
        setSubject.invoke(message, subject, "UTF-8");
    }

    private static void setBody(Object message, String body) throws Exception {
        Method setText = message.getClass().getMethod("setText", String.class, String.class);
        setText.invoke(message, body, "UTF-8");
    }

    private static void send(Object session, Object message, String host, String username, String password) throws Exception {
        if (MailConfig.isAuth()) {
            Method getTransport = session.getClass().getMethod("getTransport", String.class);
            Object transport = getTransport.invoke(session, "smtp");
            try {
                Method connect = transport.getClass().getMethod("connect", String.class, int.class, String.class, String.class);
                connect.invoke(transport, host, MailConfig.getPort(), username, password);

                Method getAllRecipients = message.getClass().getMethod("getAllRecipients");
                Object recipients = getAllRecipients.invoke(message);

                Method sendMessage = transport.getClass().getMethod("sendMessage", Class.forName("jakarta.mail.Message"), Class.forName("[Ljakarta.mail.Address;"));
                sendMessage.invoke(transport, message, recipients);
            } finally {
                Method close = transport.getClass().getMethod("close");
                close.invoke(transport);
            }
        } else {
            Class<?> transportClass = Class.forName("jakarta.mail.Transport");
            Method send = transportClass.getMethod("send", Class.forName("jakarta.mail.Message"));
            send.invoke(null, message);
        }
    }

    private static String buildInvoiceText(
            KhachHang khachHang,
            int maHoaDon,
            BigDecimal tongTien,
            BigDecimal giamGia,
            BigDecimal tienKhachDua,
            BigDecimal tienThoi,
            List<CT_HoaDon> chiTietHoaDon
    ) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(DEFAULT_LOCALE);
        StringBuilder sb = new StringBuilder();
        sb.append("Xin chao ").append(khachHang.getTenKhachHang()).append(",\n\n");
        sb.append("Cam on quy khach da thanh toan hoa don tai Quick Manager.\n");
        sb.append("Ma hoa don: ").append(maHoaDon).append("\n\n");

        sb.append("Chi tiet san pham:\n");
        if (chiTietHoaDon != null && !chiTietHoaDon.isEmpty()) {
            for (CT_HoaDon item : chiTietHoaDon) {
                sb.append("- ")
                        .append(item.getTenSanPham())
                        .append(" x")
                        .append(item.getSoLuong())
                        .append(" = ")
                        .append(currencyFormatter.format(item.getThanhTien()))
                        .append("\n");
            }
        } else {
            sb.append("(Khong co du lieu chi tiet san pham)\n");
        }

        sb.append("\nTong ket thanh toan:\n");
        sb.append("- Giam gia: ").append(currencyFormatter.format(safe(giamGia))).append("\n");
        sb.append("- Tong tien: ").append(currencyFormatter.format(safe(tongTien))).append("\n");
        sb.append("- Tien khach dua: ").append(currencyFormatter.format(safe(tienKhachDua))).append("\n");
        sb.append("- Tien thoi: ").append(currencyFormatter.format(safe(tienThoi))).append("\n\n");
        sb.append("Tran trong,\nQuick Manager");
        return sb.toString();
    }

    private static BigDecimal safe(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }
}

