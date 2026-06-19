package com.quickmanager.config;

import com.quickmanager.debug.AppLogger;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    private static final Logger logger = AppLogger.getLogger(DBConnection.class);
    
    private static final String URL =
            "jdbc:sqlserver://localhost:1433;databaseName=QL_SIEU_THI;encrypt=true;trustServerCertificate=true";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123456";

    private static HikariDataSource dataSource;

    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(URL);
            config.setUsername(USERNAME);
            config.setPassword(PASSWORD);
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setConnectionTimeout(10000);
            
            dataSource = new HikariDataSource(config);
            logger.info("HikariCP Connection Pool initialized.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Không thể khởi tạo Connection Pool", e);
        }
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Lỗi lấy kết nối từ pool", e);
            return null;
        }
    }

    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            logger.info("HikariCP Connection Pool closed.");
        }
    }
}