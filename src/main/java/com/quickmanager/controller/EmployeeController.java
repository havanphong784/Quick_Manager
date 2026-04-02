package com.quickmanager.controller;

import com.quickmanager.model.NhanVien;
import com.quickmanager.service.EmployeeService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class EmployeeController {
    //DSNV
    @FXML private TextField txtTuKhoa;
    @FXML private ComboBox<String> cbTrangThai;
    @FXML private Button btnTimKiem;
    @FXML private TableView<NhanVien> tbNhanVien;
    @FXML private TableColumn<NhanVien, Integer> colMaNV;
    @FXML private TableColumn<NhanVien, String> colHoTen;
    @FXML private TableColumn<NhanVien, String> colDiaChi;
    @FXML private TableColumn<NhanVien, String> colTrangThai;
    @FXML private TableColumn<NhanVien, String> colSdt;
    private List<NhanVien> mangNhanVien = new ArrayList<>();

    public void initialize() {
        initTBNV();
        loadTrangThai();
        loadTBNhanVien();
    }

    // DSNV
    public void initTBNV() {
        colMaNV.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
        colHoTen.setCellValueFactory(new PropertyValueFactory<>("tenNhanVien"));
        colDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        colTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        colSdt.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
    }

    public void loadTrangThai() {
        cbTrangThai.setItems(FXCollections.observableArrayList("Đang làm", "Nghỉ phép", "Nghỉ việc"));
    }

    public void loadTBNhanVien() {
        String key = txtTuKhoa.getText().trim();
        String trangThai = cbTrangThai.getValue() == null ? "" : cbTrangThai.getValue();
        mangNhanVien = EmployeeService.getNhanVien(key, trangThai);
        tbNhanVien.setItems(FXCollections.observableArrayList(mangNhanVien));
    }

    public void handleTimKiem() {
        loadTBNhanVien();
    }
}
