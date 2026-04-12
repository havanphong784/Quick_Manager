package com.quickmanager.controller;

import com.quickmanager.debug.Address;
import com.quickmanager.debug.Alerts;
import com.quickmanager.model.CT_PhieuNhap;
import com.quickmanager.model.NhaCungCap;
import com.quickmanager.model.PhieuNhap;
import com.quickmanager.model.SanPham;
import com.quickmanager.service.ProductService;
import com.quickmanager.service.SupplierService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import com.quickmanager.debug.BarcodeScanner;

public class ImportController {

    //MH
    @FXML private TextField txtTimSanPham;
    @FXML private TextField txtSoLuongNhap;
    @FXML private Button btnThemMH;
    @FXML private TableView<SanPham> tbSanPham;
    @FXML private TableColumn<SanPham,Integer> clMaSP;
    @FXML private TableColumn<SanPham,String> clTenSP;
    @FXML private TableColumn<SanPham,String> clDVT;
    @FXML private TableColumn<SanPham,Integer> clTonKho;
    @FXML private TableColumn<SanPham, BigDecimal> clGiaNhap;
    private List<SanPham> mangMH;


    // MHDT
    @FXML private TableView<CT_PhieuNhap> tbSanPhamDT;
    @FXML private TableColumn<CT_PhieuNhap,Integer> colMaSP;
    @FXML private TableColumn<CT_PhieuNhap,BigDecimal> colThanhTien;
    @FXML private TableColumn<CT_PhieuNhap, BigDecimal> colGiaNhap;
    @FXML private TableColumn<CT_PhieuNhap, BigDecimal> colSLNhap;
    private List<CT_PhieuNhap> mangMHDT = new ArrayList<>();
    @FXML private Label lblTongTien;

    // TT
    @FXML private Button btnXacNhan;
    @FXML private ComboBox<NhaCungCap> cbNCC;
    @FXML private TextField txtDiaChiNCC;
    @FXML private TextField txtLienHeNCC;
    @FXML private TextField txtTenNCC;
    @FXML private TextField txtEmail;
    @FXML private Label lblTongCong;

    // Create product quick form
    @FXML private TextField txtNewTenSP;
    @FXML private ComboBox<com.quickmanager.model.DanhMuc> cbNewDanhMuc;
    @FXML private TextField txtNewDVT;
    @FXML private TextField txtNewGiaNhap;
    @FXML private TextField txtNewGiaBan;
    @FXML private TextField txtNewSoLuong;
    @FXML private TextField txtNewBarcode;
    @FXML private TextField txtNewMucToiThieu;
    @FXML private Button btnCreateProduct;

    public void initialize() {
        loadTbMHDT();
        loadCbNCC();
        loadNewProductForm();
    };

    public void loadNewProductForm() {
        cbNewDanhMuc.getItems().setAll(ProductService.getDanhMuc());
        cbNewDanhMuc.getItems().addFirst(null);
    }

    public void handleCreateProduct() {
        String ten = txtNewTenSP.getText().trim();
        com.quickmanager.model.DanhMuc dm = cbNewDanhMuc.getValue();
        String dvt = txtNewDVT.getText().trim();
        String sGiaNhap = txtNewGiaNhap.getText().trim();
        String sGiaBan = txtNewGiaBan.getText().trim();
        String sSoLuong = txtNewSoLuong.getText().trim();
        String barcode = txtNewBarcode.getText().trim();
        String sMucToiThieu = txtNewMucToiThieu.getText().trim();

        if (ten.isEmpty()) { Alerts.thongBao("Tên sản phẩm không được để trống", ""); return; }
        if (dm == null) { Alerts.thongBao("Vui lòng chọn danh mục", ""); return; }
        try {
            java.math.BigDecimal giaNhap = sGiaNhap.isEmpty() ? java.math.BigDecimal.ZERO : new java.math.BigDecimal(sGiaNhap);
            java.math.BigDecimal giaBan = sGiaBan.isEmpty() ? java.math.BigDecimal.ZERO : new java.math.BigDecimal(sGiaBan);
            int soLuong = sSoLuong.isEmpty() ? 0 : Integer.parseInt(sSoLuong);
            int mucToiThieu = sMucToiThieu.isEmpty() ? 0 : Integer.parseInt(sMucToiThieu);

            com.quickmanager.model.SanPham sp = com.quickmanager.service.ProductService.createProduct(ten, dm.getMaDanhMuc(), giaNhap, giaBan, soLuong, dvt, barcode, mucToiThieu);
            if (sp != null) {
                Alerts.thongBao("Tạo sản phẩm thành công", "Mã: " + sp.getMaSanPham());
                // refresh product search results
                txtTimSanPham.setText(sp.getTenSanPham());
                loadTbMH();
                // select created product in table
                for (int i = 0; i < mangMH.size(); i++) {
                    if (mangMH.get(i).getMaSanPham() == sp.getMaSanPham()) {
                        tbSanPham.getSelectionModel().select(i);
                        break;
                    }
                }
            } else {
                Alerts.thongBao("Tạo sản phẩm thất bại", "");
            }
        } catch (NumberFormatException e) {
            Alerts.thongBao("Vui lòng nhập số hợp lệ cho giá/số lượng", "");
        } catch (Exception e) {
            Alerts.thongBao("Lỗi khi tạo sản phẩm: " + e.getMessage(), "");
            Address.printAddress();
        }
    }

