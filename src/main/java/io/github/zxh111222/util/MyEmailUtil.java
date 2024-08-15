package io.github.zxh111222.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.util.Properties;

/**
 * @author XinhaoZheng
 * @version 1.0
 * @description: TODO
 * @date 2024/7/29 16:48
 */
public class MyEmailUtil {

    public static void send(String sender, String secret_token, String receiver, String content)  throws Exception {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.qq.com");
        prop.put("mail.smtp.port", "587");

        // https://wx.mail.qq.com/list/readtemplate?name=app_intro.html#/agreement/authorizationCode
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, secret_token);
            }
        });

        Message message = new MimeMessage(session);
        // who you are
        message.setFrom(new InternetAddress(sender));
        // send to ...
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));

        message.setSubject("来自 Java 的提醒");

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(content, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);

        System.out.println("邮件发送成功");
    }

}
