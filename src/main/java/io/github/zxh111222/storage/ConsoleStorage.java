package io.github.zxh111222.storage;


import io.github.zxh111222.dto.CustomResult;

import java.util.List;

/**
 * @author XinhaoZheng
 * @version 1.0
 * @description: TODO
 * @date 2024/6/9 14:14
 */
public class ConsoleStorage implements Storage {
    @Override
    public void save(List<CustomResult> information) {
        for (CustomResult cr : information) {
            System.out.println(cr.getTitle());
            System.out.println(cr.getUrl());
            System.out.println(cr.getCreatedAt());
            System.out.println(cr.getUpdatedAt());
            System.out.println("--- --- ---");
        }
    }
}
