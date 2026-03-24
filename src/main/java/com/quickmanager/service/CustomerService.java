package com.quickmanager.service;

import com.quickmanager.config.DBConnection;
import com.quickmanager.debug.Address;
import com.quickmanager.model.KhachHang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    public static final String sqlAllCustomer = """
            Select * From KHACH_HANG
            """;
    public static List<KhachHang> loadCustomer() {
        List<KhachHang> ds = new ArrayList<KhachHang>();
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sqlAllCustomer);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    KhachHang kh = new KhachHang(
                            rs.getInt("MaKhachHang"),
                            rs.getString("TenKhachHang"),
                            rs.getString("SoDienThoai"),
                            rs.getString("Email"),
                            rs.getString("DiaChi"),
                            rs.getInt("DiemTichLuy"),
                            rs.getString("TrangThai")
                    );
                    ds.add(kh);
                }
            }
        }catch (Exception e) {
            System.out.println("Loi connect");
            Address.printAddress();
        }
        return ds;
    }
}
