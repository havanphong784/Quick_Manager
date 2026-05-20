package com.quickmanager.config;

import com.quickmanager.debug.AppLogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    private static final Logger logger = AppLogger.getLogger(DBConnection.class);

    private static final String URL =
            "jdbc:sqlserver://localhost:1433;databaseName=QL_SIEU_THI;encrypt=true;trustServerCertificate=true";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Không thể kết nối database", e);
            return null;
        }
    }
}