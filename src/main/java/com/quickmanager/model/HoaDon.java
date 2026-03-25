package com.quickmanager.model;

import java.math.BigDecimal;
import java.util.Date;

public class HoaDon {

    private int maHoaDon;
    private int maNhanVien;
    private Integer maKhachHang;
    private Date ngayLap;
    private BigDecimal tongTien;
    private BigDecimal tienKhachDua;
    private BigDecimal tienThoi;
    private String trangThai;

    public HoaDon() {
    }

    public HoaDon(int maHoaDon, int maNhanVien, Date ngayLap,
                  BigDecimal tongTien, BigDecimal tienKhachDua,
                  BigDecimal tienThoi, String trangThai) {
        this(maHoaDon, maNhanVien, null, ngayLap, tongTien, tienKhachDua, tienThoi, trangThai);
    }

    public HoaDon(int maHoaDon, int maNhanVien, Integer maKhachHang, Date ngayLap,
                  BigDecimal tongTien, BigDecimal tienKhachDua,
                  BigDecimal tienThoi, String trangThai) {
        this.maHoaDon = maHoaDon;
        this.maNhanVien = maNhanVien;
        this.maKhachHang = maKhachHang;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.tienKhachDua = tienKhachDua;
        this.tienThoi = tienThoi;
        this.trangThai = trangThai;
    }

    public int getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(int maHoaDon) { this.maHoaDon = maHoaDon; }

    public int getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(int maNhanVien) { this.maNhanVien = maNhanVien; }

    public Integer getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(Integer maKhachHang) { this.maKhachHang = maKhachHang; }

    public Date getNgayLap() { return ngayLap; }
    public void setNgayLap(Date ngayLap) { this.ngayLap = ngayLap; }

    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }

    public BigDecimal getTienKhachDua() { return tienKhachDua; }
    public void setTienKhachDua(BigDecimal tienKhachDua) { this.tienKhachDua = tienKhachDua; }

    public BigDecimal getTienThoi() { return tienThoi; }
    public void setTienThoi(BigDecimal tienThoi) { this.tienThoi = tienThoi; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}