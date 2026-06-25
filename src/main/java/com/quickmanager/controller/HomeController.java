package com.quickmanager.controller;

import com.quickmanager.debug.AppLogger;
import com.quickmanager.model.CanhBaoTonKho;
import com.quickmanager.model.DoanhThuNgay;
import com.quickmanager.model.TopSanPham;
import com.quickmanager.service.InvoiceService;
import com.quickmanager.service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeController {
    private static final Logger logger = AppLogger.getLogger(HomeController.class);
    @FXML
    private Label lblDoanhThuHomNay;
    @FXML
    private Label lblDonHangHomNay;
    @FXML
    private Label lblSPBanRa;
    @FXML
    private Label lblSPSapHet;

    @FXML
    private BarChart<String, Number> bcDoanhThu;
    @FXML
    private LineChart<String, Number> lcDonHang;
    @FXML
    private AreaChart<String, Number> acSPBan;
    @FXML
    private PieChart pcTopSP;

    public void initialize() {
        loadHomeSummary();
        loadCharts();
    }

    private void loadHomeSummary() {
        LocalDate today = LocalDate.now();
        BigDecimal doanhThu = InvoiceService.getTotalRevenue(today, today);
        int soHoaDon = InvoiceService.getOrderCount(today, today);
        int soSpBan = InvoiceService.getProductsSold(today, today);
        List<CanhBaoTonKho> low = ProductService.getLowStock(10);

        lblDoanhThuHomNay.setText(String.format("%,d VNĐ", doanhThu.longValue()));
        lblDonHangHomNay.setText(String.valueOf(soHoaDon));
        lblSPBanRa.setText(String.valueOf(soSpBan));
        lblSPSapHet.setText(String.valueOf(low.size()));
    }

    private void loadCharts() {
        try {
            LocalDate today = LocalDate.now();
            LocalDate from = today.minusDays(6); // 7 days
            List<DoanhThuNgay> list = InvoiceService.getDoanhThuTheoNgay(from, today);
            List<DoanhThuNgay> qtyList = InvoiceService.getSoLuongTheoNgay(from, today);

            if (bcDoanhThu != null) {
                bcDoanhThu.getData().clear();
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName("Doanh thu");
                for (DoanhThuNgay d : list) {
                    series.getData().add(new XYChart.Data<>(d.getNgay().toString(), d.getDoanhThu().longValue()));
                }
                bcDoanhThu.getData().add(series);
            }

            if (lcDonHang != null) {
                lcDonHang.getData().clear();
                XYChart.Series<String, Number> s2 = new XYChart.Series<>();
                s2.setName("Số hóa đơn");
                for (DoanhThuNgay d : list) {
                    s2.getData().add(new XYChart.Data<>(d.getNgay().toString(), d.getSoHoaDon()));
                }
                lcDonHang.getData().add(s2);
            }

            if (acSPBan != null) {
                acSPBan.getData().clear();
                XYChart.Series<String, Number> s3 = new XYChart.Series<>();
                s3.setName("Sản phẩm bán ra");
                for (DoanhThuNgay d : qtyList) {
                    s3.getData().add(new XYChart.Data<>(d.getNgay().toString(), d.getSoHoaDon()));
                }
                acSPBan.getData().add(s3);
            }

            if (pcTopSP != null) {
                pcTopSP.getData().clear();
                List<TopSanPham> top = InvoiceService.getTopSanPham(today.minusDays(30), today, 5);
                for (TopSanPham t : top) {
                    pcTopSP.getData().add(new PieChart.Data(t.getTenSanPham(), t.getSoLuong()));
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi loadCharts", e);
        }
    }
}
