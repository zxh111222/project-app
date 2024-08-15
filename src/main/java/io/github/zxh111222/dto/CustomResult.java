package io.github.zxh111222.dto;

/**
 * @author XinhaoZheng
 * @version 1.0
 * @description: TODO
 * @date 2024/7/30 10:35
 */
public class CustomResult {
    private String title;
    private String url;
    private String createdAt;
    private String updatedAt;

    public CustomResult(String title, String url, String createdAt, String updatedAt) {
        this.title = title;
        this.url = url;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
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
