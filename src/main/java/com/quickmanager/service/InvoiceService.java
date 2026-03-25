package com.quickmanager.service;

import com.quickmanager.config.DBConnection;
import com.quickmanager.model.CT_HoaDon;
import com.quickmanager.model.HoaDon;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.List;

public class InvoiceService {
    public static final String sqlInsertHD = """
    Insert Into HOA_DON ( MaNhanVien,MaKhachHang,TongTien,TienKhachDua,TienThoi)
    Values(?,?,?,?,?);
    """;
    private static final String sqlInsertCTHD =
            "INSERT INTO CT_HOA_DON (MaHoaDon, MaSanPham, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";

    private static final String sqlUpdateSP =
            "UPDATE SAN_PHAM SET SoLuongTon = SoLuongTon - ? " +
                    "WHERE MaSanPham = ? AND SoLuongTon >= ?";

    public int taoHoaDonNKH(HoaDon hd, List<CT_HoaDon> ds) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try  (PreparedStatement psHD = con.prepareStatement(sqlInsertHD, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement psCTHD = con.prepareStatement(sqlInsertCTHD);
                PreparedStatement psUDSP =  con.prepareStatement(sqlUpdateSP)) {

                // HD
                psHD.setInt(1, hd.getMaNhanVien());
                if (hd.getMaKhachHang() == null) psHD.setNull(2, Types.INTEGER);
                else psHD.setInt(2, hd.getMaKhachHang());
                psHD.setBigDecimal(3, hd.getTongTien());
                psHD.setBigDecimal(4, hd.getTienKhachDua());
                psHD.setBigDecimal(5, hd.getTienThoi());

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
}


