package io.github.zxh111222.notificator;


import io.github.zxh111222.dto.CustomResult;

import java.util.List;

import static io.github.zxh111222.App.PROPERTIES;

/**
 * @author XinhaoZheng
 * @version 1.0
 * @description: TODO
 * @date 2024/6/11 9:01
 */
public class ConsoleNotificator implements Notificator {
    @Override
    public void notify(List<CustomResult> information) {
        // 获取信息并发送到控制台
        String msg = Notificator.getMsgFromResult(information);
        if (!msg.isBlank()) {
            String to = PROPERTIES.getProperty("to");
            System.out.println("--- --- ---");
            System.out.println("Hi, " + to);
            System.out.println();
            System.out.println(msg);
            System.out.println("--- --- ---");
            System.out.println("发送完毕!");
        } else {
            System.out.println("没有命中任何关键词，无需发送通知");
        }

    }
}