    @FXML private Button btnScanBarcodeImport;

    public void handleScanBarcodeImport() {
        btnScanBarcodeImport.setDisable(true);
        BarcodeScanner.scan(code -> {
            Platform.runLater(() -> {
                try {
                    // Try to find existing product by barcode
                    com.quickmanager.model.SanPham found = ProductService.getByBarcode(code);
                    if (found != null) {
                        // populate create form with existing product details for quick edit/view
                        txtNewTenSP.setText(found.getTenSanPham());
                        // select category
                        if (found.getMaDanhMuc() != 0) {
                            for (com.quickmanager.model.DanhMuc dm : cbNewDanhMuc.getItems()) {
                                if (dm != null && dm.getMaDanhMuc() == found.getMaDanhMuc()) {
                                    cbNewDanhMuc.getSelectionModel().select(dm);
                                    break;
                                }
                            }
                        }
                        txtNewDVT.setText(found.getDonViTinh());
                        txtNewGiaNhap.setText(found.getGiaNhap() == null ? "0" : found.getGiaNhap().toString());
                        txtNewGiaBan.setText(found.getGiaBan() == null ? "0" : found.getGiaBan().toString());
                        txtNewSoLuong.setText(String.valueOf(found.getSoLuongTon()));
                        txtNewMucToiThieu.setText(String.valueOf(found.getMucToiThieu()));
                        txtNewBarcode.setText(found.getBarcode());
                        // also select in product list
                        txtTimSanPham.setText(found.getTenSanPham());
                        loadTbMH();
                        for (int i = 0; i < mangMH.size(); i++) {
                            if (mangMH.get(i).getMaSanPham() == found.getMaSanPham()) {
                                tbSanPham.getSelectionModel().select(i);
                                break;
                            }
                        }
                    } else {
                        // not found -> prefill barcode in create form and focus name
                        txtNewBarcode.setText(code);
                        txtNewTenSP.requestFocus();
                    }
                } finally {
                    btnScanBarcodeImport.setDisable(false);
                }
            });
        });
    }

    // MH
    public void loadTbMH() {
        clMaSP.setCellValueFactory(new PropertyValueFactory<>("maSanPham"));
        clTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
        clDVT.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
        clTonKho.setCellValueFactory(new PropertyValueFactory<>("soLuongTon"));
        clGiaNhap.setCellValueFactory(new PropertyValueFactory<>("giaNhap"));
        mangMH = ProductService.searchProducts(txtTimSanPham.getText().trim());
        tbSanPham.setItems(FXCollections.observableArrayList(mangMH));
    }

    public void handleThem() {
        SanPham sp = tbSanPham.getSelectionModel().getSelectedItem();
        // Fix: kiểm tra sp null trước
        if (sp == null) {
            Alerts.thongBao("Vui lòng chọn sản phẩm để thêm vào phiếu nhập","");
            return;
        }
        int sl;
        try {
            sl = Integer.parseInt(txtSoLuongNhap.getText().trim());
            if (sl <= 0) throw new NumberFormatException();
        }catch (NumberFormatException e) {
            Alerts.thongBao("Vui lòng nhập số lượng hợp lệ (số nguyên dương)","");
            return;
        }
        for (CT_PhieuNhap ct : mangMHDT) {
            if (ct.getMaSanPham() == sp.getMaSanPham()) {
                Alerts.thongBao("Sản phẩm đã có trong phiếu nhập.","");
                return;
            }
        }
        // Fix: giaNhap có thể null nếu chưa nhập
        BigDecimal giaNhap = sp.getGiaNhap() != null ? sp.getGiaNhap() : BigDecimal.ZERO;
        CT_PhieuNhap ctPH = new CT_PhieuNhap();
        ctPH.setMaSanPham(sp.getMaSanPham());
        ctPH.setGiaNhap(giaNhap);
        ctPH.setSoLuong(sl);
        ctPH.setThanhTien(giaNhap.multiply(BigDecimal.valueOf(sl)));
        mangMHDT.add(ctPH);
        loadTbMHDT();
        loadTongTien();
    }

