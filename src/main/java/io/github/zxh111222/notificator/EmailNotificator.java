package io.github.zxh111222.notificator;


import io.github.zxh111222.dto.CustomResult;
import io.github.zxh111222.util.MyEmailUtil;

import java.util.List;

import static io.github.zxh111222.App.PROPERTIES;


/**
 * @author XinhaoZheng
 * @version 1.0
 * @description: TODO
 * @date 2024/6/11 9:01
 */
public class EmailNotificator implements Notificator {
    @Override
    public void notify(List<CustomResult> information) throws Exception {
        // 处理、获取关键词
        int keywordCount = 0;
        String keywords = PROPERTIES.getProperty("keywords");
        String[] keyword = keywords.split(",");
        // 提取配置文件相关信息
        String secret_token = PROPERTIES.getProperty("secret_token");
        String from = PROPERTIES.getProperty("from");
        String to = PROPERTIES.getProperty("to");
        int fromIndex = from.indexOf(".");
        int toIndex = to.indexOf(".");
        String sender = from.substring(fromIndex + 1);
        String receiver = to.substring(toIndex + 1);
        //System.out.println(sender + " -> " + receiver);
        String msg = Notificator.getMsgFromResult(information);
        if (!msg.isBlank()) {
            try {
                msg = msg.replaceAll("\\n", "<br>");
                MyEmailUtil.send(sender, secret_token, receiver, msg);
            } catch (Exception e) {
                System.out.println("邮件发送失败！！！");
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("没有命中任何关键词，无需发送通知");
        }
    }
}
