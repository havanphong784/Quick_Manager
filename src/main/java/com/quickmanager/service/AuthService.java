package com.quickmanager.service;

import com.quickmanager.config.DBConnection;
import com.quickmanager.debug.AppLogger;
import com.quickmanager.model.TaiKhoan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthService {
    private static final Logger logger = AppLogger.getLogger(AuthService.class);

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
            logger.log(Level.SEVERE, "Lỗi đăng nhập", e);
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
            logger.log(Level.SEVERE, "Lỗi kiểm tra tên đăng nhập", e);
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
            logger.log(Level.SEVERE, "Lỗi đăng ký tài khoản", e);
            return false;
        }
    }
}
