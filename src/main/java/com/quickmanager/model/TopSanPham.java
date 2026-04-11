package com.quickmanager.model;

import java.math.BigDecimal;

public class TopSanPham {
	private String tenSanPham;
	private int soLuong;
	private BigDecimal doanhThu;

	public TopSanPham(String tenSanPham, int soLuong, BigDecimal doanhThu) {
		this.tenSanPham = tenSanPham;
		this.soLuong = soLuong;
		this.doanhThu = doanhThu;
	}

	public String getTenSanPham() { return tenSanPham; }
	public int getSoLuong() { return soLuong; }
	public BigDecimal getDoanhThu() { return doanhThu; }
}


