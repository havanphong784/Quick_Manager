package com.quickmanager.controller;

import com.quickmanager.model.GioHangItem;
import com.quickmanager.model.KhachHang;
import com.quickmanager.model.SanPham;
import com.quickmanager.service.CustomerService;
import com.quickmanager.service.ProductService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SellController {

    // SP
    @FXML private TableView<SanPham> tbvSanPham;
    @FXML private TableColumn<SanPham, Integer> colMaSP;
    @FXML private TableColumn<SanPham, String> colTenSP;
    @FXML private TableColumn<SanPham, String> colDVT;
    @FXML private TableColumn<SanPham, Integer> colTonKho;
    @FXML private TableColumn<SanPham, BigDecimal> colGiaBan;
    @FXML private TextField txtTimSanPham;
    private static final ObservableList<SanPham> dataSanPham = FXCollections.observableArrayList();

    // GH
    @FXML private TableView<GioHangItem> tbvGioHang;
    @FXML private TableColumn<GioHangItem, String> colSanPhamGH;
    @FXML private TableColumn<GioHangItem, Integer> colSoLuongGH;
    @FXML private TableColumn<GioHangItem, BigDecimal> colDonGiaGH;
    @FXML private TableColumn<GioHangItem, BigDecimal> colThanhTienGH;
    private static final ObservableList<GioHangItem> dataGioHang = FXCollections.observableArrayList();
    private static final List<GioHangItem> mangGioHang = new ArrayList<>();
    @FXML private Button btnThemGio;
    @FXML private TextField txtSoLuongNhanh;
    @FXML private Label labelThemGio;
    @FXML private Label labelXoaGio;

    // DM
    @FXML private ComboBox<String> cbDanhMuc;
    private static String stringDanhMuc= "";

    // KH
    @FXML private ComboBox<KhachHang> cbKhachHang;
    @FXML private TextField txtKhachDua;
    @FXML private TextField txtSDT;
    @FXML private TextField txtKhachHang;
    @FXML private TextField txtGiamGia;
    @FXML private Label lblTongTien;
    @FXML private Label lblTamTinh;
    @FXML private Label lblTienThoi;

    // Khỏi tạo
    @FXML
    public void initialize() {
        initTable();
        initTableGH();
        loadDanhMuc();
        tbvSanPham.setItems(dataSanPham);
        loadSanPham();
        initKhachHang();
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
        dataSanPham.setAll(ProductService.getProduct(txtTimSanPham.getText(),stringDanhMuc));
    }

    public void handleSearch() {
        loadSanPham();
    }

    // DM
    public void loadDanhMuc() {
        cbDanhMuc.getItems().addAll(ProductService.getDanhMuc());
        cbDanhMuc.getItems().addFirst("");
    }

    public void handleSelectDM() {
        String selected = cbDanhMuc.getValue();
        stringDanhMuc = (selected == null) ? "" : selected;
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
        String stringSL = txtSoLuongNhanh.getText().trim();
        int sl = 1;
        if (stringSL != null) {
            try {
                sl = Integer.parseInt(stringSL);
            }catch (NumberFormatException e){
                labelThemGio.setText("Vui long nhap so.");
                return;
            }
        }


        if (sp != null && sp.getSoLuongTon() >= sl) {
            GioHangItem gh = new GioHangItem(sp.getMaSanPham(),sp.getTenSanPham(),sl,sp.getGiaBan());
            for (GioHangItem it : mangGioHang) {
                if (gh.getMaSanPham() == it.getMaSanPham()) {
                    labelThemGio.setText("Đã tồn tại trong giỏ.");
                    return;
                }
            }
            mangGioHang.add(gh);
            dataGioHang.setAll(mangGioHang);
            labelThemGio.setText("");
        }
    }

    public void handleXoaDong() {
        GioHangItem selected = tbvGioHang.getSelectionModel().getSelectedItem();
        if (selected == null) {
            labelXoaGio.setText("Chọn dòng để xóa.");
            System.out.println("Đã xóa.");
        }else {
            mangGioHang.removeIf(it -> it.getMaSanPham() == selected.getMaSanPham());
            dataGioHang.setAll(mangGioHang);
        }
    }

    public void handleXoaGio() {
        mangGioHang.clear();
        dataGioHang.setAll(mangGioHang);
    }


    // KH
    public void initKhachHang() {
        cbKhachHang.getItems().setAll(CustomerService.loadCustomer());
        cbKhachHang.getItems().addFirst(new KhachHang(0,"","","","",0,""));
    }

    public void handleSelectKH() {
            KhachHang kh = cbKhachHang.getValue();
            txtSDT.setText(kh.getSoDienThoai());
            txtKhachHang.setText(kh.getTenKhachHang());
    }


}
