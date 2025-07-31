package com.tekon;

import java.security.GeneralSecurityException;
import java.util.Properties;
// Java内置工具
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

// 第三方包
import javax.mail.*; // 利用maven引入的javax 邮件包
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory; // 使用SSL协议进行安全的认证传输

public class App 
{
    public static boolean is_valid_email(String email){
        Pattern email_format = Pattern.compile("^[\\w]+@[\\w]+\\.[\\w]+$"); // 随便搓的一个邮件地址RE效性判断 (baka@qq.com 有效
        boolean judge = email_format.matcher(email).matches();
        if (!judge) {
            System.out.println("输入的邮箱地址不合法! 请重试...");
        }
        return judge;
    }

    public static void main( String[] args ) throws GeneralSecurityException{
        System.out.println("欢迎来到Javax Email中心~");
        Scanner user_input = new Scanner(System.in);
        String sender_address = "2772301380@qq.com";
        String receiver_address;
        boolean valid_email;
        do{
            System.out.print("\n请输入接收人邮箱: ");
            receiver_address = user_input.nextLine();
            valid_email = is_valid_email(receiver_address);
        }while(!valid_email);
        System.out.println("将要发送到邮箱: " + receiver_address);
        
        String email_host = "smtp.qq.com"; // QQ邮件服务器
        Properties pro = System.getProperties();
        pro.setProperty("mail.smtp.host", email_host); // 设置远程发信服务器
        
        // SSL验证相关 (抛出GeneralSecurityException)
        pro.put("mail.smtp.auth", "true"); // 远程服务器需要进行验证
        MailSSLSocketFactory sf = new MailSSLSocketFactory(); 
        sf.setTrustAllHosts(true);
        pro.put("mail.smtp.ssl.enable", "true");
        pro.put("mail.smtp.ssl.socketFactory", sf);

        //获取通信session对象, 并设置登录验证
        Session session = Session.getDefaultInstance(pro, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication(){ // 实现Authenticator内部的'秘钥验证', 返回PasswordAuthentication
                PasswordAuthentication my_passport = new PasswordAuthentication(sender_address, "ejmbkqtfzluhddhh");
                return my_passport;
            }
        });

        try {
            // 创建MimeMessage '消息对象', 对其内容进行填充
            MimeMessage email_obj = new MimeMessage(session);
            InternetAddress sender_ip = new InternetAddress(sender_address);
            InternetAddress receiver_ip = new InternetAddress(receiver_address);
            email_obj.setFrom(sender_ip); // 设置发件人 (这里指定要传一个Address对象)
            email_obj.addRecipient(Message.RecipientType.TO, receiver_ip); // 设置接收人 (直接发)
            // email_obj.addRecipient(Message.RecipientType.CC, receiver2_ip); // 抄送给另一个哥们
            /*  Tips:
                1. 上面的Message.RecipientType.IO是有多个不同的常量:
                    TO: 简单发给对应的收件人
                    CC: 抄送
                    BCC: 秘密抄送
                2. 上面的 '收件人' 可以是一个 Address[] 数组, 表示同时发给多个人
             */

            System.out.print("\n请输入邮件标题: ");
            String email_title = user_input.nextLine();
            email_obj.setSubject(email_title); // 设置标题

            System.out.println("请输入正文内容(回车结束): ");
            String email_content = user_input.nextLine();
            email_obj.setText(email_content); // 设置正文

            Transport.send(email_obj); // 正式发送
            System.out.println("\n已尝试发送邮件至 " + receiver_address);
            
        } catch (MessagingException e) {
            e.printStackTrace(); // 打印异常信息
        }
        
        
    }
}
