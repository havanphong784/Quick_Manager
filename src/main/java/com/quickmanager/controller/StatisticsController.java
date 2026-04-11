package com.quickmanager.controller;

import com.quickmanager.model.CanhBaoTonKho;
import com.quickmanager.model.DoanhThuNgay;
import com.quickmanager.model.TopSanPham;
import com.quickmanager.service.InvoiceService;
import com.quickmanager.service.ProductService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StatisticsController {
	@FXML private DatePicker dpTuNgay;
	@FXML private DatePicker dpDenNgay;
	@FXML private ComboBox<String> cbKieuThongKe;
	@FXML private Button btnThongKe;

	@FXML private Label lblDoanhThu;
	@FXML private Label lblSoHoaDon;
	@FXML private Label lblSanPhamBan;

	@FXML private TableView<DoanhThuNgay> tblDoanhThuNgay;
	@FXML private TableColumn<DoanhThuNgay, String> colNgay;
	@FXML private TableColumn<DoanhThuNgay, Integer> colSoHoaDonNgay;
	@FXML private TableColumn<DoanhThuNgay, String> colDoanhThuNgay;

	@FXML private TableView<TopSanPham> tblTopSanPham;
	@FXML private TableColumn<TopSanPham, String> colTopTenSP;
	@FXML private TableColumn<TopSanPham, Integer> colTopSoLuong;
	@FXML private TableColumn<TopSanPham, String> colTopDoanhThu;

	@FXML private TableView<CanhBaoTonKho> tblTonKhoThap;
	@FXML private TableColumn<CanhBaoTonKho, Integer> colCanhBaoMaSP;
	@FXML private TableColumn<CanhBaoTonKho, String> colCanhBaoTenSP;
	@FXML private TableColumn<CanhBaoTonKho, Integer> colCanhBaoTonKho;
	@FXML private TableColumn<CanhBaoTonKho, Integer> colCanhBaoMucToiThieu;
	@FXML private TableColumn<CanhBaoTonKho, String> colCanhBaoTrangThai;

	private final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@FXML
	public void initialize() {
		// set default dates
		if (dpDenNgay != null) dpDenNgay.setValue(LocalDate.now());
		if (dpTuNgay != null) dpTuNgay.setValue(LocalDate.now().minusDays(7));

		if (cbKieuThongKe != null) {
			cbKieuThongKe.getItems().setAll("Doanh thu", "Số hóa đơn", "Sản phẩm bán ra");
			cbKieuThongKe.getSelectionModel().selectFirst();
		}

		initTables();
		if (btnThongKe != null) btnThongKe.setOnAction(e -> doThongKe());
		doThongKe();
	}

	private void initTables() {
		if (colNgay != null) colNgay.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getNgay().format(df)));
		if (colSoHoaDonNgay != null) colSoHoaDonNgay.setCellValueFactory(new PropertyValueFactory<>("soHoaDon"));
		if (colDoanhThuNgay != null) colDoanhThuNgay.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(String.format("%,d", cd.getValue().getDoanhThu().longValue())));

		if (colTopTenSP != null) colTopTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
		if (colTopSoLuong != null) colTopSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
		if (colTopDoanhThu != null) colTopDoanhThu.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(String.format("%,d", cd.getValue().getDoanhThu().longValue())));

		if (colCanhBaoMaSP != null) colCanhBaoMaSP.setCellValueFactory(new PropertyValueFactory<>("maSanPham"));
		if (colCanhBaoTenSP != null) colCanhBaoTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
		if (colCanhBaoTonKho != null) colCanhBaoTonKho.setCellValueFactory(new PropertyValueFactory<>("tonKho"));
		if (colCanhBaoMucToiThieu != null) colCanhBaoMucToiThieu.setCellValueFactory(new PropertyValueFactory<>("mucToiThieu"));
		if (colCanhBaoTrangThai != null) colCanhBaoTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
	}

	private void doThongKe() {
		LocalDate from = (dpTuNgay == null) ? LocalDate.of(2020,1,1) : dpTuNgay.getValue();
		LocalDate to = (dpDenNgay == null) ? LocalDate.now() : dpDenNgay.getValue();
		if (from == null) from = LocalDate.of(2020,1,1);
		if (to == null) to = LocalDate.now();

		java.math.BigDecimal total = InvoiceService.getTotalRevenue(from, to);
		int hoaDon = InvoiceService.getOrderCount(from, to);
		int spBan = InvoiceService.getProductsSold(from, to);

		if (lblDoanhThu != null) lblDoanhThu.setText(String.format("%,d VNĐ", total.longValue()));
		if (lblSoHoaDon != null) lblSoHoaDon.setText(String.valueOf(hoaDon));
		if (lblSanPhamBan != null) lblSanPhamBan.setText(String.valueOf(spBan));

		if (tblDoanhThuNgay != null) tblDoanhThuNgay.setItems(FXCollections.observableArrayList(InvoiceService.getDoanhThuTheoNgay(from, to)));
		if (tblTopSanPham != null) tblTopSanPham.setItems(FXCollections.observableArrayList(InvoiceService.getTopSanPham(from, to, 10)));
		if (tblTonKhoThap != null) tblTonKhoThap.setItems(FXCollections.observableArrayList(ProductService.getLowStock(10)));
	}
}
