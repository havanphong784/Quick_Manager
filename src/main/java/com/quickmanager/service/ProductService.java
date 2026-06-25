package com.quickmanager.service;

import com.quickmanager.model.CanhBaoTonKho;
import java.math.BigDecimal;

import com.quickmanager.config.DBConnection;
import com.quickmanager.debug.Address;
import com.quickmanager.debug.AppLogger;
import com.quickmanager.model.DanhMuc;
import com.quickmanager.model.SanPham;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductService {
    private static final Logger logger = AppLogger.getLogger(ProductService.class);
    private static List<SanPham> allProductsCache = null;

    public static synchronized void refreshProductCache() {
        String sqlGetAllProducts = """
                SELECT sp.*, dm.TenDanhMuc 
                FROM SAN_PHAM sp
                JOIN DANH_MUC dm ON sp.MaDanhMuc = dm.MaDanhMuc
                """;
        List<SanPham> ds = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlGetAllProducts);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSanPham(rs.getInt("MaSanPham"));
                sp.setTenSanPham(rs.getString("TenSanPham"));
                sp.setMaDanhMuc(rs.getInt("MaDanhMuc"));
                sp.setGiaBan(rs.getBigDecimal("GiaBan"));
                sp.setGiaNhap(rs.getBigDecimal("GiaNhap"));
                sp.setSoLuongTon(rs.getInt("SoLuongTon"));
                sp.setDonViTinh(rs.getString("DonViTinh"));
                sp.setTenDanhMuc(rs.getString("TenDanhMuc"));
                sp.setTrangThai(rs.getString("TrangThai"));
                sp.setBarcode(rs.getString("Barcode"));
                try {
                    sp.setMucToiThieu(rs.getInt("MucToiThieu"));
                } catch (Exception ignored) {
                }
                sp.setNgaySanXuat(rs.getDate("NgaySanXuat"));
                sp.setHanSuDung(rs.getDate("HanSuDung"));
                ds.add(sp);
            }
            allProductsCache = ds;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi ngoại lệ khi làm mới bộ nhớ đệm", e);
            Address.printAddress();
        }
    }

    public static List<SanPham> getAllProductsFromCache() {
        if (allProductsCache == null) {
            refreshProductCache();
        }
        return allProductsCache;
    }

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

    public static List<SanPham> getProduct(String key, String danhMuc, String trangThai, Boolean hh) {
        List<SanPham> allProducts = getAllProductsFromCache();
        List<SanPham> result = new ArrayList<>();

        String filterKey = key == null ? "" : key.trim().toLowerCase();
        String filterDanhMuc = danhMuc == null ? "" : danhMuc.trim().toLowerCase();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        java.util.Date today = cal.getTime();

        for (SanPham sp : allProducts) {
            if (trangThai != null && !trangThai.isEmpty()) {
                if (sp.getTrangThai() == null || !sp.getTrangThai().equalsIgnoreCase(trangThai)) {
                    continue;
                }
            }

            boolean matchKey = filterKey.isEmpty();
            if (!matchKey) {
                if (String.valueOf(sp.getMaSanPham()).toLowerCase().contains(filterKey) ||
                        (sp.getTenSanPham() != null && sp.getTenSanPham().toLowerCase().contains(filterKey)) ||
                        (sp.getBarcode() != null && sp.getBarcode().toLowerCase().contains(filterKey))) {
                    matchKey = true;
                }
            }
            if (!matchKey) continue;

            if (!filterDanhMuc.isEmpty()) {
                if (sp.getTenDanhMuc() == null || !sp.getTenDanhMuc().toLowerCase().contains(filterDanhMuc)) {
                    continue;
                }
            }

            if (hh != null && hh) {
                if (sp.getHanSuDung() != null && sp.getHanSuDung().before(today)) {
                    continue;
                }
            }

            result.add(sp);
        }

        return result;
    }

    public static List<DanhMuc> getDanhMuc() {
        List<DanhMuc> ds = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement(getDanhMuc);
                 ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    ds.add(new DanhMuc(rs.getInt("MaDanhMuc"), rs.getString("TenDanhMuc")));
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Lỗi ngoại lệ", e);
                Address.printAddress();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi ngoại lệ", e);
            Address.printAddress();
        }
        return ds;
    }

    public static List<SanPham> searchProducts(String key) {
        List<SanPham> allProducts = getAllProductsFromCache();
        List<SanPham> ds = new ArrayList<SanPham>();
        String filterKey = key == null ? "" : key.trim().toLowerCase();

        for (SanPham sp : allProducts) {
            boolean matchKey = filterKey.isEmpty() ||
                    (sp.getTenSanPham() != null && sp.getTenSanPham().toLowerCase().contains(filterKey)) ||
                    String.valueOf(sp.getMaSanPham()).toLowerCase().contains(filterKey) ||
                    (sp.getBarcode() != null && sp.getBarcode().toLowerCase().contains(filterKey));

            if (matchKey) {
                SanPham newSp = new SanPham();
                newSp.setTenSanPham(sp.getTenSanPham());
                newSp.setMaSanPham(sp.getMaSanPham());
                newSp.setDonViTinh(sp.getDonViTinh());
                newSp.setGiaNhap(sp.getGiaNhap());
                newSp.setSoLuongTon(sp.getSoLuongTon());
                ds.add(newSp);
            }
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
            if (rowsAffected > 0) {
                refreshProductCache();
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi cập nhật sản phẩm", e);
            Address.printAddress();
            return false;
        }
    }

    public static List<CanhBaoTonKho> getLowStock(int threshold) {
        List<CanhBaoTonKho> ds = new ArrayList<>();
        String sql = "SELECT MaSanPham, TenSanPham, SoLuongTon, TrangThai FROM SAN_PHAM WHERE SoLuongTon <= ? ORDER BY SoLuongTon ASC";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, threshold);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ds.add(new CanhBaoTonKho(rs.getInt("MaSanPham"), rs.getString("TenSanPham"), rs.getInt("SoLuongTon"), threshold, rs.getString("TrangThai")));
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi ngoại lệ", e);
            Address.printAddress();
        }
        return ds;
    }

    public static SanPham createProduct(String tenSanPham, int maDanhMuc, BigDecimal giaNhap, BigDecimal giaBan, int soLuongTon, String donViTinh, String barcode, int mucToiThieu) {
        String sql = "INSERT INTO SAN_PHAM (TenSanPham, MaDanhMuc, GiaNhap, GiaBan, SoLuongTon, DonViTinh, TrangThai, Barcode, MucToiThieu) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, tenSanPham);
            ps.setInt(2, maDanhMuc);
            ps.setBigDecimal(3, giaNhap);
            ps.setBigDecimal(4, giaBan);
            ps.setInt(5, soLuongTon);
            ps.setString(6, donViTinh == null ? "" : donViTinh);
            ps.setString(7, "Đang bán");
            ps.setString(8, barcode);
            ps.setInt(9, mucToiThieu);
            int rows = ps.executeUpdate();
            if (rows == 0) return null;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
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
                    refreshProductCache();
                    return sp;
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi ngoại lệ", e);
            Address.printAddress();
        }
        return null;
    }

    public static SanPham getByBarcode(String code) {
        String sql = "SELECT * FROM SAN_PHAM WHERE Barcode = ?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
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
                    try {
                        sp.setMucToiThieu(rs.getInt("MucToiThieu"));
                    } catch (Exception ignored) {
                    }
                    return sp;
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi ngoại lệ", e);
            Address.printAddress();
        }
        return null;
    }

}
