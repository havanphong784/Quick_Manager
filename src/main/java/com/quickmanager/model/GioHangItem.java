package com.quickmanager.model;

import java.math.BigDecimal;

public class GioHangItem {
    private int maSanPham;
    private String tenSanPham;
    private int soLuong;
    private BigDecimal giaBan;
    private BigDecimal thanhTien;

    public GioHangItem(int maSanPham, String tenSanPham, int soLuong,
                       BigDecimal giaBan) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.thanhTien = giaBan.multiply(BigDecimal.valueOf(soLuong));
    }

    public int getMaSanPham() { return maSanPham; }
    public String getTenSanPham() { return tenSanPham; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
        this.thanhTien = giaBan.multiply(BigDecimal.valueOf(soLuong));
    }
    public BigDecimal getGiaBan() { return giaBan; }
    public BigDecimal getThanhTien() { return thanhTien; }
}
