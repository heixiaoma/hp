package net.hserver.hp.server.utils;

import cn.hserver.core.server.util.PropUtil;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtils {
    /**
     *
     * @param email     接收者邮箱
     * @param subject   邮件主题
     * @param emailMsg  邮件内容
     * @throws AddressException
     * @throws MessagingException
     */
    public static boolean sendMail(String email, String subject,String emailMsg) {
        try {
            //创建配置文件
            final Properties props = new Properties();
            // 发送服务器需要身份验证
            props.setProperty("mail.smtp.auth", "true");
            // 设置邮件服务器主机名
            props.setProperty("mail.host", PropUtil.getInstance().get("mail.host"));
            props.setProperty("mail.smtp.port", PropUtil.getInstance().get("mail.port"));
            props.setProperty("mail.transport.protocol", "smtp");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            // 服务端口号
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.starttls.required", "true");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    //return new PasswordAuthentication("用户名", "密码");
                    //注意qq邮箱需要去qq邮箱的设置中获取授权码，并将授权码作为密码来填写
                    return new PasswordAuthentication(PropUtil.getInstance().get("mail.username"), PropUtil.getInstance().get("mail.password"));
                }
            };
            //创建session域
            Session session = Session.getInstance(props, auth);
            Message message = new MimeMessage(session);
            //设置邮件发送者,与PasswordAuthentication中的邮箱一致即可
            message.setFrom(new InternetAddress("HP-Proxy<"+PropUtil.getInstance().get("mail.username")+">"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //设置邮件主题
            message.setSubject(subject);
            //设置邮件内容
            message.setContent(emailMsg, "text/html;charset=utf-8");
            //发送邮件
            Transport.send(message);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        sendMail("1417262058@qq.com","a","b");
    }
}
