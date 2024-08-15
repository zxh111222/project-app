package io.github.zxh111222.downloader;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * @author XinhaoZheng
 * @version 1.0
 * @description: TODO
 * @date 2024/6/8 18:37
 */
public class MyIODownloader implements Downloader {
    public MyIODownloader() {
        System.out.println("MyIODownloader.MyIODownloader");
    }

    @Override
    public String download(String url) {
        String html = "";
        BufferedReader in = null;
        try {
            URL oracle = new URL(url);
            URLConnection uc = null;
            uc = oracle.openConnection();
            uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36");
            in = new BufferedReader(new InputStreamReader(uc.getInputStream(), Charset.forName("gbk")));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                html += inputLine;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                System.out.println("IO 异常");
                throw new RuntimeException(e);
            }
        }

        return html;
    }
}
