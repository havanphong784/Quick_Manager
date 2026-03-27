package com.quickmanager.service;

import com.quickmanager.config.DBConnection;
import com.quickmanager.debug.Address;
import com.quickmanager.model.KhachHang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    public static final String sqlAllCustomer = """
            Select * From KHACH_HANG
            """;

    public static final String sqlInsertKH = """
            Insert into KHACH_HANG (TenKhachHang, SoDienThoai)
            Values(?,?)
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

    public static KhachHang themKhachHang(String name , String sdt) {
        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlInsertKH, Statement.RETURN_GENERATED_KEYS);)
            {
            ps.setString(1, name);
            ps.setString(2, sdt);
            int kt = ps.executeUpdate();
            if (kt == 0) throw new SQLException("Tạo khách hàng thất bại");
            try (ResultSet key = ps.getGeneratedKeys()) {
                if (key.next()) {
                    return new KhachHang(
                            key.getInt(1),
                            name,
                            sdt,
                            null,
                            null,
                            0,
                            "Hoạt động"
                    );
                } else throw new SQLException("Lấy mã khách hàng thất bại");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Lỗi tạo khách hàng");
            Address.printAddress();
            return null;
        }
    }
}
