package com.quickmanager.service;

import com.quickmanager.config.DBConnection;
import com.quickmanager.debug.Address;
import com.quickmanager.model.SanPham;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static String sqlGetProducts = """
            SELECT *
            FROM SAN_PHAM AS sp
            WHERE CAST(sp.MaSanPham AS VARCHAR) LIKE ?
               OR sp.TenSanPham LIKE ?;
            """;
    public static List<SanPham> getProduct(String key) {
        List<SanPham> ds = new ArrayList<>();
        String string = "%" + ( key == null ? "" : key.trim()) + "%";
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sqlGetProducts);
            ps.setString(1, string);
            ps.setString(2, string);
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
}
