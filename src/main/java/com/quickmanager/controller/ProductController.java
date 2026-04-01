package com.quickmanager.controller;

import com.quickmanager.model.SanPham;
import com.quickmanager.service.ProductService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductController {
    //DSSP
    @FXML private Button btnTimKiem;
    @FXML private ComboBox<String> cbDanhMuc;
    @FXML private ComboBox<String> cbTrangThai;
    @FXML private TextField txtTuKhoa;
    @FXML private TableView<SanPham> tbSanPham;
    @FXML private TableColumn<SanPham, String> colMaSP;
    @FXML private TableColumn<SanPham,String> colTenSP;
    @FXML private TableColumn<SanPham,String> colDanhMuc;
    @FXML private TableColumn<SanPham, Integer> colTonKho;
    @FXML private TableColumn<SanPham, BigDecimal> colGiaNhap;
    @FXML private TableColumn<SanPham, BigDecimal> colGiaBan;
    @FXML private TableColumn<SanPham, String> colTrangThai;
    @FXML private List<SanPham> mangSanPham = new ArrayList<>();
    @FXML private Label lblTongSoSP;


    public void initialize() {
        initDSSP();
        handleTimKiem();
        loadDSSP();
        loadDanhMuc();
        loadTrangThai();
    }

    // DSSP
    public void initDSSP() {
        colMaSP.setCellValueFactory(new PropertyValueFactory<>("maSanPham"));
        colDanhMuc.setCellValueFactory(new PropertyValueFactory<>("tenDanhMuc"));
        colTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
        colTonKho.setCellValueFactory(new PropertyValueFactory<>("soLuongTon"));
        colGiaNhap.setCellValueFactory(new PropertyValueFactory<>("giaNhap"));
        colGiaBan.setCellValueFactory(new PropertyValueFactory<>("giaBan"));
        colTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
    }

    public void loadDanhMuc() {
        cbDanhMuc.getItems().setAll(ProductService.getDanhMuc());
        cbDanhMuc.getItems().addFirst(null);
    }

    public void loadTrangThai() {
        cbTrangThai.getItems().setAll("Đang bán", "Ngừng kinh doanh","Hết hạn","Hết hàng");
        cbTrangThai.getItems().addFirst(null);
    }

    public void loadDSSP() {
        tbSanPham.setItems(FXCollections.observableArrayList(mangSanPham));
    }

    public void handleTimKiem() {
        String keyword = txtTuKhoa.getText();
        String danhMuc = cbDanhMuc.getValue();
        String trangThai = cbTrangThai.getValue();
        mangSanPham = ProductService.getProduct(keyword, danhMuc, trangThai,false);
        loadDSSP();
        lblTongSoSP.setText("Tổng số sản phẩm: " + mangSanPham.size());
    }
}
