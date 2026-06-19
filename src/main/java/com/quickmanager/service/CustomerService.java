package com.quickmanager.service;

import com.quickmanager.debug.AppLogger;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.quickmanager.config.DBConnection;
import com.quickmanager.debug.Address;
import com.quickmanager.model.KhachHang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class    CustomerService {
    private static final Logger logger = AppLogger.getLogger(CustomerService.class);
    public static final String sqlAllCustomer = """
            Select * From KHACH_HANG
            """;

    public static final String sqlInsertKH = """
            Insert into KHACH_HANG (TenKhachHang, SoDienThoai, Email)
            Values(?,?,?)
            """;
    public static List<KhachHang> loadCustomer() {
        List<KhachHang> ds = new ArrayList<KhachHang>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlAllCustomer);
             ResultSet rs = ps.executeQuery()) {
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
        }catch (Exception e) {
            logger.info("Loi connect");
            Address.printAddress();
        }
        return ds;
    }

    public static KhachHang themKhachHang(String name, String sdt, String email) {
        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlInsertKH, Statement.RETURN_GENERATED_KEYS);)
            {
            ps.setString(1, name);
            ps.setString(2, sdt);
            if (email == null || email.isBlank()) {
                ps.setNull(3, Types.VARCHAR);
            } else {
                ps.setString(3, email);
            }
            int kt = ps.executeUpdate();
            if (kt == 0) throw new SQLException("Tạo khách hàng thất bại");
            try (ResultSet key = ps.getGeneratedKeys()) {
                if (key.next()) {
                    return new KhachHang(
                            key.getInt(1),
                            name,
                            sdt,
                            email,
                            null,
                            0,
                            "Hoạt động"
                    );
                } else throw new SQLException("Lấy mã khách hàng thất bại");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Lỗi ngoại lệ", e);
            logger.info("Lỗi tạo khách hàng");
            Address.printAddress();
            return null;
        }
    }
}
