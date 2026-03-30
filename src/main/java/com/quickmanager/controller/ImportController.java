package com.quickmanager.controller;

import com.quickmanager.debug.Alerts;
import com.quickmanager.model.CT_PhieuNhap;
import com.quickmanager.model.NhaCungCap;
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

public class ImportController {

    //MH
    @FXML private TextField txtTimSanPham;
    @FXML private TextField txtSoLuongNhap;
    @FXML private TextField txtDonGiaNhap;
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

    public void initialize() {
        loadTbMHDT();
        loadCbNCC();
    };

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
        int sl;
        try {
            sl = Integer.parseInt(txtSoLuongNhap.getText().trim());
        }catch (NumberFormatException e) {
            Alerts.thongBao("Vui lòng nhập số lượng hợp lệ","");
            return;
        }
        if (sp != null) {
            CT_PhieuNhap ctPH = new CT_PhieuNhap();
            ctPH.setMaSanPham(sp.getMaSanPham());
            ctPH.setGiaNhap(sp.getGiaNhap());
            ctPH.setSoLuong(sl);
            ctPH.setThanhTien(sp.getGiaNhap().multiply(BigDecimal.valueOf(sl)));
            mangMHDT.add(ctPH);
        }else {
            Alerts.thongBao("Vui lòng chọn sản phẩm để thêm vào phiếu nhập","");
        }
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
}
