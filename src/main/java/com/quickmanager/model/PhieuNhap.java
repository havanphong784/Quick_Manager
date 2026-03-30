package com.quickmanager.model;

import java.math.BigDecimal;
import java.util.Date;

public class PhieuNhap {

    private int maPhieuNhap;
    private int maNhanVien;
    private int maNCC;
    private Date ngayNhap;
    private BigDecimal tongTien;
    private String trangThai;

    public PhieuNhap() {
    }

    public PhieuNhap(int maPhieuNhap, int maNhanVien, int maNCC, Date ngayNhap, BigDecimal tongTien, String trangThai) {
        this.maPhieuNhap = maPhieuNhap;
        this.maNhanVien = maNhanVien;
        this.maNCC = maNCC;
        this.ngayNhap = ngayNhap;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public int getMaPhieuNhap() { return maPhieuNhap; }
    public void setMaPhieuNhap(int maPhieuNhap) { this.maPhieuNhap = maPhieuNhap; }

    public int getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(int maNhanVien) { this.maNhanVien = maNhanVien; }

    public int getMaNCC() { return maNCC; }
    public void setMaNCC(int maNCC) { this.maNCC = maNCC; }

    public Date getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(Date ngayNhap) { this.ngayNhap = ngayNhap; }

    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
