package com.quickmanager.controller;

import com.quickmanager.debug.Alerts;
import com.quickmanager.model.DanhMuc;
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
    // DSSP
    @FXML private ComboBox<DanhMuc> cbDanhMuc;
    @FXML private ComboBox<String> cbTrangThai;
    @FXML private TextField txtTuKhoa;
    @FXML private TableView<SanPham> tbSanPham;
    @FXML private TableColumn<SanPham, String> colMaSP;
    @FXML private TableColumn<SanPham, String> colTenSP;
    @FXML private TableColumn<SanPham, String> colDanhMuc;
    @FXML private TableColumn<SanPham, Integer> colTonKho;
    @FXML private TableColumn<SanPham, BigDecimal> colGiaNhap;
    @FXML private TableColumn<SanPham, BigDecimal> colGiaBan;
    @FXML private TableColumn<SanPham, String> colTrangThai;
    private List<SanPham> mangSanPham = new ArrayList<>();
    @FXML private Label lblTongSoSP;

    // TTSP
    @FXML private TextField txtFormMaSP;
    @FXML private TextField txtFormTenSP;
    @FXML private TextField txtFormDVT;
    @FXML private TextField txtFormTonKho;
    @FXML private TextField txtFormGiaNhap;
    @FXML private TextField txtFormGiaBan;
    @FXML private TextField txtFormTrangThai;
    @FXML private ComboBox<DanhMuc> cbFormDanhMuc;
    @FXML private ComboBox<String> cbFormTrangThai;

    public void initialize() {
        initDSSP();
        tbSanPham.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> handleSelectTB());

        if (txtTuKhoa != null) {
            txtTuKhoa.textProperty().addListener((observable, oldValue, newValue) -> handleTimKiem());
        }
        if (cbDanhMuc != null) {
            cbDanhMuc.valueProperty().addListener((observable, oldValue, newValue) -> handleTimKiem());
        }
        if (cbTrangThai != null) {
            cbTrangThai.valueProperty().addListener((observable, oldValue, newValue) -> handleTimKiem());
        }

        if (tbSanPham != null) {
            tbSanPham.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    javafx.application.Platform.runLater(() -> {
                        loadDanhMuc();
                        loadTrangThai();
                        handleTimKiem();
                    });
                }
            });
        } else {
            loadDanhMuc();
            loadTrangThai();
            handleTimKiem();
        }
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
        cbDanhMuc.getItems().setAll(FXCollections.observableArrayList(ProductService.getDanhMuc()));
        cbDanhMuc.getItems().addFirst(null);
        cbFormDanhMuc.getItems().setAll(FXCollections.observableArrayList(ProductService.getDanhMuc()));
        cbFormDanhMuc.getItems().addFirst(null);
    }

    public void loadTrangThai() {
        cbTrangThai.getItems().setAll("Đang bán", "Ngừng kinh doanh", "Hết hạn", "Hết hàng");
        cbTrangThai.getItems().addFirst(null);
        cbFormTrangThai.getItems().setAll("Đang bán", "Ngừng kinh doanh", "Hết hạn", "Hết hàng");
        cbFormTrangThai.getItems().addFirst(null);
    }

    public void loadDSSP() {
        tbSanPham.setItems(FXCollections.observableArrayList(mangSanPham));
    }

    public void handleTimKiem() {
        String keyword = txtTuKhoa.getText();
        DanhMuc dm = cbDanhMuc.getValue();
        String danhMuc = (dm == null) ? null : dm.getTenDanhMuc();
        String trangThai = cbTrangThai.getValue();
        mangSanPham = ProductService.getProduct(keyword, danhMuc, trangThai, false);
        loadDSSP();
        lblTongSoSP.setText("Tổng số sản phẩm: " + mangSanPham.size());
    }

    public void handleSelectTB() {
        SanPham sp = tbSanPham.getSelectionModel().getSelectedItem();
        if (sp != null) {
            txtFormMaSP.setText(String.valueOf(sp.getMaSanPham()));
            txtFormTenSP.setText(sp.getTenSanPham());
            txtFormDVT.setText(sp.getDonViTinh());
            txtFormTonKho.setText(String.valueOf(sp.getSoLuongTon()));
            txtFormGiaNhap.setText(String.valueOf(sp.getGiaNhap()));
            txtFormGiaBan.setText(String.valueOf(sp.getGiaBan()));
            for (DanhMuc dm : cbFormDanhMuc.getItems()) {
                if (dm != null && dm.getMaDanhMuc() == sp.getMaDanhMuc()) {
                    cbFormDanhMuc.getSelectionModel().select(dm);
                    break;
                }
            }
            for (String i : cbFormTrangThai.getItems()) {
                if (i != null && i.equals(sp.getTrangThai())) {
                    cbFormTrangThai.getSelectionModel().select(i);
                    break;
                }
            }
        } else {
            txtFormDVT.clear();
            txtFormGiaBan.clear();
            txtFormGiaNhap.clear();
            txtFormMaSP.clear();
            txtFormTenSP.clear();
            txtFormTonKho.clear();
            cbFormDanhMuc.getSelectionModel().clearSelection();
            cbFormTrangThai.getSelectionModel().clearSelection();
        }
    }

    public void handleCapNhat() {
        SanPham sp = tbSanPham.getSelectionModel().getSelectedItem();
        if (sp == null) {
            Alerts.thongBao("Vui lòng chọn sản phẩm để cập nhật", "");
            return;
        }

        try {
            sp.setTenSanPham(txtFormTenSP.getText().trim());
            sp.setDonViTinh(txtFormDVT.getText().trim());
            sp.setGiaNhap(new BigDecimal(txtFormGiaNhap.getText().trim()));
            sp.setGiaBan(new BigDecimal(txtFormGiaBan.getText().trim()));
            sp.setSoLuongTon(Integer.parseInt(txtFormTonKho.getText().trim()));
            sp.setTrangThai(cbFormTrangThai.getValue() == null ? "" : cbFormTrangThai.getValue());

            DanhMuc dmForm = cbFormDanhMuc.getSelectionModel().getSelectedItem();
            if (dmForm == null) {
                Alerts.thongBao("Vui lòng chọn danh mục sản phẩm", "");
                return;
            }
            sp.setMaDanhMuc(dmForm.getMaDanhMuc());

            if (ProductService.updateProduct(sp)) {
                Alerts.thongBao("Cập nhật sản phẩm thành công!", "");
                handleTimKiem();
                tbSanPham.getSelectionModel().select(sp);
            } else {
                Alerts.thongBao("Cập nhật sản phẩm thất bại!", "");
            }
        } catch (NumberFormatException e) {
            Alerts.thongBao("Vui lòng nhập giá và số lượng hợp lệ!", "");
        } catch (Exception e) {
            Alerts.thongBao("Cập nhật sản phẩm thất bại: " + e.getMessage(), "");
        }
    }
}
