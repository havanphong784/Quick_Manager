package com.quickmanager.service;

import com.quickmanager.debug.AppLogger;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.quickmanager.config.DBConnection;
import com.quickmanager.debug.Address;
import com.quickmanager.model.CT_HoaDon;
import com.quickmanager.model.HoaDon;
import com.quickmanager.model.KhachHang;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceService {
    private static final Logger logger = AppLogger.getLogger(InvoiceService.class);
    public static final String sqlInsertHD = """
    Insert Into HOA_DON ( MaNhanVien,MaKhachHang,TongTien,GiamGia,TienKhachDua,TienThoi)
    Values(?,?,?,?,?,?);
    """;
    private static final String sqlInsertCTHD =
            "INSERT INTO CT_HOA_DON (MaHoaDon, MaSanPham, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";

    private static final String sqlUpdateSP = """
    UPDATE SAN_PHAM
    SET SoLuongTon = SoLuongTon - ?,
        TrangThai = CASE
            WHEN SoLuongTon - ? <= 0 THEN N'Ngung ban'
            ELSE TrangThai
        END
    WHERE MaSanPham = ? AND SoLuongTon >= ?;
    """;


    public static int taoHoaDonNKH(HoaDon hd, List<CT_HoaDon> ds) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement psHD = con.prepareStatement(sqlInsertHD, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement psCTHD = con.prepareStatement(sqlInsertCTHD);
                PreparedStatement psUDSP =  con.prepareStatement(sqlUpdateSP)) {

                // HD
                psHD.setInt(1, hd.getMaNhanVien());
                if (hd.getMaKhachHang() == null) psHD.setNull(2, Types.INTEGER);
                else psHD.setInt(2, hd.getMaKhachHang());
                psHD.setBigDecimal(3, hd.getTongTien());
                psHD.setBigDecimal(4, hd.getGiamGia());
                psHD.setBigDecimal(5, hd.getTienKhachDua());
                psHD.setBigDecimal(6, hd.getTienThoi());

                int ktHD = psHD.executeUpdate();
                if (ktHD == 0) throw new SQLException("Tạo hóa đơn thất bại");

                // CTHD
                int maHoaDon;
                try (ResultSet key = psHD.getGeneratedKeys()) {
                    if (key.next()) maHoaDon = key.getInt(1);
                    else throw new SQLException("Lấy mã hóa đơn thất bại");
                }

                for (CT_HoaDon items : ds) {
                    psUDSP.setInt(1, items.getSoLuong());
                    psUDSP.setInt(2, items.getSoLuong());
                    psUDSP.setInt(3, items.getMaSanPham());
                    psUDSP.setInt(4, items.getSoLuong());
                    int ktUDSP = psUDSP.executeUpdate();
                    if (ktUDSP != 1) throw new SQLException("Tồn kho không đủ hoặc cập nhật sản phẩm thất bại cho mã SP: " + items.getMaSanPham());

                    psCTHD.setInt(1, maHoaDon);
                    psCTHD.setInt(2, items.getMaSanPham());
                    psCTHD.setInt(3, items.getSoLuong());
                    psCTHD.setBigDecimal(4, items.getDonGia());
                    psCTHD.setBigDecimal(5, items.getThanhTien());
                    psCTHD.executeUpdate();
                }
                con.commit();
                return maHoaDon;
            }catch (Exception e) {
                con.rollback();
                throw e;  // gui loi ra ngoai
            }

        }
    }

    public static final String sqlGetHoaDon = """
        Select *
        From HOA_DON as hd
        Left Join KHACH_HANG as kh on kh.MaKhachHang = hd.MaKhachHang
        Where hd.NgayLap >= ? and hd.NgayLap < ?
        And (kh.TenKhachHang Like ? or kh.TenKhachHang is null)
        And CAST(hd.MaHoaDon AS VARCHAR(20)) Like ?
        """;

    public static List<HoaDon> getHoaDon(String nameKH, String maHD, LocalDate start, LocalDate end) {
        List<HoaDon> ds = new ArrayList<>();
        LocalDate fromDate = (start != null) ? start : LocalDate.of(2020, 1, 1);
        LocalDate toDateExclusive = ((end != null) ? end : LocalDate.now()).plusDays(1);
        String nameFilter = (nameKH == null || nameKH.isBlank()) ? "%" : "%" + nameKH.trim() + "%";
        String maHdFilter = (maHD == null || maHD.isBlank()) ? "%" : "%" + maHD.trim() + "%";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlGetHoaDon)) {
            ps.setDate(1, Date.valueOf(fromDate));
            ps.setDate(2, Date.valueOf(toDateExclusive));
            ps.setString(3, nameFilter);
            ps.setString(4, maHdFilter);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int maKhachHangRaw = rs.getInt("MaKhachHang");
                    Integer maKhachHang = rs.wasNull() ? null : maKhachHangRaw;
                    ds.add(new HoaDon(
                            rs.getInt("MaHoaDon"),
                            maKhachHang,
                            rs.getString("TenKhachHang"),
                            rs.getDate("NgayLap").toLocalDate(),
                            rs.getBigDecimal("TongTien"),
                            rs.getBigDecimal("GiamGia")));
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi ngoại lệ", e);
            Address.printAddress();
        }
        return ds;
    }

    // Statistics helpers
    public static java.math.BigDecimal getTotalRevenue(LocalDate start, LocalDate end) {
        java.math.BigDecimal total = java.math.BigDecimal.ZERO;
        String sql = "SELECT SUM(TongTien) as Total FROM HOA_DON WHERE NgayLap >= ? AND NgayLap < ?";
        LocalDate fromDate = (start != null) ? start : LocalDate.of(2020,1,1);
        LocalDate toDateExclusive = ((end != null) ? end : LocalDate.now()).plusDays(1);
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setDate(1, Date.valueOf(fromDate));
            ps.setDate(2, Date.valueOf(toDateExclusive));
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) total = rs.getBigDecimal("Total") == null ? java.math.BigDecimal.ZERO : rs.getBigDecimal("Total");
            }
        }catch (Exception e){ logger.log(Level.SEVERE, "Lỗi ngoại lệ", e); Address.printAddress(); }
        return total;
    }

    public static int getOrderCount(LocalDate start, LocalDate end) {
        int cnt = 0;
        String sql = "SELECT COUNT(*) as Cnt FROM HOA_DON WHERE NgayLap >= ? AND NgayLap < ?";
        LocalDate fromDate = (start != null) ? start : LocalDate.of(2020,1,1);
        LocalDate toDateExclusive = ((end != null) ? end : LocalDate.now()).plusDays(1);
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setDate(1, Date.valueOf(fromDate));
            ps.setDate(2, Date.valueOf(toDateExclusive));
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) cnt = rs.getInt("Cnt");
            }
        }catch (Exception e){ logger.log(Level.SEVERE, "Lỗi ngoại lệ", e); Address.printAddress(); }
        return cnt;
    }

    public static int getProductsSold(LocalDate start, LocalDate end) {
        int cnt = 0;
        String sql = "SELECT SUM(ct.SoLuong) as TotalQty FROM CT_HOA_DON ct JOIN HOA_DON hd ON ct.MaHoaDon = hd.MaHoaDon WHERE hd.NgayLap >= ? AND hd.NgayLap < ?";
        LocalDate fromDate = (start != null) ? start : LocalDate.of(2020,1,1);
        LocalDate toDateExclusive = ((end != null) ? end : LocalDate.now()).plusDays(1);
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setDate(1, Date.valueOf(fromDate));
            ps.setDate(2, Date.valueOf(toDateExclusive));
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) cnt = rs.getInt("TotalQty");
            }
        }catch (Exception e){ logger.log(Level.SEVERE, "Lỗi ngoại lệ", e); Address.printAddress(); }
        return cnt;
    }

    public static java.util.List<com.quickmanager.model.DoanhThuNgay> getDoanhThuTheoNgay(LocalDate start, LocalDate end) {
        java.util.List<com.quickmanager.model.DoanhThuNgay> ds = new java.util.ArrayList<>();
        String sql = "SELECT CAST(NgayLap AS DATE) AS Ngay, COUNT(*) AS SoHoaDon, SUM(TongTien) AS DoanhThu FROM HOA_DON WHERE NgayLap >= ? AND NgayLap < ? GROUP BY CAST(NgayLap AS DATE) ORDER BY Ngay";
        LocalDate fromDate = (start != null) ? start : LocalDate.of(2020,1,1);
        LocalDate toDateExclusive = ((end != null) ? end : LocalDate.now()).plusDays(1);
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setDate(1, Date.valueOf(fromDate));
            ps.setDate(2, Date.valueOf(toDateExclusive));
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    java.sql.Date d = rs.getDate("Ngay");
                    ds.add(new com.quickmanager.model.DoanhThuNgay(d.toLocalDate(), rs.getInt("SoHoaDon"), rs.getBigDecimal("DoanhThu")));
                }
            }
        }catch (Exception e){ logger.log(Level.SEVERE, "Lỗi ngoại lệ", e); Address.printAddress(); }
        return ds;
    }

    public static java.util.List<com.quickmanager.model.DoanhThuNgay> getSoLuongTheoNgay(LocalDate start, LocalDate end) {
        java.util.List<com.quickmanager.model.DoanhThuNgay> ds = new java.util.ArrayList<>();
        String sql = "SELECT CAST(hd.NgayLap AS DATE) AS Ngay, SUM(ct.SoLuong) AS SoLuong, SUM(ct.ThanhTien) AS DoanhThu FROM CT_HOA_DON ct JOIN HOA_DON hd ON ct.MaHoaDon = hd.MaHoaDon WHERE hd.NgayLap >= ? AND hd.NgayLap < ? GROUP BY CAST(hd.NgayLap AS DATE) ORDER BY Ngay";
        LocalDate fromDate = (start != null) ? start : LocalDate.of(2020,1,1);
        LocalDate toDateExclusive = ((end != null) ? end : LocalDate.now()).plusDays(1);
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setDate(1, Date.valueOf(fromDate));
            ps.setDate(2, Date.valueOf(toDateExclusive));
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    java.sql.Date d = rs.getDate("Ngay");
                    int soLuong = rs.getInt("SoLuong");
                    java.math.BigDecimal doanhThu = rs.getBigDecimal("DoanhThu");
                    ds.add(new com.quickmanager.model.DoanhThuNgay(d.toLocalDate(), soLuong, doanhThu));
                }
            }
        }catch (Exception e){ logger.log(Level.SEVERE, "Lỗi ngoại lệ", e); Address.printAddress(); }
        return ds;
    }

    public static java.util.List<com.quickmanager.model.TopSanPham> getTopSanPham(LocalDate start, LocalDate end, int limit) {
        java.util.List<com.quickmanager.model.TopSanPham> ds = new java.util.ArrayList<>();
        String sql = "SELECT sp.TenSanPham, SUM(ct.SoLuong) AS SoLuong, SUM(ct.ThanhTien) AS DoanhThu FROM CT_HOA_DON ct JOIN HOA_DON hd ON ct.MaHoaDon = hd.MaHoaDon JOIN SAN_PHAM sp ON ct.MaSanPham = sp.MaSanPham WHERE hd.NgayLap >= ? AND hd.NgayLap < ? GROUP BY sp.TenSanPham ORDER BY SUM(ct.SoLuong) DESC";
        LocalDate fromDate = (start != null) ? start : LocalDate.of(2020,1,1);
        LocalDate toDateExclusive = ((end != null) ? end : LocalDate.now()).plusDays(1);
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setDate(1, Date.valueOf(fromDate));
            ps.setDate(2, Date.valueOf(toDateExclusive));
            try (ResultSet rs = ps.executeQuery()){
                int count = 0;
                while (rs.next()) {
                    ds.add(new com.quickmanager.model.TopSanPham(rs.getString("TenSanPham"), rs.getInt("SoLuong"), rs.getBigDecimal("DoanhThu")));
                    count++; if (count >= limit) break;
                }
            }
        }catch (Exception e){ logger.log(Level.SEVERE, "Lỗi ngoại lệ", e); Address.printAddress(); }
        return ds;
    }

    public static final String sqlGetCTHD = """
        SELECT 
            ROW_NUMBER() OVER (ORDER BY sp.MaSanPham) AS STT,
            hd.*,
            sp.*
        FROM CT_HOA_DON AS hd
        JOIN SAN_PHAM AS sp 
            ON hd.MaSanPham = sp.MaSanPham
        WHERE hd.MaHoaDon = ?
        """;
    public static List<CT_HoaDon> getCTHD(int maHD) {
        List<CT_HoaDon> ds = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlGetCTHD)) {
            ps.setInt(1, maHD);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ds.add( new CT_HoaDon(
                            rs.getInt("STT"),
                            rs.getString("TenSanPham"),
                            rs.getString("DonViTinh"),
                            rs.getInt("SoLuong"),
                            rs.getBigDecimal("DonGia"),
                            rs.getBigDecimal("ThanhTien")
                    ));
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

    public  static final String sqlKH = """
            Select * From KHACH_HANG
            Where MaKhachHang = ?
            """;

    public static KhachHang getInfoKH(int id) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlKH)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new KhachHang(
                            rs.getInt("MaKhachHang"),
                            rs.getString("TenKhachHang"),
                            rs.getString("SoDienThoai"),
                            rs.getString("Email"),
                            rs.getString("DiaChi")
                    );
                }
            }catch (SQLException e) {
                logger.log(Level.SEVERE, "Lỗi ngoại lệ", e);
                Address.printAddress();
            }
        }catch (SQLException e ) {
            logger.log(Level.SEVERE, "Lỗi ngoại lệ", e);
            Address.printAddress();
        }
        return null;
    }
}


