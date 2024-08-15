package io.github.zxh111222.downloader;


import io.github.zxh111222.util.MyReflectionUtil;

import java.io.IOException;

public interface Downloader {

    static Downloader getInstance(String downloader) {
        return MyReflectionUtil.getInstance(downloader);
    }

    String download(String url) throws IOException;

}
