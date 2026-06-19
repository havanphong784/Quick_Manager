package com.quickmanager.controller;

import com.quickmanager.model.CanhBaoTonKho;
import com.quickmanager.model.DoanhThuNgay;
import com.quickmanager.model.TopSanPham;
import com.quickmanager.service.InvoiceService;
import com.quickmanager.service.ProductService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class StatisticsController {

    @FXML
    private DatePicker dpTuNgay;

    @FXML
    private DatePicker dpDenNgay;

    @FXML
    private ComboBox<String> cbKieuThongKe;

    @FXML
    private Label lblDoanhThu;

    @FXML
    private Label lblSoHoaDon;

    @FXML
    private Label lblSanPhamBan;

    @FXML
    private TableView<DoanhThuNgay> tblDoanhThuNgay;

    @FXML
    private TableColumn<DoanhThuNgay, LocalDate> colNgay;

    @FXML
    private TableColumn<DoanhThuNgay, Integer> colSoHoaDonNgay;

    @FXML
    private TableColumn<DoanhThuNgay, BigDecimal> colDoanhThuNgay;

    @FXML
    private TableView<TopSanPham> tblTopSanPham;

    @FXML
    private TableColumn<TopSanPham, String> colTopTenSP;

    @FXML
    private TableColumn<TopSanPham, Integer> colTopSoLuong;

    @FXML
    private TableColumn<TopSanPham, BigDecimal> colTopDoanhThu;

    @FXML
    private TableView<CanhBaoTonKho> tblTonKhoThap;

    @FXML
    private TableColumn<CanhBaoTonKho, Integer> colCanhBaoMaSP;

    @FXML
    private TableColumn<CanhBaoTonKho, String> colCanhBaoTenSP;

    @FXML
    private TableColumn<CanhBaoTonKho, Integer> colCanhBaoTonKho;

    @FXML
    private TableColumn<CanhBaoTonKho, Integer> colCanhBaoMucToiThieu;

    @FXML
    private TableColumn<CanhBaoTonKho, String> colCanhBaoTrangThai;

    @FXML
    public void initialize() {
        initTables();
        initFilters();

        if (tblDoanhThuNgay != null) {
            tblDoanhThuNgay.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    javafx.application.Platform.runLater(this::loadStatistics);
                }
            });
        } else {
            loadStatistics();
        }
    }

    private void initTables() {
        colNgay.setCellValueFactory(new PropertyValueFactory<>("ngay"));
        colSoHoaDonNgay.setCellValueFactory(new PropertyValueFactory<>("soHoaDon"));
        colDoanhThuNgay.setCellValueFactory(new PropertyValueFactory<>("doanhThu"));

        colTopTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
        colTopSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colTopDoanhThu.setCellValueFactory(new PropertyValueFactory<>("doanhThu"));

        colCanhBaoMaSP.setCellValueFactory(new PropertyValueFactory<>("maSanPham"));
        colCanhBaoTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
        colCanhBaoTonKho.setCellValueFactory(new PropertyValueFactory<>("tonKho"));
        colCanhBaoMucToiThieu.setCellValueFactory(new PropertyValueFactory<>("mucToiThieu"));
        colCanhBaoTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
    }

    private void initFilters() {
        cbKieuThongKe.setItems(FXCollections.observableArrayList("Hom nay", "7 ngay", "30 ngay", "Tuy chinh"));
        cbKieuThongKe.getSelectionModel().select("30 ngay");
        LocalDate today = LocalDate.now();
        dpDenNgay.setValue(today);
        dpTuNgay.setValue(today.minusDays(29));
    }

    @FXML
    private void handleThongKe() {
        loadStatistics();
    }

    private void loadStatistics() {
        LocalDate to = dpDenNgay.getValue() == null ? LocalDate.now() : dpDenNgay.getValue();
        LocalDate from = resolveFromDate(to);

        if (from.isAfter(to)) {
            LocalDate temp = from;
            from = to;
            to = temp;
            dpTuNgay.setValue(from);
            dpDenNgay.setValue(to);
        }

        BigDecimal doanhThu = InvoiceService.getTotalRevenue(from, to);
        int soHoaDon = InvoiceService.getOrderCount(from, to);
        int soSanPhamBan = InvoiceService.getProductsSold(from, to);

        lblDoanhThu.setText(String.format("%,d VNĐ", doanhThu.longValue()));
        lblSoHoaDon.setText(String.valueOf(soHoaDon));
        lblSanPhamBan.setText(String.valueOf(soSanPhamBan));

        List<DoanhThuNgay> doanhThuNgay = InvoiceService.getDoanhThuTheoNgay(from, to);
        tblDoanhThuNgay.setItems(FXCollections.observableArrayList(doanhThuNgay));

        List<TopSanPham> topSanPham = InvoiceService.getTopSanPham(from, to, 10);
        tblTopSanPham.setItems(FXCollections.observableArrayList(topSanPham));

        List<CanhBaoTonKho> tonKhoThap = ProductService.getLowStock(10);
        tblTonKhoThap.setItems(FXCollections.observableArrayList(tonKhoThap));
    }

    private LocalDate resolveFromDate(LocalDate toDate) {
        String type = cbKieuThongKe.getValue();
        if ("Hom nay".equals(type)) {
            LocalDate from = toDate;
            dpTuNgay.setValue(from);
            return from;
        }
        if ("7 ngay".equals(type)) {
            LocalDate from = toDate.minusDays(6);
            dpTuNgay.setValue(from);
            return from;
        }
        if ("30 ngay".equals(type)) {
            LocalDate from = toDate.minusDays(29);
            dpTuNgay.setValue(from);
            return from;
        }
        return dpTuNgay.getValue() == null ? toDate.minusDays(29) : dpTuNgay.getValue();
    }
}
