package com.quickmanager.controller;

import com.quickmanager.debug.Alerts;
import com.quickmanager.model.CT_PhieuNhap;
import com.quickmanager.model.SanPham;
import com.quickmanager.service.ProductService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    public void initialize() {
        loadTbMHDT();
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
        }
    }

    public void xoaHet() {
        if (mangMHDT.isEmpty()) {
            Alerts.thongBao("Không có sản phẩm nào trong phiếu nhập để xóa","");
        }else {
            mangMHDT.clear();
            loadTbMHDT();
        }
    }


}
