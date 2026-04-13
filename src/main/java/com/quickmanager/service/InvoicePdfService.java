package com.quickmanager.service;

import com.quickmanager.model.CT_HoaDon;
import com.quickmanager.model.HoaDon;
import com.quickmanager.model.KhachHang;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class InvoicePdfService {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final NumberFormat CURRENCY = NumberFormat.getNumberInstance(Locale.forLanguageTag("vi-VN"));

    private InvoicePdfService() {
    }

    public static void exportInvoicePdf(File outFile, HoaDon hoaDon, List<CT_HoaDon> chiTiet, KhachHang khachHang)
            throws IOException {
        List<String> lines = buildInvoiceLines(hoaDon, chiTiet, khachHang);
        BufferedImage image = renderInvoiceImage(lines);
        byte[] jpegBytes = toJpeg(image);
        byte[] pdfBytes = buildPdfFromJpeg(jpegBytes, image.getWidth(), image.getHeight());
        Files.write(outFile.toPath(), pdfBytes);
    }

    private static List<String> buildInvoiceLines(HoaDon hoaDon, List<CT_HoaDon> chiTiet, KhachHang khachHang) {
        List<String> lines = new ArrayList<>();
        String ngayLap = hoaDon.getNgayLap() == null ? "" : hoaDon.getNgayLap().format(DATE_FORMAT);

        lines.add("HOA DON BAN HANG");
        lines.add("");
        lines.add("Ma hoa don: " + hoaDon.getMaHoaDon());
        lines.add("Ngay lap: " + ngayLap);
        lines.add("Khach hang: " + getCustomerName(hoaDon, khachHang));
        lines.add("So dien thoai: " + getValue(khachHang == null ? null : khachHang.getSoDienThoai()));
        lines.add("Dia chi: " + getValue(khachHang == null ? null : khachHang.getDiaChi()));
        lines.add("");
        lines.add(String.format("%-4s %-28s %6s %12s %12s", "STT", "San pham", "SL", "Don gia", "Thanh tien"));
        lines.add("------------------------------------------------------------------------");

        int index = 1;
        for (CT_HoaDon item : chiTiet) {
            lines.add(String.format(
                    "%-4s %-28s %6s %12s %12s",
                    index++,
                    truncate(getValue(item.getTenSanPham()), 28),
                    item.getSoLuong(),
                    compactMoney(item.getDonGia()),
                    compactMoney(item.getThanhTien())
            ));
        }

        lines.add("");
        lines.add("Tam tinh: " + formatMoney(addMoney(hoaDon.getTongTien(), hoaDon.getGiamGia())));
        lines.add("Giam gia: " + formatMoney(hoaDon.getGiamGia()));
        lines.add("Tong cong: " + formatMoney(hoaDon.getTongTien()));
        return lines;
    }

    private static BufferedImage renderInvoiceImage(List<String> lines) {
        Font textFont = new Font("Arial", Font.PLAIN, 16);
        int margin = 28;
        int width = 1240;

        BufferedImage probe = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        Graphics2D pg = probe.createGraphics();
        pg.setFont(textFont);
        FontMetrics fm = pg.getFontMetrics();
        int lineHeight = fm.getHeight() + 6;
        pg.dispose();

        int height = margin * 2 + Math.max(1, lines.size()) * lineHeight;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.BLACK);
        g.setFont(textFont);
        int y = margin + fm.getAscent();
        for (String line : lines) {
            g.drawString(line, margin, y);
            y += lineHeight;
        }
        g.dispose();
        return image;
    }

    private static byte[] toJpeg(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return baos.toByteArray();
    }

    private static byte[] buildPdfFromJpeg(byte[] jpegBytes, int imageWidthPx, int imageHeightPx) throws IOException {
        float pageWidth = 595f;
        float pageHeight = 842f;
        float margin = 20f;
        float maxW = pageWidth - margin * 2;
        float maxH = pageHeight - margin * 2;

        float scale = Math.min(maxW / imageWidthPx, maxH / imageHeightPx);
        float drawW = imageWidthPx * scale;
        float drawH = imageHeightPx * scale;
        float drawX = margin;
        float drawY = pageHeight - margin - drawH;

        String content = "q\n" +
                drawW + " 0 0 " + drawH + " " + drawX + " " + drawY + " cm\n" +
                "/Im0 Do\nQ\n";
        byte[] contentBytes = content.getBytes(StandardCharsets.US_ASCII);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        List<Integer> offsets = new ArrayList<>();
        offsets.add(0);

        writeAscii(out, "%PDF-1.4\n");

        offsets.add(out.size());
        writeAscii(out, "1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n");

        offsets.add(out.size());
        writeAscii(out, "2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n");

        offsets.add(out.size());
        writeAscii(out,
                "3 0 obj\n<< /Type /Page /Parent 2 0 R /MediaBox [0 0 595 842] /Resources << /XObject << /Im0 5 0 R >> >> /Contents 4 0 R >>\nendobj\n");

        offsets.add(out.size());
        writeAscii(out, "4 0 obj\n<< /Length " + contentBytes.length + " >>\nstream\n");
        out.write(contentBytes);
        writeAscii(out, "endstream\nendobj\n");

        offsets.add(out.size());
        writeAscii(out,
                "5 0 obj\n<< /Type /XObject /Subtype /Image /Width " + imageWidthPx +
                        " /Height " + imageHeightPx +
                        " /ColorSpace /DeviceRGB /BitsPerComponent 8 /Filter /DCTDecode /Length " + jpegBytes.length +
                        " >>\nstream\n");
        out.write(jpegBytes);
        writeAscii(out, "\nendstream\nendobj\n");

        int startXref = out.size();
        writeAscii(out, "xref\n0 6\n");
        writeAscii(out, "0000000000 65535 f \n");
        for (int i = 1; i <= 5; i++) {
            writeAscii(out, String.format("%010d 00000 n \n", offsets.get(i)));
        }
        writeAscii(out, "trailer\n<< /Size 6 /Root 1 0 R >>\n");
        writeAscii(out, "startxref\n" + startXref + "\n%%EOF");

        return out.toByteArray();
    }

    private static void writeAscii(ByteArrayOutputStream out, String text) throws IOException {
        out.write(text.getBytes(StandardCharsets.US_ASCII));
    }

    private static String getCustomerName(HoaDon hoaDon, KhachHang khachHang) {
        if (khachHang != null && khachHang.getTenKhachHang() != null && !khachHang.getTenKhachHang().isBlank()) {
            return khachHang.getTenKhachHang();
        }
        return getValue(hoaDon.getTenKhachHang());
    }

    private static String getValue(String value) {
        if (value == null || value.isBlank()) {
            return "Khach le";
        }
        return value;
    }

    private static String formatMoney(BigDecimal amount) {
        BigDecimal safeAmount = amount == null ? BigDecimal.ZERO : amount;
        return CURRENCY.format(safeAmount) + " VND";
    }

    private static String compactMoney(BigDecimal amount) {
        BigDecimal safeAmount = amount == null ? BigDecimal.ZERO : amount;
        return CURRENCY.format(safeAmount);
    }

    private static String truncate(String value, int maxLength) {
        if (value == null) {
            return "";
        }
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, Math.max(0, maxLength - 3)) + "...";
    }


    private static BigDecimal addMoney(BigDecimal first, BigDecimal second) {
        BigDecimal a = first == null ? BigDecimal.ZERO : first;
        BigDecimal b = second == null ? BigDecimal.ZERO : second;
        return a.add(b);
    }
}



