package io.github.zxh111222.storage;


import io.github.zxh111222.dto.CustomResult;

import java.io.*;
import java.util.*;

/**
 * @author XinhaoZheng
 * @version 1.0
 * @description: TODO
 * @date 2024/6/9 14:14
 */
public class FileStorage implements Storage {
    @Override
    public void save(List<CustomResult> information) throws IOException {
        File file = new File("src/main/java/io/github/zxh111222/resources/information.data");
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            for (CustomResult cr : information) {
                pw.println(cr.getTitle());
                pw.println(cr.getUrl());
                pw.println(cr.getCreatedAt());
                pw.println(cr.getUpdatedAt());
                pw.println("--- --- ---");
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
