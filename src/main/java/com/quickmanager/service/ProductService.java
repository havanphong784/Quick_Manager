package com.quickmanager.service;

import com.quickmanager.config.DBConnection;
import com.quickmanager.debug.Address;
import com.quickmanager.model.SanPham;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static final String sqlGetProducts = """
    SELECT sp.MaSanPham,
           sp.TenSanPham,
           sp.MaDanhMuc,
           dm.TenDanhMuc,
           sp.GiaBan,
           sp.SoLuongTon,
           sp.DonViTinh,
           sp.HanSuDung,
           sp.TrangThai
    FROM SAN_PHAM sp
    JOIN DANH_MUC dm ON sp.MaDanhMuc = dm.MaDanhMuc
    WHERE (sp.HanSuDung IS NULL OR sp.HanSuDung >= CAST(GETDATE() AS DATE))
      AND (
            CAST(sp.MaSanPham AS VARCHAR(20)) LIKE ?
         OR sp.TenSanPham LIKE ?
      )
        AND dm.TenDanhMuc LIKE ?
    """;

    public static final String getDanhMuc = """
            Select [TenDanhMuc] From DANH_MUC;
            """;

    public static List<SanPham> getProduct(String key,String danhMuc) {
        List<SanPham> ds = new ArrayList<>();
        String string = "%" + ( key == null ? "" : key.trim()) + "%";
        String dm =  "%" + ( danhMuc == null ? "" : danhMuc.trim()) + "%";
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sqlGetProducts);
            ps.setString(1, string);
            ps.setString(2, string);
            ps.setString(3, dm);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SanPham sp = new SanPham();
                    sp.setMaSanPham(rs.getInt("MaSanPham"));
                    sp.setTenSanPham(rs.getString("TenSanPham"));
                    sp.setDonViTinh(rs.getString("DonViTinh"));
                    sp.setSoLuongTon(rs.getInt("SoLuongTon"));
                    sp.setGiaBan(rs.getBigDecimal("GiaBan"));
                    ds.add(sp);
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
                Address.printAddress();
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
            Address.printAddress();
        }
        return ds;
    }

    public static List<String> getDanhMuc() {
        List<String> ds = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement(getDanhMuc);
                 ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    ds.add(rs.getString("TenDanhMuc"));
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
