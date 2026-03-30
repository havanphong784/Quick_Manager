package com.quickmanager.controller;

import com.quickmanager.model.CT_HoaDon;
import com.quickmanager.model.HoaDon;
import com.quickmanager.service.InvoiceService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InvoiceController {
    @FXML private TextField txtMaHoaDon;
    @FXML private TextField txtTimKhachHang;
    @FXML private DatePicker dpTuNgay;
    @FXML private DatePicker dpDenNgay;
    @FXML private Button btnTimKiem;

    // DSHD
    @FXML private TableView<HoaDon> tbHoaDon;
    @FXML private TableColumn<HoaDon, Integer> colMaHD;
    @FXML private TableColumn<HoaDon, String> colTenKH;
    @FXML private TableColumn<HoaDon, LocalDateTime> colNgayLap;
    @FXML private TableColumn<HoaDon, BigDecimal> colTongTien;
    @FXML private TableColumn<HoaDon, BigDecimal> colGiamGia;
    @FXML private List<HoaDon> mangHoaDon = new ArrayList<HoaDon>();

    public void initialize() {
        initTbHoaDon();
        loadTbHoaDon();
        intTbCTHD();
        tbHoaDon.getSelectionModel().selectedItemProperty().addListener((obs,hdc,hdm) -> {
            handleSelectTbHD();
        });
    }

    public void initTbHoaDon() {
        colMaHD.setCellValueFactory(new PropertyValueFactory<>("maHoaDon"));
        colTenKH.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));
        colNgayLap.setCellValueFactory(new PropertyValueFactory<>("ngayLap"));
        colTongTien.setCellValueFactory(new PropertyValueFactory<>("tongTien"));
        colGiamGia.setCellValueFactory(new PropertyValueFactory<>("giamGia"));
    }

    public void loadTbHoaDon() {
        LocalDate ldTN = dpTuNgay.getValue();
        LocalDate ldDN = dpDenNgay.getValue();
        String maHD = txtMaHoaDon.getText();
        String tenKH = txtTimKhachHang.getText();
        mangHoaDon = InvoiceService.getHoaDon(tenKH, maHD,ldTN, ldDN);
        tbHoaDon.setItems(FXCollections.observableArrayList(mangHoaDon));
    }

    public void handleTimKiem() {
        loadTbHoaDon();
    }

    //CTHD
    @FXML private TableView<CT_HoaDon> tbCTHD;
    @FXML private TableColumn<CT_HoaDon,Integer> colSTT;
    @FXML private TableColumn<CT_HoaDon,String> colSanPham;
    @FXML private TableColumn<CT_HoaDon,String> colDonVi;
    @FXML private TableColumn<CT_HoaDon,Integer> colSoLuong;
    @FXML private TableColumn<CT_HoaDon,BigDecimal> colGiaBan;
    @FXML private TableColumn<CT_HoaDon,BigDecimal> colThanhTien;
    private List<CT_HoaDon> mangCTHD = new ArrayList<>();

    public void intTbCTHD() {
        colSTT.setCellValueFactory(new PropertyValueFactory<>("stt"));
        colDonVi.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
        colSanPham.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colGiaBan.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        colThanhTien.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));
    }

    public void handleSelectTbHD() {
        HoaDon hd = tbHoaDon.getSelectionModel().getSelectedItem();
        if (hd != null) {
            mangCTHD = InvoiceService.getCTHD(hd.getMaHoaDon());
            tbCTHD.setItems(FXCollections.observableArrayList(mangCTHD));
        }else {
            tbCTHD.getItems().clear();
        }
    }
}