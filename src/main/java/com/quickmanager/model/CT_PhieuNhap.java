package com.quickmanager.model;

import java.math.BigDecimal;

public class CT_PhieuNhap {

    private int maPhieuNhap;
    private int maSanPham;
    private int soLuong;
    private BigDecimal giaNhap;
    private BigDecimal thanhTien;

    public CT_PhieuNhap() {
    }

    public CT_PhieuNhap(int maPhieuNhap, int maSanPham, int soLuong, BigDecimal giaNhap, BigDecimal thanhTien) {
        this.maPhieuNhap = maPhieuNhap;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.thanhTien = thanhTien;
    }

    public int getMaPhieuNhap() { return maPhieuNhap; }
    public void setMaPhieuNhap(int maPhieuNhap) { this.maPhieuNhap = maPhieuNhap; }

    public int getMaSanPham() { return maSanPham; }
    public void setMaSanPham(int maSanPham) { this.maSanPham = maSanPham; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public BigDecimal getGiaNhap() { return giaNhap; }
    public void setGiaNhap(BigDecimal giaNhap) { this.giaNhap = giaNhap; }

    public BigDecimal getThanhTien() { return thanhTien; }
    public void setThanhTien(BigDecimal thanhTien) { this.thanhTien = thanhTien; }
}