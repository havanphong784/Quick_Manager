package com.quickmanager.controller;

import com.quickmanager.service.InvoiceService;
import com.quickmanager.service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class HomeController {
	@FXML private Label lblDoanhThuHomNay;
	@FXML private Label lblDonHangHomNay;
	@FXML private Label lblSPBanRa;
	@FXML private Label lblSPSapHet;

	@FXML private BarChart<String, Number> bcDoanhThu;
	@FXML private LineChart<String, Number> lcDonHang;
	@FXML private AreaChart<String, Number> acSPBan;
	@FXML private PieChart pcTopSP;

	public void initialize() {
		loadHomeSummary();
		loadCharts();
	}

	private void loadHomeSummary() {
		LocalDate today = LocalDate.now();
		java.math.BigDecimal doanhThu = InvoiceService.getTotalRevenue(today, today);
		int soHoaDon = InvoiceService.getOrderCount(today, today);
		int soSpBan = InvoiceService.getProductsSold(today, today);
		java.util.List<com.quickmanager.model.CanhBaoTonKho> low = ProductService.getLowStock(10);

		lblDoanhThuHomNay.setText(String.format("%,d VNĐ", doanhThu.longValue()));
		lblDonHangHomNay.setText(String.valueOf(soHoaDon));
		lblSPBanRa.setText(String.valueOf(soSpBan));
		lblSPSapHet.setText(String.valueOf(low.size()));
	}

	private void loadCharts() {
		try {
			LocalDate today = LocalDate.now();
			LocalDate from = today.minusDays(6); // 7 days
			java.util.List<com.quickmanager.model.DoanhThuNgay> list = InvoiceService.getDoanhThuTheoNgay(from, today);
			java.util.List<com.quickmanager.model.DoanhThuNgay> qtyList = InvoiceService.getSoLuongTheoNgay(from, today);

			if (bcDoanhThu != null) {
				bcDoanhThu.getData().clear();
				javafx.scene.chart.XYChart.Series<String, Number> series = new javafx.scene.chart.XYChart.Series<>();
				series.setName("Doanh thu");
				for (com.quickmanager.model.DoanhThuNgay d : list) {
					series.getData().add(new javafx.scene.chart.XYChart.Data<>(d.getNgay().toString(), d.getDoanhThu().longValue()));
				}
				bcDoanhThu.getData().add(series);
			}

			if (lcDonHang != null) {
				lcDonHang.getData().clear();
				javafx.scene.chart.XYChart.Series<String, Number> s2 = new javafx.scene.chart.XYChart.Series<>();
				s2.setName("Số hóa đơn");
				for (com.quickmanager.model.DoanhThuNgay d : list) {
					s2.getData().add(new javafx.scene.chart.XYChart.Data<>(d.getNgay().toString(), d.getSoHoaDon()));
				}
				lcDonHang.getData().add(s2);
			}

			if (acSPBan != null) {
				acSPBan.getData().clear();
				javafx.scene.chart.XYChart.Series<String, Number> s3 = new javafx.scene.chart.XYChart.Series<>();
				s3.setName("Sản phẩm bán ra");
				for (com.quickmanager.model.DoanhThuNgay d : qtyList) {
					s3.getData().add(new javafx.scene.chart.XYChart.Data<>(d.getNgay().toString(), d.getSoHoaDon()));
				}
				acSPBan.getData().add(s3);
			}

			if (pcTopSP != null) {
				pcTopSP.getData().clear();
				java.util.List<com.quickmanager.model.TopSanPham> top = InvoiceService.getTopSanPham(today.minusDays(30), today, 5);
				for (com.quickmanager.model.TopSanPham t : top) {
					pcTopSP.getData().add(new PieChart.Data(t.getTenSanPham(), t.getSoLuong()));
				}
			}
		} catch (Exception e) {
			System.out.println("Lỗi loadCharts: " + e.getMessage());
		}
	}
}
