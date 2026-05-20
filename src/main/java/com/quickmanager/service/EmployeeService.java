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
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlGetNv)) {
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

    public static boolean updateNhanVien(NhanVien nv) {
        String sql = """
        UPDATE NHAN_VIEN
        SET TenNhanVien = ?,
            NgaySinh = ?,
            GioiTinh = ?,
            SoDienThoai = ?,
            Email = ?,
            DiaChi = ?,
            Luong = ?,
            TrangThai = ?
        WHERE MaNhanVien = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nv.getTenNhanVien());
            if (nv.getNgaySinh() != null) {
                ps.setDate(2, nv.getNgaySinh());
            } else {
                ps.setNull(2, java.sql.Types.DATE);
            }
            ps.setString(3, nv.getGioiTinh());
            ps.setString(4, nv.getSoDienThoai());
            ps.setString(5, nv.getEmail());
            ps.setString(6, nv.getDiaChi());
            ps.setDouble(7, nv.getLuong());
            ps.setString(8, nv.getTrangThai());
            ps.setInt(9, nv.getMaNhanVien());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("Lỗi updateNhanVien: " + e.getMessage());
            Address.printAddress();
            return false;
        }
    }

    public static boolean addNhanVien(NhanVien nv) {
        String sql = """
        INSERT INTO NHAN_VIEN
            (TenNhanVien, NgaySinh, GioiTinh, SoDienThoai, Email, DiaChi, Luong, TrangThai)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nv.getTenNhanVien());
            if (nv.getNgaySinh() != null) {
                ps.setDate(2, nv.getNgaySinh());
            } else {
                ps.setNull(2, java.sql.Types.DATE);
            }
            ps.setString(3, nv.getGioiTinh());
            ps.setString(4, nv.getSoDienThoai());
            ps.setString(5, nv.getEmail());
            ps.setString(6, nv.getDiaChi());
            ps.setDouble(7, nv.getLuong());
            ps.setString(8, nv.getTrangThai());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        nv.setMaNhanVien(keys.getInt(1));
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    Address.printAddress();
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Lỗi addNhanVien: " + e.getMessage());
            Address.printAddress();
            return false;
        }
    }

    public static List<NhanVien> getNhanVienDangLam() {
        String sql = "SELECT * FROM NHAN_VIEN WHERE TrangThai = N'Đang làm' ORDER BY MaNhanVien";
        List<NhanVien> ds = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getInt("MaNhanVien"));
                nv.setTenNhanVien(rs.getString("TenNhanVien"));
                nv.setTrangThai(rs.getString("TrangThai"));
                nv.setEmail(rs.getString("Email"));
                ds.add(nv);
            }
        } catch (Exception e) {
            System.out.println("Loi lay danh sach nhan vien dang lam: " + e.getMessage());
            Address.printAddress();
        }
        return ds;
    }
}
