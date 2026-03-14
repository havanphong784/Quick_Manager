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
                Select * 
                From TAI_KHOAN
                Where TenDangNhap= ?
                And MatKhau = ?
                """;
        try(Connection con = DBConnection.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1,username);
            st.setString(2,password);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    TaiKhoan tk = new TaiKhoan();
                    tk.setTenDangNhap(rs.getString("TenDangNhap"));
                    tk.setMatKhau(rs.getString("MatKhau"));
                    tk.setMaNhanVien(rs.getInt("MaNhanVien"));
                    tk.setVaiTro(rs.getString("VaiTro"));
                    tk.setTrangThai(rs.getString("TrangThai"));
                    return tk;
                }
            }
        }catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
            Address.printAddress();
        }
        return null;
    }
}
