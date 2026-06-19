package com.quickmanager.service;

import com.quickmanager.debug.AppLogger;
import java.util.logging.Logger;
import java.util.logging.Level;

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
    private static final Logger logger = AppLogger.getLogger(ProductService.class);
    private static final String sqlGetProducts = """
    SELECT *
    FROM SAN_PHAM sp
    JOIN DANH_MUC dm ON sp.MaDanhMuc = dm.MaDanhMuc
    WHERE sp.[TrangThai] LIKE ?
      AND (
            CAST(sp.MaSanPham AS VARCHAR(20)) LIKE ?
         OR sp.TenSanPham LIKE ?
         Or sp.Barcode like ?
          
      )
        AND dm.TenDanhMuc LIKE ?
    """;

    public static final String getDanhMuc = """
            Select * From DANH_MUC;
            """;

    public static final String sqlSearchProducts = """
        Select *
        From SAN_PHAM sp
        Where sp.TenSanPham Like ?
            Or CAST(sp.MaSanPham AS VARCHAR(50)) like ?
            Or sp.Barcode like ?
        """;

    public static List<SanPham> getProduct(String key, String danhMuc, String trangThai,Boolean hh) {
        String sql = hh ? sqlGetProducts + "AND (sp.HanSuDung IS NULL OR sp.HanSuDung >= CAST(GETDATE() AS DATE))" : sqlGetProducts;
        List<SanPham> ds = new ArrayList<>();
        String string = "%" + ( key == null ? "" : key.trim()) + "%";
        String dm =  "%" + ( danhMuc == null ? "" : danhMuc.trim()) + "%";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, trangThai == null || trangThai.isEmpty() ? "%" : trangThai);
            ps.setString(2, string);
            ps.setString(3, string);
            ps.setString(4, string);
            ps.setString(5, dm);
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
                    sp.setBarcode(rs.getString("Barcode"));
                    ds.add(sp);
                }
            }catch (Exception e) {
                logger.log(Level.SEVERE, "Lỗi ngoại lệ", e);
                Address.printAddress();
            }
        }catch(Exception e) {
            logger.log(Level.SEVERE, "Lỗi ngoại lệ", e);
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
                logger.log(Level.SEVERE, "Lỗi ngoại lệ", e);
                Address.printAddress();
            }
        }catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi ngoại lệ", e);
            Address.printAddress();
        }
        return ds;
    }

    public static List<SanPham> searchProducts(String key) {
        List<SanPham> ds = new ArrayList<SanPham>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlSearchProducts)) {
            String q = (key == null) ? "" : "%"+key+"%";
            ps.setString(1, q);
            ps.setString(2, q);
            ps.setString(3, q);
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
                logger.log(Level.SEVERE, "Lỗi ngoại lệ", e);
                Address.printAddress();
            }
        }catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi ngoại lệ", e);
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

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlUpdate)) {
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
            logger.log(Level.SEVERE, "Lỗi cập nhật sản phẩm", e);
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
        }catch (Exception e){ logger.log(Level.SEVERE, "Lỗi ngoại lệ", e); Address.printAddress(); }
        return ds;
    }

    public static SanPham createProduct(String tenSanPham, int maDanhMuc, java.math.BigDecimal giaNhap, java.math.BigDecimal giaBan, int soLuongTon, String donViTinh, String barcode, int mucToiThieu) {
        String sql = "INSERT INTO SAN_PHAM (TenSanPham, MaDanhMuc, GiaNhap, GiaBan, SoLuongTon, DonViTinh, TrangThai, Barcode, MucToiThieu) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, tenSanPham);
            ps.setInt(2, maDanhMuc);
            ps.setBigDecimal(3, giaNhap);
            ps.setBigDecimal(4, giaBan);
            ps.setInt(5, soLuongTon);
            ps.setString(6, donViTinh == null ? "" : donViTinh);
            ps.setString(7, "Đang bán");
            ps.setString(8, barcode == null ? null : barcode);
            ps.setInt(9, mucToiThieu);
            int rows = ps.executeUpdate();
            if (rows == 0) return null;
            try (ResultSet rs = ps.getGeneratedKeys()){
                if (rs.next()){
                    int id = rs.getInt(1);
                    SanPham sp = new SanPham();
                    sp.setMaSanPham(id);
                    sp.setTenSanPham(tenSanPham);
                    sp.setMaDanhMuc(maDanhMuc);
                    sp.setGiaNhap(giaNhap);
                    sp.setGiaBan(giaBan);
                    sp.setSoLuongTon(soLuongTon);
                    sp.setDonViTinh(donViTinh);
                    sp.setTrangThai("Đang bán");
                    sp.setBarcode(barcode);
                    sp.setMucToiThieu(mucToiThieu);
                    return sp;
                }
            }
        }catch (Exception e){ logger.log(Level.SEVERE, "Lỗi ngoại lệ", e); Address.printAddress(); }
        return null;
    }

    public static SanPham getByBarcode(String code) {
        String sql = "SELECT * FROM SAN_PHAM WHERE Barcode = ?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    SanPham sp = new SanPham();
                    sp.setMaSanPham(rs.getInt("MaSanPham"));
                    sp.setTenSanPham(rs.getString("TenSanPham"));
                    sp.setMaDanhMuc(rs.getInt("MaDanhMuc"));
                    sp.setGiaNhap(rs.getBigDecimal("GiaNhap"));
                    sp.setGiaBan(rs.getBigDecimal("GiaBan"));
                    sp.setSoLuongTon(rs.getInt("SoLuongTon"));
                    sp.setDonViTinh(rs.getString("DonViTinh"));
                    sp.setTrangThai(rs.getString("TrangThai"));
                    sp.setBarcode(rs.getString("Barcode"));
                    try { sp.setMucToiThieu(rs.getInt("MucToiThieu")); } catch (Exception ignored) {}
                    return sp;
                }
            }
        } catch (Exception e) { logger.log(Level.SEVERE, "Lỗi ngoại lệ", e); Address.printAddress(); }
        return null;
    }

}
