package com.quickmanager.config;

import com.quickmanager.debug.Address;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL =
            "jdbc:sqlserver://localhost:1433;databaseName=QL_SIEU_THI;encrypt=true;trustServerCertificate=true";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123456789";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
            Address.printAddress();
            return null;
        }

    }
}