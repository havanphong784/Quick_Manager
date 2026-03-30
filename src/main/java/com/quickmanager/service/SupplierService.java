package com.quickmanager.service;

import com.quickmanager.config.DBConnection;
import com.quickmanager.debug.Address;
import com.quickmanager.model.NhaCungCap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SupplierService {
    public static final String sqlGetNCC = """
            Select * From NHA_CUNG_CAP
            """;

    public static List<NhaCungCap> getNCC() {
        List<NhaCungCap> ds = new ArrayList<NhaCungCap>();
         try (Connection con = DBConnection.getConnection();
              PreparedStatement ps = con.prepareStatement(sqlGetNCC);
         ) {
             try {
                 ResultSet rs = ps.executeQuery();
                 while (rs.next()) {
                     NhaCungCap ncc = new NhaCungCap(
                             rs.getInt("MaNCC"),
                             rs.getString("TenNCC"),
                             rs.getString("SoDienThoai"),
                             rs.getString("Email"),
                             rs.getString("DiaChi"),
                             rs.getString("TrangThai")
                     );
                     ds.add(ncc);
                 }
             }catch (Exception e) {
                 System.out.println("Loi connect");
                 Address.printAddress();
             }
         }catch (Exception e) {
             System.out.println("Loi connect");
             Address.printAddress();
         }
         return ds;
    }
}

