package com.quickmanager.service;

import com.quickmanager.config.DBConnection;
import com.quickmanager.debug.Address;
import com.quickmanager.model.DanhMuc;
import com.quickmanager.model.SanPham;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static final String sqlGetProducts = """
    SELECT *
    FROM SAN_PHAM sp
    JOIN DANH_MUC dm ON sp.MaDanhMuc = dm.MaDanhMuc
    WHERE sp.[TrangThai] LIKE ?
      AND (
            CAST(sp.MaSanPham AS VARCHAR(20)) LIKE ?
         OR sp.TenSanPham LIKE ?
      )
        AND dm.TenDanhMuc LIKE ?
    """;

    public static final String getDanhMuc = """
            Select * From DANH_MUC;
            """;

    public static final String sqlSearchProducts = """
        Select sp.MaSanPham,
               sp.TenSanPham,
               sp.DonViTinh,
               sp.SoLuongTon,
               sp.GiaNhap
        From SAN_PHAM sp
        Where sp.TenSanPham Like ?
            Or sp.MaSanPham like ?
        """;

    public static List<SanPham> getProduct(String key, String danhMuc, String trangThai,Boolean hh) {
        String sql = hh ? sqlGetProducts + "AND (sp.HanSuDung IS NULL OR sp.HanSuDung >= CAST(GETDATE() AS DATE))" : sqlGetProducts;
        List<SanPham> ds = new ArrayList<>();
        String string = "%" + ( key == null ? "" : key.trim()) + "%";
        String dm =  "%" + ( danhMuc == null ? "" : danhMuc.trim()) + "%";
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, trangThai == null || trangThai.isEmpty() ? "%" : trangThai);
            ps.setString(2, string);
            ps.setString(3, string);
            ps.setString(4, dm);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SanPham sp = new SanPham();
                    sp.setMaSanPham(rs.getInt("MaSanPham"));
                    sp.setTenSanPham(rs.getString("TenSanPham"));
                    sp.setMaDanhMuc(rs.getInt("MaDanhMuc"));
                    sp.setGiaBan(rs.getBigDecimal("GiaBan"));
                    sp.setGiaNhap(rs.getBigDecimal("GiaNhap"));
                    sp.setSoLuongTon(rs.getInt("SoLuongTon"));
                    sp.setDonViTinh(rs.getString("DonViTinh"));
                    sp.setGiaBan(rs.getBigDecimal("GiaBan"));
                    sp.setTenDanhMuc(rs.getString("TenDanhMuc"));
                    sp.setTrangThai(rs.getString("TrangThai"));
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

    public static List<DanhMuc> getDanhMuc() {
        List<DanhMuc> ds = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement(getDanhMuc);
                 ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    ds.add(new DanhMuc(rs.getInt("MaDanhMuc"),rs.getString("TenDanhMuc")));
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

    public static List<SanPham> searchProducts(String key) {
        List<SanPham> ds = new ArrayList<SanPham>();
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sqlSearchProducts);
            ps.setString(1,(key == null) ? "" : "%"+key+"%");
            ps.setString(2,(key == null) ? "" : "%"+key+"%");
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    SanPham sp = new SanPham();
                    sp.setTenSanPham(rs.getString("TenSanPham"));
                    sp.setMaSanPham(rs.getInt("MaSanPham"));
                    sp.setDonViTinh(rs.getString("DonVitinh"));
                    sp.setGiaNhap(rs.getBigDecimal("GiaNhap"));
                    sp.setSoLuongTon(rs.getInt("SoLuongton"));
                    ds.add(sp);
                }
            }catch(Exception e) {
                System.out.println(e.getMessage());
                Address.printAddress();
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            Address.printAddress();
        }
        return ds;
    }

    public static boolean updateProduct(SanPham sp) {
        String sqlUpdate = """
        UPDATE SAN_PHAM
        SET TenSanPham = ?,
            MaDanhMuc = ?,
            GiaNhap = ?,
            GiaBan = ?,
            SoLuongTon = ?,
            DonViTinh = ?,
            TrangThai = ?
        WHERE MaSanPham = ?
        """;

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sqlUpdate);
            ps.setString(1, sp.getTenSanPham());
            ps.setInt(2, sp.getMaDanhMuc());
            ps.setBigDecimal(3, sp.getGiaNhap());
            ps.setBigDecimal(4, sp.getGiaBan());
            ps.setInt(5, sp.getSoLuongTon());
            ps.setString(6, sp.getDonViTinh());
            ps.setString(7, sp.getTrangThai());
            ps.setInt(8, sp.getMaSanPham());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Lỗi cập nhật sản phẩm: " + e.getMessage());
            Address.printAddress();
            return false;
        }
    }

    // low stock
    public static java.util.List<com.quickmanager.model.CanhBaoTonKho> getLowStock(int threshold) {
        java.util.List<com.quickmanager.model.CanhBaoTonKho> ds = new java.util.ArrayList<>();
        String sql = "SELECT MaSanPham, TenSanPham, SoLuongTon, TrangThai FROM SAN_PHAM WHERE SoLuongTon <= ? ORDER BY SoLuongTon ASC";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, threshold);
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    ds.add(new com.quickmanager.model.CanhBaoTonKho(rs.getInt("MaSanPham"), rs.getString("TenSanPham"), rs.getInt("SoLuongTon"), threshold, rs.getString("TrangThai")));
                }
            }
        }catch (Exception e){ System.out.println(e.getMessage()); Address.printAddress(); }
        return ds;
    }

}
