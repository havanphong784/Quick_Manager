package com.quickmanager.model;

public class CanhBaoTonKho {
    private int maSanPham;
    private String tenSanPham;
    private int tonKho;
    private int mucToiThieu;
    private String trangThai;

    public CanhBaoTonKho(int maSanPham, String tenSanPham, int tonKho, int mucToiThieu, String trangThai) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.tonKho = tonKho;
        this.mucToiThieu = mucToiThieu;
        this.trangThai = trangThai;
    }

    public int getMaSanPham() { return maSanPham; }
    public String getTenSanPham() { return tenSanPham; }
    public int getTonKho() { return tonKho; }
    public int getMucToiThieu() { return mucToiThieu; }
    public String getTrangThai() { return trangThai; }
}

