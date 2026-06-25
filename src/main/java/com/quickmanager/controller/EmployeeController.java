package com.quickmanager.controller;

import com.quickmanager.debug.Alerts;
import com.quickmanager.model.NhanVien;
import com.quickmanager.service.EmployeeService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {
    //DSNV
    @FXML
    private TextField txtTuKhoa;
    @FXML
    private ComboBox<String> cbTrangThai;
    @FXML
    private Button btnTimKiem;
    @FXML
    private TableView<NhanVien> tbNhanVien;
    @FXML
    private TableColumn<NhanVien, Integer> colMaNV;
    @FXML
    private TableColumn<NhanVien, String> colHoTen;
    @FXML
    private TableColumn<NhanVien, String> colDiaChi;
    @FXML
    private TableColumn<NhanVien, String> colTrangThai;
    @FXML
    private TableColumn<NhanVien, String> colSdt;
    @FXML
    private Label lblTongNhanVien;
    private List<NhanVien> mangNhanVien = new ArrayList<>();

    // TTNV
    @FXML
    private TextField txtMaNV;
    @FXML
    private TextField txtTenNV;
    @FXML
    private DatePicker dpNgaySinh;
    @FXML
    private ComboBox<String> cbGioiTinh;
    @FXML
    private TextField txtSdt;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtDiaChi;
    @FXML
    private ComboBox<String> cbFormTrangThai;
    @FXML
    private Button btnCapNhat;
    @FXML
    private Button btnThem;
    @FXML
    private Button btnMoi;

    public void initialize() {
        initTBNV();
        tbNhanVien.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> handleSelectTBNV());
        loadTrangThai();
        loadTBNhanVien();
        loadGioiTinh();
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
        lblTongNhanVien.setText("Tổng nhân viên: " + mangNhanVien.size());
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
            Date sqlDate = nv.getNgaySinh();
            if (sqlDate != null) {
                dpNgaySinh.setValue(sqlDate.toLocalDate());
            } else {
                dpNgaySinh.setValue(null);
            }
        } else {
            txtDiaChi.clear();
            txtMaNV.clear();
            txtEmail.clear();
            txtSdt.clear();
            txtTenNV.clear();
            cbFormTrangThai.getSelectionModel().clearSelection();
            cbGioiTinh.getSelectionModel().clearSelection();
            dpNgaySinh.setValue(null);
        }
        if (nv != null) {
            btnThem.setDisable(true);
            btnCapNhat.setDisable(false);
        } else {
            btnThem.setDisable(false);
            btnCapNhat.setDisable(true);
        }
        btnMoi.setText("Mới");
    }

    public void handleCapNhat() {
        String maStr = txtMaNV.getText();
        if (maStr == null || maStr.isBlank()) {
            Alerts.thongBao("Lỗi", "Vui lòng chọn nhân viên để cập nhật");
            return;
        }

        int ma;
        try {
            ma = Integer.parseInt(maStr);
        } catch (NumberFormatException ex) {
            Alerts.thongBao("Lỗi", "Mã nhân viên không hợp lệ");
            return;
        }

        String ten = txtTenNV.getText() == null ? "" : txtTenNV.getText().trim();
        String gioiTinh = cbGioiTinh.getValue();
        String sdt = txtSdt.getText() == null ? "" : txtSdt.getText().trim();
        String email = txtEmail.getText() == null ? "" : txtEmail.getText().trim();
        String diaChi = txtDiaChi.getText() == null ? "" : txtDiaChi.getText().trim();
        String trangThai = cbFormTrangThai.getValue() == null ? "" : cbFormTrangThai.getValue();

        if (ten.isEmpty()) {
            Alerts.thongBao("Lỗi", "Tên nhân viên không được để trống");
            return;
        }

        Date sqlDate = null;
        LocalDate ld = dpNgaySinh.getValue();
        if (ld != null) {
            sqlDate = Date.valueOf(ld);
        }

        double luong = 0d;
        NhanVien existing = tbNhanVien.getSelectionModel().getSelectedItem();
        if (existing != null) {
            luong = existing.getLuong();
        }

        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(ma);
        nv.setTenNhanVien(ten);
        nv.setGioiTinh(gioiTinh);
        nv.setSoDienThoai(sdt);
        nv.setEmail(email);
        nv.setDiaChi(diaChi);
        nv.setTrangThai(trangThai);
        nv.setNgaySinh(sqlDate);
        nv.setLuong(luong);

        boolean ok = EmployeeService.updateNhanVien(nv);
        if (ok) {
            Alerts.thongBao("Thành công", "Cập nhật nhân viên thành công");
            loadTBNhanVien();
            for (int i = 0; i < mangNhanVien.size(); i++) {
                if (mangNhanVien.get(i).getMaNhanVien() == ma) {
                    tbNhanVien.getSelectionModel().clearAndSelect(i);
                    break;
                }
            }
        } else {
            Alerts.thongBao("Thất bại", "Cập nhật nhân viên thất bại");
        }
    }


    public void handleThem() {
        String ten = txtTenNV.getText() == null ? "" : txtTenNV.getText().trim();
        String gioiTinh = cbGioiTinh.getValue();
        String sdt = txtSdt.getText() == null ? "" : txtSdt.getText().trim();
        String email = txtEmail.getText() == null ? "" : txtEmail.getText().trim();
        String diaChi = txtDiaChi.getText() == null ? "" : txtDiaChi.getText().trim();
        String trangThai = cbFormTrangThai.getValue() == null ? "" : cbFormTrangThai.getValue();

        if (ten.isEmpty()) {
            Alerts.thongBao("Lỗi", "Tên nhân viên không được để trống");
            return;
        }

        Date sqlDate = null;
        LocalDate ld = dpNgaySinh.getValue();
        if (ld != null) sqlDate = Date.valueOf(ld);

        NhanVien nv = new NhanVien();
        nv.setTenNhanVien(ten);
        nv.setGioiTinh(gioiTinh);
        nv.setSoDienThoai(sdt);
        nv.setEmail(email);
        nv.setDiaChi(diaChi);
        nv.setTrangThai(trangThai);
        nv.setNgaySinh(sqlDate);
        nv.setLuong(0d);

        boolean ok = EmployeeService.addNhanVien(nv);
        if (ok) {
            Alerts.thongBao("Thành công", "Thêm nhân viên thành công");
            loadTBNhanVien();
            if (nv.getMaNhanVien() > 0) {
                for (int i = 0; i < mangNhanVien.size(); i++) {
                    if (mangNhanVien.get(i).getMaNhanVien() == nv.getMaNhanVien()) {
                        tbNhanVien.getSelectionModel().clearAndSelect(i);
                        break;
                    }
                }
            } else {
                for (int i = 0; i < mangNhanVien.size(); i++) {
                    NhanVien a = mangNhanVien.get(i);
                    if (a.getTenNhanVien().equals(nv.getTenNhanVien()) &&
                            ((a.getEmail() == null && nv.getEmail() == null) || (a.getEmail() != null && a.getEmail().equals(nv.getEmail())))) {
                        tbNhanVien.getSelectionModel().clearAndSelect(i);
                        break;
                    }
                }
            }
            txtMaNV.clear();
            txtTenNV.clear();
            dpNgaySinh.setValue(null);
            cbGioiTinh.getSelectionModel().clearSelection();
            txtSdt.clear();
            txtEmail.clear();
            txtDiaChi.clear();
            cbFormTrangThai.getSelectionModel().clearSelection();
        } else {
            Alerts.thongBao("Thất bại", "Thêm nhân viên thất bại");
        }
    }

    public void handleMoi() {
        txtMaNV.clear();
        txtTenNV.clear();
        dpNgaySinh.setValue(null);
        cbGioiTinh.getSelectionModel().clearSelection();
        txtSdt.clear();
        txtEmail.clear();
        txtDiaChi.clear();
        cbFormTrangThai.getSelectionModel().clearSelection();
        btnThem.setDisable(false);
        btnCapNhat.setDisable(true);
        btnMoi.setText("Clear");
    }
}
