package io.github.zxh111222.parser;

import io.github.zxh111222.dto.CustomResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
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

        for (Element e : es) {
            // 过滤公告、置顶
            Element noticeAndTop = e.select("td.icon a i").first();
            if (noticeAndTop.hasAttr("alt") && "置顶帖标志".equals(noticeAndTop.attr("alt"))) {
                continue;
            }
            // todo: 举例可扩展的点: 按时间过滤
            // 记录本次抓取的最新的/最大的那个时间 (前提是时间有排序的)
            // http://bbs.xmfish.com/thread-htm-fid-55-search-all-orderway-lastpost-asc-DESC-page-1.html
            // 按最后回复时间排序的地址如上
            // 比如: 2024-08-15 12:40
            // 作为下一次程序运行的起始入库时间点
            // 次程序启动，解析到每一个帖子，拿这个帖子大的时间跟上面这个时间对比
            //      如果比它早，就可以不入库
            //          甚至可以直接结束 for 循环， 因为我们是按时间倒序的，那后面的帖子肯定都更早
            //      如果解析到最后一条帖子，它的时间大于等于上次抓取的时间点，说明应该再抓下一页
            // 增量采集




            //解析数据
            Element titleElement = e.select("td.subject a.subject_t").first();
            String title = titleElement.text();
            String url = titleElement.attr("href");
            url = "http://bbs.xmfish.com/" + url;
            String createdAt = e.select("td.author").first().select("p").text();
            String updatedAt = e.select("td.author").eq(1).select("p > a").attr("title");
            parserList.add(new CustomResult(title, url, createdAt, updatedAt));
        }

        return parserList;
    }
}