    // MHDT
    public void loadTbMHDT() {
        colMaSP.setCellValueFactory(new PropertyValueFactory<>("maSanPham"));
        colSLNhap.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colGiaNhap.setCellValueFactory(new PropertyValueFactory<>("giaNhap"));
        colThanhTien.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));
        tbSanPhamDT.setItems(FXCollections.observableArrayList(mangMHDT));
    }

    public void xoaDong() {
        CT_PhieuNhap ctPH = tbSanPhamDT.getSelectionModel().getSelectedItem();
        if (ctPH == null) {
            Alerts.thongBao("Vui lòng chọn sản phẩm để xóa khỏi phiếu nhập","");
        }else {
            mangMHDT.removeIf(ct -> ct.getMaSanPham() == ctPH.getMaSanPham());
            loadTbMHDT();
            loadTongTien();
        }
    }

    public void xoaHet() {
        if (mangMHDT.isEmpty()) {
            Alerts.thongBao("Không có sản phẩm nào trong phiếu nhập để xóa","");
        }else {
            mangMHDT.clear();
            loadTbMHDT();
            loadTongTien();
        }
    }

    public void loadTongTien() {
        BigDecimal tongTien = BigDecimal.ZERO;
        for (CT_PhieuNhap ct : mangMHDT) {
            tongTien = tongTien.add(ct.getThanhTien());
        }
        lblTongTien.setText("Tổng tiền: " + tongTien.toString());
        lblTongCong.setText(tongTien.toString() + " VNĐ");
    }

    public void loadCbNCC() {
        List<NhaCungCap> dsNCC = SupplierService.getNCC();
        cbNCC.setItems(FXCollections.observableArrayList(dsNCC));
        cbNCC.getItems().addFirst(null);
    }

    public void setLockTF(Boolean look) {
        txtDiaChiNCC.setDisable(look);
        txtTenNCC.setDisable(look);
        txtLienHeNCC.setDisable(look);
        txtEmail.setDisable(look);
    }

    public void handleCbNCC() {
        NhaCungCap ncc = cbNCC.getSelectionModel().getSelectedItem();
        if (ncc == null) {
            txtDiaChiNCC.clear();
            txtTenNCC.clear();
            txtLienHeNCC.clear();
            txtEmail.clear();
            setLockTF(false);
        }else {
            txtLienHeNCC.setText(ncc.getSoDienThoai());
            txtDiaChiNCC.setText(ncc.getDiaChi());
            txtTenNCC.setText(ncc.getTenNCC());
            txtEmail.setText(ncc.getEmail());
            setLockTF(true);
        }
    }

    public void handleXacNhan() {
        // Fix: kiểm tra danh sách mặt hàng không rỗng
        if (mangMHDT.isEmpty()) {
            Alerts.thongBao("Phiếu nhập trống", "Vui lòng thêm ít nhất một mặt hàng vào phiếu nhập.");
            return;
        }

        NhaCungCap ncc = cbNCC.getSelectionModel().getSelectedItem();
        if (ncc == null) {
            String tenNCC = txtTenNCC.getText().trim();
            String sdt = txtLienHeNCC.getText().trim();
            String diaChi = txtDiaChiNCC.getText().trim();
            String email = txtEmail.getText().trim();
            if (!tenNCC.isEmpty() && !sdt.isEmpty() && !diaChi.isEmpty() && !email.isEmpty()) {
                try {
                    ncc = SupplierService.taoNCC(tenNCC, sdt, email, diaChi);
                    if (ncc == null) {
                        Alerts.thongBao("Tạo nhà cung cấp thất bại", "");
                        return;
                    }
                }catch (Exception e) {
                    Alerts.thongBao("Tạo nhà cung cấp thất bại", "");
                    return;
                }
            }else {
                Alerts.thongBao("Vui lòng nhập đầy đủ thông tin nhà cung cấp", "");
                return;
            }
        }

        // Fix: tính tổng tiền trực tiếp từ danh sách thay vì parse label
        BigDecimal tongTien = BigDecimal.ZERO;
        for (CT_PhieuNhap ct : mangMHDT) {
            tongTien = tongTien.add(ct.getThanhTien());
        }
        PhieuNhap pn = new PhieuNhap();
        pn.setMaNCC(ncc.getMaNCC());
        pn.setTongTien(tongTien);
        int maPhieuNhap;
        try {
            maPhieuNhap = SupplierService.taoPhieuNhap(pn, mangMHDT);
        }catch (Exception e) {
            Alerts.thongBao("Tạo phiếu nhập thất bại", "");
            Address.printAddress();
            return;
        }
        Alerts.thongBao("Tạo phiếu nhập thành công", "Mã phiếu nhập: " + maPhieuNhap);
        clearAll();
    }

    public void clearAll() {
        cbNCC.getSelectionModel().clearSelection();
        txtDiaChiNCC.clear();
        txtTenNCC.clear();
        txtLienHeNCC.clear();
        txtEmail.clear();
        setLockTF(false);
        mangMHDT.clear();
        loadTbMHDT();
        loadTongTien();
    }
}
