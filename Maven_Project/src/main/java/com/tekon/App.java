package com.tekon;

import com.tekon.my_class.Gun;

// 在maven项目中导入其他类时, 统一都是从'com'下开始, 基本上代码的开发就在src自己'声明的域名'下 (package声明同理) 
public class App {
    public static void main( String[] args ){
        Gun shotgun = new Gun("Super X3", "12 Gague", 114514);
        // System.out.println("已成功创建");
    }
}
