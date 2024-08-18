package io.github.zxh111222.parser;

import io.github.zxh111222.dto.CustomResult;
import io.github.zxh111222.util.MyDBUtil;
import io.github.zxh111222.util.MyDateConversionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HangzhouSecondhandParser implements Parser {
    @Override
    public List<CustomResult> parse(String html) {
        List<CustomResult> parserList = new ArrayList<>();
        Document doc = null;
        doc = Jsoup.parse(html);
        Elements elements = doc.select("div[class=info-content]");

        Date lastCrawlDate = getLastCrawlDateFromDB(); // 获取上次采集的最大时间
        Date newCrawlDate = lastCrawlDate; // 初始化为上次采集时间

        System.out.println("本次从" + MyDateConversionUtil.formatDate(lastCrawlDate) + "时间点开始获取");

        for (Element element : elements) {
            String title = element.select("h4[class=hoverLine]").text();
            String url = element.select("a").attr("href");
            url = "https://info.19lou.com" + url;
            String createdAtStr = element.select("p[class=info-content-time]").text();

            int startIndex = Math.max(0, createdAtStr.length() - 20);
            createdAtStr = createdAtStr.substring(startIndex);
            String updatedAtStr = null;

            // 将时间字符串转换为Date对象
            Date createdAt = MyDateConversionUtil.parseDate(createdAtStr);
            Date updatedAt = null;

            // 时间过滤逻辑
            if (createdAt != null && createdAt.compareTo(lastCrawlDate) <= 0) {
                break; // 结束循环
            }

            // 更新最大时间
            if (createdAt != null && (newCrawlDate == null || createdAt.after(newCrawlDate))) {
                newCrawlDate = createdAt;
            }

            parserList.add(new CustomResult(title, url, createdAtStr, updatedAtStr));
        }

        // 保存最新的抓取时间
        if (newCrawlDate != null) {
            saveLastCrawlDateToDB(newCrawlDate);
        }
        System.out.println("本次获取到" + parserList.size() + "条数据");


        return parserList;
    }



    private Date getLastCrawlDateFromDB() {
        String getParserQuery = "SELECT parser FROM url_parse WHERE name = '杭州二手'";
        String getLastCrawlDateQuery = "SELECT lastCrawlDate FROM url_parse WHERE parser = ?";

        try {
            Connection connection = MyDBUtil.getConnection();
            // 获取 parser 值
            PreparedStatement parserStmt = connection.prepareStatement(getParserQuery);
            ResultSet parserRs = parserStmt.executeQuery();

            if (parserRs.next()) {
                String parser = parserRs.getString("parser");

                // 获取对应的 lastCrawlDate
                PreparedStatement lastCrawlDateStmt = connection.prepareStatement(getLastCrawlDateQuery);
                lastCrawlDateStmt.setString(1, parser);
                ResultSet lastCrawlDateRs = lastCrawlDateStmt.executeQuery();

                if (lastCrawlDateRs.next()) {
                    Timestamp lastCrawlDate = lastCrawlDateRs.getTimestamp("lastCrawlDate");

                    // 如果 lastCrawlDate 为空，赋值为默认日期
                    if (lastCrawlDate == null) {
                        return new java.util.Date(java.sql.Date.valueOf("2024-01-01").getTime());
                    }
                    return new java.util.Date(lastCrawlDate.getTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new java.util.Date(java.sql.Date.valueOf("2024-01-01").getTime());
    }


    private void saveLastCrawlDateToDB(Date newCrawlDate) {
        String getParserQuery = "SELECT parser FROM url_parse WHERE name = '杭州二手'";
        String updateLastCrawlDateQuery = "UPDATE url_parse SET lastCrawlDate = ? WHERE parser = ?";

        try {
            Connection connection = MyDBUtil.getConnection();
            // 获取 parser 值
            PreparedStatement parserStmt = connection.prepareStatement(getParserQuery);
            ResultSet parserRs = parserStmt.executeQuery();

            if (parserRs.next()) {
                String parser = parserRs.getString("parser");

                // 更新对应的 lastCrawlDate
                PreparedStatement updateStmt = connection.prepareStatement(updateLastCrawlDateQuery);
                updateStmt.setTimestamp(1, new Timestamp(newCrawlDate.getTime()));
                updateStmt.setString(2, parser);
                updateStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
