package io.github.zxh111222.parser;

import io.github.zxh111222.dto.CustomResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HangzhouSecondhandParser implements Parser {
    @Override
    public List<CustomResult> parse(String html) {
        List<CustomResult> parserList = new ArrayList<>();
        Document doc = null;
        doc = Jsoup.parse(html);
        Elements elements = doc.select("div[class=info-content]");
        for (Element element : elements) {
            String title = element.select("h4[class=hoverLine]").text();
            String url = element.select("a").attr("href");
            url = "https://info.19lou.com" + url;
            String createdAt = element.select("p[class=info-content-time]").text();

            int startIndex = Math.max(0, createdAt.length() - 20);
            createdAt = createdAt.substring(startIndex);

            String updatedAt = null;

            parserList.add(new CustomResult(title, url, createdAt, updatedAt));
        }

        return parserList;
    }
}
