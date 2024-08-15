package io.github.zxh111222.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDBUtil {
    private static Connection connection;

    public synchronized static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "root", "123456");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    // 创建数据库
    private static void createDatabaseIfNotExists(Connection connection) throws SQLException {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String createDbSql = "CREATE DATABASE IF NOT EXISTS app;";
            stmt.execute(createDbSql);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    // 创建表结构
    private static void createTableIfNotExists(Connection connection) throws SQLException {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String createTableSql = "CREATE TABLE IF NOT EXISTS information (" +
                    "`id` INT AUTO_INCREMENT PRIMARY KEY, " +
                    "`title` VARCHAR(255) DEFAULT NULL, " +
                    "`url` VARCHAR(255) NOT NULL UNIQUE, " +
                    "`createdAt` DATE DEFAULT NULL, " +
                    "`updatedAt` DATETIME DEFAULT NULL" +
                    ");";
            stmt.execute(createTableSql);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}
