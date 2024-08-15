package io.github.zxh111222.storage;

import io.github.zxh111222.dto.CustomResult;
import io.github.zxh111222.storage.Storage;
import io.github.zxh111222.util.MyDBUtil;

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
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;
        String checkSql = "SELECT COUNT(*) FROM information WHERE url = ?;";
        String insertSql = "INSERT INTO information (title, url, createdAt, updatedAt) VALUES (?, ?, ?, ?);";

        try {
            // 获取数据库连接
            Connection connection = MyDBUtil.getConnection();
            // 准备查询和插入的 SQL 语句
            checkStmt = connection.prepareStatement(checkSql);
            insertStmt = connection.prepareStatement(insertSql);
            for (CustomResult result : information) {
                // 检查 URL 是否存在
                checkStmt.setString(1, result.getUrl());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    // URL 已存在，跳过
                    System.out.println("URL 已存在: " + result.getUrl() + "  ---跳过");
                    continue;
                }

                // 插入
                insertStmt.setString(1, result.getTitle());
                insertStmt.setString(2, result.getUrl());
                if (result.getCreatedAt() != null) {
                    insertStmt.setTimestamp(3, new Timestamp(result.getCreatedAt().getTime()));
                } else {
                    insertStmt.setNull(3, Types.TIMESTAMP);
                }

                if (result.getUpdatedAt() != null) {
                    insertStmt.setTimestamp(4, new Timestamp(result.getUpdatedAt().getTime()));
                } else {
                    insertStmt.setNull(4, Types.TIMESTAMP);
                }
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭资源
            if (checkStmt != null) {
                checkStmt.close();
            }
            if (insertStmt != null) {
                insertStmt.close();
            }

        }
    }

}
