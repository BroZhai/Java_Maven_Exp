package com.tekon;

// Java内置工具
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

// 第三方包
import javax.mail.*; // 利用maven引入的javax 邮件包

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

    public static void main( String[] args ){
        System.out.println("欢迎来到Javax Email中心~");
        Scanner user_input = new Scanner(System.in);
        String receiver_address;
        boolean valid_email;
        do{
            System.out.print("\n请输入接收人邮箱: ");
            receiver_address = user_input.nextLine();
            valid_email = is_valid_email(receiver_address);
        }while(!valid_email);

    }
}
