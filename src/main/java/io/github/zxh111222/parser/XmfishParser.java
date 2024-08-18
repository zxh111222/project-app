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

/**
 * @author XinhaoZheng
 * @version 1.0
 * @description: 解析厦门小鱼网
 * @date 2024/6/8 18:37
 */
public class XmfishParser implements Parser {
    @Override
    public List<CustomResult> parse(String html) {
        List<CustomResult> parserList = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Elements es = doc.select("tbody[id=threadlist]").select("tr[class=tr3]");

        Date lastCrawlDate = getLastCrawlDateFromDB(); // 获取上次采集的最大时间
        Date newCrawlDate = lastCrawlDate; // 初始化为上次采集时间

        System.out.println("本次从" + MyDateConversionUtil.formatDate(lastCrawlDate) + "时间点开始获取");

        for (Element e : es) {
            // 过滤公告、置顶
            Element noticeAndTop = e.select("td.icon a i").first();
            if (noticeAndTop.hasAttr("alt") && "置顶帖标志".equals(noticeAndTop.attr("alt"))) {
                continue;
            }

            //解析数据
            Element titleElement = e.select("td.subject a.subject_t").first();
            String title = titleElement.text();
            String url = titleElement.attr("href");
            url = "http://bbs.xmfish.com/" + url;
            String createdAtStr = e.select("td.author").first().select("p").text();
            String updatedAtStr = e.select("td.author").eq(1).select("p > a").attr("title");

            // todo: 举例可扩展的点: 按时间过滤
            // 记录本次抓取的最新的/最大的那个时间 (前提是时间有排序的)
            // http://bbs.xmfish.com/thread-htm-fid-55-search-all-orderway-lastpost-asc-DESC-page-1.html
            // 按最后回复时间排序的地址如上
            // 比如: 2024-08-15 12:40
            // 作为下一次程序运行的起始入库时间点
            // 每次程序启动，解析到每一个帖子，拿这个帖子大的时间跟上面这个时间对比
            //      如果比它早，就可以不入库
            //          甚至可以直接结束 for 循环， 因为我们是按时间倒序的，那后面的帖子肯定都更早
            //      如果解析到最后一条帖子，它的时间大于等于上次抓取的时间点，说明应该再抓下一页
            // 增量采集

            // 将时间字符串转换为Date对象
            Date createdAt = MyDateConversionUtil.parseDate(createdAtStr);
            Date updatedAt = MyDateConversionUtil.parseDate(updatedAtStr);

            // 时间过滤逻辑
            if (updatedAt != null && updatedAt.compareTo(lastCrawlDate) <= 0) {
                break; // 结束循环
            }

            // 更新最大时间
            if (updatedAt != null && (newCrawlDate == null || updatedAt.after(newCrawlDate))) {
                newCrawlDate = updatedAt;
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
        String getParserQuery = "SELECT parser FROM url_parse WHERE name = '厦门小鱼'";
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
        String getParserQuery = "SELECT parser FROM url_parse WHERE name = '厦门小鱼'";
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
