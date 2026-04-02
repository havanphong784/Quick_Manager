package com.quickmanager.service;

import com.quickmanager.config.DBConnection;
import com.quickmanager.debug.Address;
import com.quickmanager.model.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    public static final String sqlGetNv = """
    SELECT * FROM NHAN_VIEN
    WHERE (
        TenNhanVien LIKE ?
        OR CAST(MaNhanVien AS VARCHAR(20)) LIKE ?
        OR Email LIKE ?
        OR SoDienThoai LIKE ?
        OR GioiTinh LIKE ?
    )
    AND TrangThai LIKE ?
    """;


    public static List<NhanVien> getNhanVien(String key,String trangThai) {
        List<NhanVien> ds = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sqlGetNv);
            ps.setString(1, "%" + key + "%");
            ps.setString(2, "%" + key + "%");
            ps.setString(3, "%" + key + "%");
            ps.setString(4, "%" + key + "%");
            ps.setString(5, "%" + key + "%");
            ps.setString(6, "%" + trangThai + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NhanVien nv = new NhanVien();
                    nv.setMaNhanVien(rs.getInt("MaNhanVien"));
                    nv.setTenNhanVien(rs.getString("TenNhanVien"));
                    nv.setGioiTinh(rs.getString("GioiTinh"));
                    nv.setEmail(rs.getString("Email"));
                    nv.setSoDienThoai(rs.getString("SoDienThoai"));
                    nv.setDiaChi(rs.getString("DiaChi"));
                    nv.setTrangThai(rs.getString("TrangThai"));
                    nv.setNgaySinh(rs.getDate("NgaySinh"));
                    ds.add(nv);
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
                Address.printAddress();
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            Address.printAddress();
        }
        return ds;
    }
}
