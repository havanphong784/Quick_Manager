package com.quickmanager.controller;

import com.quickmanager.model.NhanVien;
import com.quickmanager.service.EmployeeService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.Date;
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

    // TTNV
    @FXML private TextField txtMaNV;
    @FXML private TextField txtTenNV;
    @FXML private DatePicker dpNgaySinh;
    @FXML private ComboBox<String> cbGioiTinh;
    @FXML private TextField txtSdt;
    @FXML private TextField txtEmail;
    @FXML private TextField txtDiaChi;
    @FXML private ComboBox<String> cbFormTrangThai;

    public void initialize() {
        initTBNV();
        loadTrangThai();
        loadTBNhanVien();
        loadGioiTinh();
        tbNhanVien.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> handleSelectTBNV());
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
        cbFormTrangThai.setItems(FXCollections.observableArrayList("Đang làm", "Nghỉ phép", "Nghỉ việc"));
    }

    public void loadGioiTinh() {
        cbGioiTinh.setItems(FXCollections.observableArrayList("Nam", "Nữ", "Khác"));
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

    public void handleSelectTBNV() {
        NhanVien nv = tbNhanVien.getSelectionModel().getSelectedItem();
        if (nv != null) {
            txtDiaChi.setText(nv.getDiaChi());
            txtEmail.setText(nv.getEmail());
            txtMaNV.setText(String.valueOf(nv.getMaNhanVien()));
            txtSdt.setText(nv.getSoDienThoai());
            txtTenNV.setText(nv.getTenNhanVien());
            cbFormTrangThai.setValue(nv.getTrangThai());
            cbGioiTinh.setValue(nv.getGioiTinh());
            java.sql.Date sqlDate = nv.getNgaySinh();
            if (sqlDate != null) {
                dpNgaySinh.setValue(sqlDate.toLocalDate());
            } else {
                dpNgaySinh.setValue(null);
            }
        } else {
            txtDiaChi.clear();
            txtEmail.clear();
            txtSdt.clear();
            txtTenNV.clear();
            cbFormTrangThai.getSelectionModel().clearSelection();
            cbGioiTinh.getSelectionModel().clearSelection();
            dpNgaySinh.setValue(null);
        }
    }

}
