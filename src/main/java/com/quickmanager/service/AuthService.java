package com.quickmanager.service;

import com.quickmanager.config.DBConnection;
import com.quickmanager.debug.Address;
import com.quickmanager.model.TaiKhoan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthService {

    public static TaiKhoan login(String username, String password) {
        String sql = """
            SELECT TenDangNhap, MatKhau, MaNhanVien, VaiTro
            FROM TAI_KHOAN
            WHERE TenDangNhap = ?
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, username);

            try (ResultSet rs = st.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                String mkLuu = rs.getString("MatKhau");
                if (!PasswordUtils.ktraHash(password, mkLuu)) {
                    return null;
                }

                TaiKhoan tk = new TaiKhoan();
                tk.setTenDangNhap(rs.getString("TenDangNhap"));
                tk.setMaNhanVien(rs.getInt("MaNhanVien"));
                tk.setVaiTro(rs.getString("VaiTro"));
                return tk;
            }
        } catch (Exception e) {
            System.out.println("Lỗi đăng nhập: " + e.getMessage());
            Address.printAddress();
            return null;
        }
    }

    public static boolean isUsernameExists(String username) {
        String sql = "SELECT 1 FROM TAI_KHOAN WHERE TenDangNhap = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            System.out.println("Loi kiem tra ten dang nhap: " + e.getMessage());
            Address.printAddress();
            return true;
        }
    }

    public static boolean register(String username, String password, int maNhanVien) {
        String sql = """
            INSERT INTO TAI_KHOAN (TenDangNhap, MatKhau, MaNhanVien, VaiTro, TrangThai)
            VALUES (?, ?, ?, ?, ?)
            """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            st.setString(2, PasswordUtils.hash(password));
            st.setInt(3, maNhanVien);
            st.setString(4, "NHANVIEN");
            st.setString(5, "Đang làm");
            return st.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi dang ky: " + e.getMessage());
            Address.printAddress();
            return false;
        }
    }
}
