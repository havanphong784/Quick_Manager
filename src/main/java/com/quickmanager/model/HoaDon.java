package com.quickmanager.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class HoaDon {

    private int maHoaDon;
    private int maNhanVien;
    private Integer maKhachHang;
    private LocalDate ngayLap;
    private BigDecimal tongTien;
    private BigDecimal giamGia;
    private BigDecimal tienKhachDua;
    private BigDecimal tienThoi;
    private String trangThai;
    private String tenKhachHang;

    public HoaDon(int maHoaDon, String tenKhachHang, LocalDate ngayLap, BigDecimal tongTien, BigDecimal giamGia) {
        this.maHoaDon = maHoaDon;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.giamGia = giamGia;
        this.tenKhachHang = tenKhachHang;
    }

    public HoaDon(int maNhanVien, Integer maKhachHang, BigDecimal tongTien, BigDecimal giamGia, BigDecimal tienKhachDua, BigDecimal tienThoi) {
        this.maNhanVien = maNhanVien;
        this.maKhachHang = maKhachHang;
        this.tongTien = tongTien;
        this.giamGia = giamGia;
        this.tienKhachDua = tienKhachDua;
        this.tienThoi = tienThoi;
    }

    public HoaDon(int maHoaDon, Integer maKhachHang, String tenKhachHang, LocalDate ngayLap, BigDecimal tongTien, BigDecimal giamGia) {
        this.maHoaDon = maHoaDon;
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.giamGia = giamGia;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public Integer getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(Integer maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public BigDecimal getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(BigDecimal giamGia) {
        this.giamGia = giamGia;
    }

    public BigDecimal getTienKhachDua() {
        return tienKhachDua;
    }

    public void setTienKhachDua(BigDecimal tienKhachDua) {
        this.tienKhachDua = tienKhachDua;
    }

    public BigDecimal getTienThoi() {
        return tienThoi;
    }

    public void setTienThoi(BigDecimal tienThoi) {
        this.tienThoi = tienThoi;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }
}