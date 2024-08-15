package io.github.zxh111222.storage;

import io.github.zxh111222.dto.CustomResult;

import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 * @author XinhaoZheng
 * @version 1.0
 * @description: TODO
 * @date 2024/8/12 9:24
 */
public class DbStorage implements Storage {
    @Override
    public void save(List<CustomResult> information) throws IOException, SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            // 数据库连接，首先尝试连接到 MySQL Server
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/xmfish", "root", "123456");

            // 创建表结构
            createTableIfNotExists(connection);

            String insertSql = "INSERT INTO xmfish (title, url, createdAt, updatedAt) VALUES (?, ?, ?, ?);";
            pstmt = connection.prepareStatement(insertSql);

            connection.setAutoCommit(false);

            // 插入每一条记录
            for (CustomResult result : information) {
                pstmt.setString(1, result.getTitle());
                pstmt.setString(2, result.getUrl());
                pstmt.setString(3, result.getCreatedAt());
                pstmt.setString(4, result.getUpdatedAt());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            if (connection != null && !connection.getAutoCommit()) {
                connection.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            // 关闭资源
            if (pstmt != null) {
                pstmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }


    // 创建表结构
    private void createTableIfNotExists(Connection connection) throws SQLException {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String createTableSql = "CREATE TABLE IF NOT EXISTS xmfish (" +
                                    "`id` INT AUTO_INCREMENT PRIMARY KEY, " +
                                    "`title` VARCHAR(255) NOT NULL, " +
                                    "`url` VARCHAR(255) NOT NULL, " +
                                    "`createdAt` VARCHAR(50) NOT NULL, " +
                                    "`updatedAt` VARCHAR(50) NOT NULL" +
                                    ");";
            stmt.execute(createTableSql);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    private static void select(Connection connection) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            String selectSql = "select id, title, url, createdAt, updatedAt from xmfish;";
            rs = stmt.executeQuery(selectSql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String url = rs.getString("url");
                String createdAt = rs.getString("createdAt");
                String updatedAt = rs.getString("updatedAt");
                System.out.println(id + "\t" + title + "\t" + url + "\t" + createdAt + "\t" + updatedAt);
            }
        } finally {
            // 关闭资源
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

    }

}
