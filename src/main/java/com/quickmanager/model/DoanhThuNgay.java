package com.quickmanager.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DoanhThuNgay {
    private LocalDate ngay;
    private int soHoaDon;
    private BigDecimal doanhThu;

    public DoanhThuNgay(LocalDate ngay, int soHoaDon, BigDecimal doanhThu) {
        this.ngay = ngay;
        this.soHoaDon = soHoaDon;
        this.doanhThu = doanhThu;
    }

    public LocalDate getNgay() { return ngay; }
    public int getSoHoaDon() { return soHoaDon; }
    public BigDecimal getDoanhThu() { return doanhThu; }
}

