package com.quickmanager.controller;

import com.quickmanager.service.InvoiceService;
import com.quickmanager.service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class HomeController {
	@FXML private Label lblDoanhThuHomNay;
	@FXML private Label lblDonHangHomNay;
	@FXML private Label lblSPBanRa;
	@FXML private Label lblSPSapHet;

	public void initialize() {
		loadHomeSummary();
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
}
