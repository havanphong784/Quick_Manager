package com.quickmanager.service;

import com.quickmanager.config.DBConnection;
import com.quickmanager.debug.Address;
import com.quickmanager.debug.AppLogger;
import com.quickmanager.model.CT_PhieuNhap;
import com.quickmanager.model.NhaCungCap;
import com.quickmanager.model.PhieuNhap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SupplierService {
    private static final Logger logger = AppLogger.getLogger(SupplierService.class);
    public static final String sqlGetNCC = """
            Select * From NHA_CUNG_CAP
            """;

    public static final String sqlPhieuNhap = """
            Insert into PHIEU_NHAP (MaNCC, TongTien, MaNhanVien)
            Values(?,?,?)
            """;

    public static final String sqlCTPhieuNhap = """
            Insert into CT_PHIEU_NHAP (MaPhieuNhap, MaSanPham, SoLuong, GiaNhap, ThanhTien)
            Values(?,?,?,?,?)
            """;

    public static final String sqlUpdateSP = """
            Update SAN_PHAM Set SoLuongTon = SoLuongTon + ?,
                            TrangThai = N'Đang bán'
            Where MaSanPham = ?
            """;

    public static final String sqlTaoNCC = """
            Insert into NHA_CUNG_CAP (TenNCC, SoDienThoai, Email, DiaChi)
            Values(?,?,?,?)
            """;

    public static NhaCungCap taoNCC(String name, String sdt, String email, String diaChi) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement psTaoNCC = con.prepareStatement(sqlTaoNCC, Statement.RETURN_GENERATED_KEYS)) {
            psTaoNCC.setString(1, name);
            psTaoNCC.setString(2, sdt);
            psTaoNCC.setString(3, email);
            psTaoNCC.setString(4, diaChi);
            int kt = psTaoNCC.executeUpdate();
            if (kt == 0) throw new SQLException("Tạo nhà cung cấp thất bại");
            try (ResultSet rs = psTaoNCC.getGeneratedKeys()) {
                if (rs.next()) {
                    return new NhaCungCap(
                            rs.getInt(1),
                            name,
                            sdt,
                            email,
                            diaChi
                    );
                }
            }
        }
        return null;
    }

    public static List<NhaCungCap> getNCC() {
        List<NhaCungCap> ds = new ArrayList<NhaCungCap>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlGetNCC);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap(
                        rs.getInt("MaNCC"),
                        rs.getString("TenNCC"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("DiaChi")
                );
                ds.add(ncc);
            }
        } catch (Exception e) {
            logger.info("Loi connect");
            Address.printAddress();
        }
        return ds;
    }

    public static int taoPhieuNhap(PhieuNhap pn, List<CT_PhieuNhap> ds) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement psPN = con.prepareStatement(sqlPhieuNhap, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement psCTPN = con.prepareStatement(sqlCTPhieuNhap);
                 PreparedStatement psUDSP = con.prepareStatement(sqlUpdateSP)) {

                // PN
                psPN.setInt(1, pn.getMaNCC());
                psPN.setBigDecimal(2, pn.getTongTien());
                psPN.setInt(3, SessionService.getUser().getMaNhanVien());

                int ktPN = psPN.executeUpdate();
                if (ktPN == 0) throw new SQLException("Tạo phiếu nhập thất bại");

                // CTPN
                int maPhieuNhap;
                try (ResultSet rs = psPN.getGeneratedKeys()) {
                    if (rs.next()) maPhieuNhap = rs.getInt(1);
                    else throw new SQLException("Lấy mã phiếu nhập thất bại");
                }

                for (CT_PhieuNhap ct : ds) {
                    psUDSP.setInt(1, ct.getSoLuong());
                    psUDSP.setInt(2, ct.getMaSanPham());
                    int ktUDSP = psUDSP.executeUpdate();
                    if (ktUDSP != 1) throw new SQLException("Cập nhật sản phẩm thất bại");

                    psCTPN.setInt(1, maPhieuNhap);
                    psCTPN.setInt(2, ct.getMaSanPham());
                    psCTPN.setInt(3, ct.getSoLuong());
                    psCTPN.setBigDecimal(4, ct.getGiaNhap());
                    psCTPN.setBigDecimal(5, ct.getThanhTien());
                    psCTPN.executeUpdate();
                }
                con.commit();
                ProductService.refreshProductCache();
                return maPhieuNhap;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }
}

