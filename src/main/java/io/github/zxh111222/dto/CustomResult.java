package io.github.zxh111222.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author XinhaoZheng
 * @version 1.0
 * @description: TODO
 * @date 2024/7/30 10:35
 */
public class CustomResult {
    private String title;
    private String url;
    private Date createdAt;
    private Date updatedAt;

    // private static final SimpleDateFormat dateFormat_1 = new SimpleDateFormat("yyyy-MM-dd");
    // private static final SimpleDateFormat dateFormat_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public CustomResult(String title, String url, Date createdAt, Date updatedAt) {
        this.title = title;
        this.url = url;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        /*try {
            // 将字符串解析为 Date 对象
            if (createdAt != null && !createdAt.isEmpty()) {
                this.createdAt = dateFormat_1.parse(createdAt);
            } else {
                this.createdAt = null;
            }
            if (updatedAt != null && !updatedAt.isEmpty()) {
                this.updatedAt = dateFormat_2.parse(updatedAt);
            } else {
                this.updatedAt = null;
            }
        } catch (ParseException e) {
            e.printStackTrace(); // 处理解析异常
        }*/
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "CustomResult{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
