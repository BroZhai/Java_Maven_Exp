package com.tekon;

// 本地其他类
import com.tekon.my_class.Gun;

import java.io.BufferedReader;
import java.io.BufferedWriter;
// Java内置类
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// dependencies中引入的其他第三方库 (json)
// 通过修改pom.xml导入的第三方json库Gson (一般第三方的'package的声明'对应pom.xml中的<groupId>)

// 在maven项目中导入其他类时, 统一都是从'com'下开始, 基本上代码的开发就在src自己'声明的域名'下 (package声明同理) 
public class App {
    public static void main( String[] args ) throws IOException{
        Gun shotgun = new Gun("Super X3", "12 Gague", 114514);
        // System.out.println("已成功创建");

        // 接下来我们用fastjson对Gun对象进行序列化 (参考: https://github.com/alibaba/fastjson2)
        // Java对象转json (这里fastjson提供了toString 和 tobyte 两种类型转换方法)


        System.out.println(System.getProperty("user.dir"));
        String working_dir = System.getProperty("user.dir");
        
        // 突然发现了一个严重的问题, Files在java 8 下并没有writeString()方法, 这下不得不用BufferedWriter了...
        File shotgun_path = new File(working_dir + "/Maven_Project/src/main/java/com/tekon/Guns_json/" +shotgun.get_gun_name()+".json"); // 生成'目标文件', 下面进行数据写入
        // System.out.println(Files.exists(shotgun_path));

        // BufferedWriter json_writer = new BufferedWriter(new FileWriter(shotgun_path));
        // json_writer.write(json_text); // 使用BufferedWriter对json 字符串数据进行写入
        // json_writer.flush();
        // json_writer.close();
    

        System.out.println();
        // 现在json文件已经成功创建, 我们要读该json文件的'内容', 将读到的'内容'放在JSON.parseObject中解析;
        
        // Gun imported_shotgun =JSON.parseObject()
    }
}
