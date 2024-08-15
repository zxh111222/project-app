package io.github.zxh111222.downloader;

import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * @author XinhaoZheng
 * @version 1.0
 * @description: TODO
 * @date 2024/6/8 18:35
 */
public class JsoupDownloader implements Downloader {

    public JsoupDownloader() {
        System.out.println("JsoupDownloader.JsoupDownloader");
    }

    @Override
    public String download(String url) {
        String html = null;
        try {
            html = Jsoup.connect(url).get().html();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return html;
    }
}
