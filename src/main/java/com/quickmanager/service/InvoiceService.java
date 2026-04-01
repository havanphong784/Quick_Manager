package com.quickmanager.service;

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
    public static final String sqlInsertHD = """
    Insert Into HOA_DON ( MaNhanVien,MaKhachHang,TongTien,GiamGia,TienKhachDua,TienThoi)
    Values(?,?,?,?,?,?);
    """;
    private static final String sqlInsertCTHD =
            "INSERT INTO CT_HOA_DON (MaHoaDon, MaSanPham, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";

    private static final String sqlUpdateSP =
            "UPDATE SAN_PHAM SET SoLuongTon = SoLuongTon - ? " +
                    "WHERE MaSanPham = ? AND SoLuongTon >= ?";

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
                    psUDSP.setInt(2, items.getMaSanPham());
                    psUDSP.setInt(3, items.getSoLuong());
                    int ktUDSP = psUDSP.executeUpdate();
                    if (ktUDSP != 1) throw new SQLException("Cập nhật sản phẩm thất bại");

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
                    ds.add(new HoaDon(
                            rs.getInt("MaHoaDon"),
                            rs.getInt("MaKhachHang"),
                            rs.getString("TenKhachHang"),
                            rs.getDate("NgayLap").toLocalDate(),
                            rs.getBigDecimal("TongTien"),
                            rs.getBigDecimal("GiamGia")));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Address.printAddress();
        }
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
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sqlGetCTHD);
            ps.setInt(1, maHD);
            try {
                ResultSet rs = ps.executeQuery();
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
                System.out.println(e.getMessage());
                Address.printAddress();
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            Address.printAddress();
        }
        return ds;
    }

    public  static final String sqlKH = """
            Select * From KHACH_HANG
            Where MaKhachHang = ?
            """;

    public static KhachHang getInfoKH(int id) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sqlKH);
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
                System.out.println(e.getMessage());
                Address.printAddress();
            }
        }catch (SQLException e ) {
            System.out.println(e.getMessage());
            Address.printAddress();
        }
        return null;
    }
}


