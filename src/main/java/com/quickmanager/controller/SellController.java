package com.quickmanager.controller;

import com.quickmanager.debug.AppLogger;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.quickmanager.debug.Address;
import com.quickmanager.debug.Alerts;
import com.quickmanager.model.*;
import com.quickmanager.service.CustomerService;
import com.quickmanager.service.EmailService;
import com.quickmanager.service.InvoiceService;
import com.quickmanager.service.ProductService;
import com.quickmanager.service.SessionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.application.Platform;
import com.quickmanager.debug.BarcodeScanner;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class SellController {
    private static final Logger logger = AppLogger.getLogger(SellController.class);
    private static final Pattern GMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@gmail\\.com$");

    // SP
    @FXML private TableView<SanPham> tbvSanPham;
    @FXML private TableColumn<SanPham, Integer> colMaSP;
    @FXML private TableColumn<SanPham, String> colTenSP;
    @FXML private TableColumn<SanPham, String> colDVT;
    @FXML private TableColumn<SanPham, Integer> colTonKho;
    @FXML private TableColumn<SanPham, BigDecimal> colGiaBan;
    @FXML private TextField txtTimSanPham;
    private final ObservableList<SanPham> dataSanPham = FXCollections.observableArrayList();

    // GH
    @FXML private TableView<GioHangItem> tbvGioHang;
    @FXML private TableColumn<GioHangItem, String> colSanPhamGH;
    @FXML private TableColumn<GioHangItem, Integer> colSoLuongGH;
    @FXML private TableColumn<GioHangItem, BigDecimal> colDonGiaGH;
    @FXML private TableColumn<GioHangItem, BigDecimal> colThanhTienGH;
    private final ObservableList<GioHangItem> dataGioHang = FXCollections.observableArrayList();
    private final List<GioHangItem> mangGioHang = new ArrayList<>();
    @FXML private TextField txtSoLuongNhanh;

    // DM
    @FXML private ComboBox<DanhMuc> cbDanhMuc;
    private static String stringDanhMuc= "";

    // KH
    @FXML private ComboBox<KhachHang> cbKhachHang;
    @FXML private TextField txtKhachDua;
    @FXML private TextField txtSDT;
    @FXML private TextField txtGmail;
    @FXML private TextField txtKhachHang;
    @FXML private TextField txtGiamGia;
    @FXML private Label lblTongTien;
    @FXML private Label lblTamTinh;
    @FXML private Label lblTienThoi;

    // TT
    @FXML private Button btnThanhToan;

    // Khỏi tạo
    @FXML
    public void initialize() {
        initTable();
        initTableGH();
        loadDanhMuc();
        tbvSanPham.setItems(dataSanPham);
        loadSanPham();
        initKhachHang();
        btnThanhToan.setDisable(true);
    }

    @FXML private Button btnScanBarcodeSell;

    public void handleScanBarcodeSell() {
        if (btnScanBarcodeSell != null) btnScanBarcodeSell.setDisable(true);
        BarcodeScanner.scan(code -> {
            Platform.runLater(() -> {
                try {
                    txtTimSanPham.setText(code);
                    handleSearch();
                    if (!dataSanPham.isEmpty()) {
                        tbvSanPham.getSelectionModel().select(0);
                        txtSoLuongNhanh.setText("1");
                    }
                } finally {
                    if (btnScanBarcodeSell != null) btnScanBarcodeSell.setDisable(false);
                }
            });
        });
    }

    // SP
    public void initTable() {
        colMaSP.setCellValueFactory(new PropertyValueFactory<>("maSanPham"));
        colTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
        colDVT.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
        colTonKho.setCellValueFactory(new PropertyValueFactory<>("soLuongTon"));
        colGiaBan.setCellValueFactory(new PropertyValueFactory<>("giaBan"));
    }

    public void loadSanPham() {
        dataSanPham.setAll(ProductService.getProduct(txtTimSanPham.getText(),stringDanhMuc,"Đang bán",true));
    }

    public void handleSearch() {
        loadSanPham();
    }

    // DM
    public void loadDanhMuc() {
        cbDanhMuc.getItems().setAll(ProductService.getDanhMuc());
        cbDanhMuc.getItems().addFirst(null);
    }

    public void handleSelectDM() {
        DanhMuc selected = cbDanhMuc.getValue();
        stringDanhMuc = (selected == null || selected.getTenDanhMuc() == null) ? "" : selected.getTenDanhMuc();
        loadSanPham();
    }

    // GH
    public void initTableGH() {
        colSanPhamGH.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
        colDonGiaGH.setCellValueFactory(new PropertyValueFactory<>("giaBan"));
        colSoLuongGH.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colThanhTienGH.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));
        tbvGioHang.setItems(dataGioHang);
    }

    public void handleThemGio() {
        SanPham sp = tbvSanPham.getSelectionModel().getSelectedItem();
        if (sp ==  null) {
            Alerts.thongBao("Chưa chọn sản phẩm.","Vui lòng chọn sản phẩm cần thêm vào giỏ hàng.");
            return;
        }
        String stringSL = txtSoLuongNhanh.getText().trim();
        int sl;
        try {
            sl = Integer.parseInt(stringSL);
            if (sl > 0 && sp.getSoLuongTon() >= sl) {
                GioHangItem gh = new GioHangItem(sp.getMaSanPham(),sp.getTenSanPham(),sl,sp.getGiaBan());
                for (GioHangItem it : mangGioHang) {
                    if (gh.getMaSanPham() == it.getMaSanPham()) {
                        Alerts.thongBao("Đã tồn tại sản phẩm trong giỏ hàng.","Vui lòng chỉnh sửa số lượng trong giỏ hàng.");
                        return;
                    }
                }
                mangGioHang.add(gh);
                dataGioHang.setAll(mangGioHang);
                handleTinhTien();   // goi lai pt tinh tien
            }else {
                Alerts.thongBao("Không thể thêm sản phẩm.","Vượt quá số lượng tồn kho.");
            }
        }catch (NumberFormatException e) {
            Alerts.thongBao("Số lượng không hợp lệ.","Vui lòng nhập số nguyên dương.");
            return;
        }
    }

    public void handleXoaDong() {
        GioHangItem selected = tbvGioHang.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alerts.thongBao("Chưa chọn sản phẩm.","Vui lòng chọn sản phẩm cần xóa.");
        }else {
            mangGioHang.removeIf(it -> it.getMaSanPham() == selected.getMaSanPham());
            dataGioHang.setAll(mangGioHang);
            handleTinhTien();   // goi lai pt tinh tien
        }
    }

    public void handleXoaGio() {
        mangGioHang.clear();
        dataGioHang.setAll(mangGioHang);
        handleTinhTien();
    }


    // KH
    public void initKhachHang() {
        cbKhachHang.getItems().setAll(CustomerService.loadCustomer());
        cbKhachHang.getItems().addFirst(null);
    }

    public void handleSelectKH() {
            KhachHang kh = cbKhachHang.getValue();
            if (kh != null) {
                txtSDT.setText(kh.getSoDienThoai());
                txtSDT.setDisable(true);
                txtKhachHang.setText(kh.getTenKhachHang());
                txtKhachHang.setDisable(true);
                txtGmail.setText(kh.getEmail() == null ? "" : kh.getEmail());
                txtGmail.setDisable(true);
            }else {
                txtSDT.clear();
                txtSDT.setDisable(false);
                txtKhachHang.clear();
                txtKhachHang.setDisable(false);
                txtGmail.clear();
                txtGmail.setDisable(false);
            }
    }

    public void handleTinhTien() {
        BigDecimal tamTinh = BigDecimal.ZERO;
        for (GioHangItem it : mangGioHang) {
            tamTinh = tamTinh.add(it.getThanhTien());
        }
        lblTamTinh.setText(tamTinh.toString());

        BigDecimal giamGia;
        try {
            String strGiamGia = txtGiamGia.getText().trim();
            giamGia = (strGiamGia.isEmpty()) ? BigDecimal.ZERO : (new BigDecimal(strGiamGia));
        }catch (NumberFormatException e) {
            txtGiamGia.clear();
            giamGia = BigDecimal.ZERO;
        }
        if (giamGia.compareTo(tamTinh) > 0 || giamGia.compareTo(BigDecimal.ZERO) < 0) {
            txtGiamGia.clear();
            giamGia = BigDecimal.ZERO;
        }

        BigDecimal tongTien = tamTinh.subtract(giamGia);
        lblTongTien.setText(tongTien.toString());

        BigDecimal khachDua;
        try {
            String strKhachDua = txtKhachDua.getText().trim();
            khachDua = new BigDecimal(strKhachDua);
        }catch (NumberFormatException e) {
            txtKhachDua.clear();
            khachDua = BigDecimal.ZERO;
        }
        if (khachDua.compareTo(tongTien) >= 0) {
            BigDecimal tienThoi = khachDua.subtract(tongTien);
            lblTienThoi.setText(tienThoi.toString());
            btnThanhToan.setDisable(mangGioHang.isEmpty());
        }else {
            lblTienThoi.setText("Tiền khách đưa không đủ.");
            btnThanhToan.setDisable(true);
        }
    }


    // TT
    public void handleThanhToan() {
        btnThanhToan.setDisable(true);
        handleTinhTien();
        if (mangGioHang.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Giỏ hàng trống");
            alert.setContentText("Vui lòng thêm sản phẩm vào giỏ hàng trước khi thanh toán.");
            alert.showAndWait();
            btnThanhToan.setDisable(false);
            return;
        }

        // KH
        KhachHang kh = cbKhachHang.getValue();
        String emailKH = normalizeEmail(txtGmail.getText());
        if (!emailKH.isEmpty() && !isValidGmail(emailKH)) {
            Alerts.thongBao("Gmail khong hop le.","Vui long nhap dung dinh dang name@gmail.com.");
            btnThanhToan.setDisable(false);
            return;
        }
        if (kh == null) {
            String nameKH = txtKhachHang.getText().trim();
            String sdtKH = txtSDT.getText().trim();
            if (!nameKH.isEmpty() && !sdtKH.isEmpty()) {
                kh = CustomerService.themKhachHang(nameKH, sdtKH, emailKH.isEmpty() ? null : emailKH);
            }
        }

        // LHD
        List<CT_HoaDon> listHD = new LinkedList<>();
        BigDecimal tamTinh = BigDecimal.ZERO;
        for (GioHangItem item : dataGioHang) {
            CT_HoaDon a = new CT_HoaDon(item.getMaSanPham(), item.getSoLuong(), item.getGiaBan(), item.getThanhTien());
            tamTinh = tamTinh.add(item.getThanhTien());
            listHD.add(a);
        }
        String strGiamGia = txtGiamGia.getText().trim();
        BigDecimal giamGia = (strGiamGia.isEmpty()) ? BigDecimal.ZERO : (new BigDecimal(strGiamGia));
        BigDecimal tongTien = tamTinh.subtract(giamGia);
        String strKhachDua = txtKhachDua.getText().trim();
        BigDecimal khachDua = (strKhachDua.isEmpty()) ? BigDecimal.ZERO : new BigDecimal(strKhachDua);
        BigDecimal tienThoi = khachDua.subtract(tongTien);

        HoaDon hd = new HoaDon(SessionService.getUser().getMaNhanVien(),(kh == null) ? null :kh.getMaKhachHang(),tongTien,giamGia,khachDua,tienThoi);
        try {
            int maHD = InvoiceService.taoHoaDonNKH(hd, listHD);
            if (maHD >0) {
                KhachHang customerForEmail = resolveCustomerForEmail(kh, hd.getMaKhachHang());
                sendInvoiceEmailAsync(customerForEmail, maHD, tongTien, giamGia, khachDua, tienThoi);
                Alerts.thongBao("Thanh toán thành công.","Mã hóa đơn: " + maHD);
                reset();
                loadSanPham();
            }else {
                Alerts.thongBao("Lỗi thanh toán.","Không thể tạo hóa đơn. Vui lòng thử lại.");
            }
        }catch (Exception e) {
            Alerts.thongBao("Lỗi thanh toán.","Không thể tạo hóa đơn. Vui lòng thử lại.");
            Address.printAddress();
        }finally {
            btnThanhToan.setDisable(false);
        }
    }

    private void reset() {
        mangGioHang.clear();
        dataGioHang.clear();
        tbvGioHang.getSelectionModel().clearSelection();
        cbKhachHang.getSelectionModel().clearSelection();
        txtKhachHang.clear();
        txtKhachHang.setDisable(false);
        txtSDT.clear();
        txtSDT.setDisable(false);
        txtGmail.clear();
        txtGmail.setDisable(false);
        txtGiamGia.clear();
        txtKhachDua.clear();
        lblTamTinh.setText("0");
        lblTongTien.setText("0");
        lblTienThoi.setText("0");
        txtSoLuongNhanh.setText("1");
        tbvSanPham.getSelectionModel().clearSelection();
        btnThanhToan.setDisable(true);
    }

    private String normalizeEmail(String rawEmail) {
        if (rawEmail == null) {
            return "";
        }
        return rawEmail.trim().toLowerCase(Locale.ROOT);
    }

    private boolean isValidGmail(String email) {
        return GMAIL_PATTERN.matcher(email).matches();
    }

    private KhachHang resolveCustomerForEmail(KhachHang selectedCustomer, Integer maKhachHang) {
        if (selectedCustomer != null && selectedCustomer.getEmail() != null && !selectedCustomer.getEmail().isBlank()) {
            return selectedCustomer;
        }
        if (maKhachHang == null) {
            return selectedCustomer;
        }
        KhachHang fromDb = InvoiceService.getInfoKH(maKhachHang);
        return fromDb != null ? fromDb : selectedCustomer;
    }

    private void sendInvoiceEmailAsync(
            KhachHang khachHang,
            int maHoaDon,
            BigDecimal tongTien,
            BigDecimal giamGia,
            BigDecimal tienKhachDua,
            BigDecimal tienThoi
    ) {
        if (khachHang == null || khachHang.getEmail() == null || khachHang.getEmail().isBlank()) {
            return;
        }

        CompletableFuture.runAsync(() -> {
            List<CT_HoaDon> details = InvoiceService.getCTHD(maHoaDon);
            boolean sent = EmailService.sendInvoicePaidEmail(
                    khachHang,
                    maHoaDon,
                    tongTien,
                    giamGia,
                    tienKhachDua,
                    tienThoi,
                    details
            );
            if (!sent) {
                logger.info("Khong gui duoc email hoa don #" + maHoaDon);
            }
        });
    }


}
