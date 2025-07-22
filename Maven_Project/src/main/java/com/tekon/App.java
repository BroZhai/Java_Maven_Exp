package com.tekon;

// 本地其他类
import com.tekon.my_class.Gun;

// Java内置类
import java.io.File; // 由于java 8的Files并没有readString() 和 writeString()方法(Java 11+ 才有), 这里用老一套的File类进行文件的写入
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.nio.file.Files; // 用Files类对外部json文件进行读取 (尽管没有readString(), 但是哥们想整一下readALlLine())
import java.util.List; // 用于接收.readAllLines()的结果
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;

// dependencies中引入的其他第三方库 (json)
import com.google.gson.*;// 通过修改pom.xml导入的第三方json库Gson (一般第三方的'package的声明'对应pom.xml中的<groupId>)

// 在maven项目中导入其他类时, 统一都是从'com'下开始, 基本上代码的开发就在src自己'声明的域名'下 (package声明同理) 
public class App {
    public static void main( String[] args ) throws IOException{
        Gun shotgun = new Gun("Super X3", "12 Gague", 114514);
        // System.out.println("已成功创建");

        // 接下来我们用Gson对Gun对象进行序列化 (参考: https://www.runoob.com/java/java-gson-lib.html)
        // Java对象转json (Gson提供了toJson()的实例转换方法, 将对象转为json 字符内容)
        Gson gson = new Gson(); // 创建一个gson实例
        String shotgun_json_content = gson.toJson(shotgun);
        System.out.println("Gson转换出来的json字符内容为: " + shotgun_json_content);

        // 指定'输出路径', 准备将上面的json字符内容 输出到文件
        // System.out.println(System.getProperty("user.dir"));
        String working_dir = System.getProperty("user.dir");
        
        // 突然发现了一个严重的问题, Files在java 8 下并没有writeString()方法, 这下不得不用BufferedWriter了...
        File shotgun_path = new File(working_dir + "/Maven_Project/src/main/java/com/tekon/Guns_json/" +shotgun.get_gun_name()+".json"); // 生成'目标文件', 下面进行数据写入
        // System.out.println(Files.exists(shotgun_path));

        BufferedWriter json_writer = new BufferedWriter(new FileWriter(shotgun_path));
        json_writer.write(shotgun_json_content); // 使用BufferedWriter对json 字符串数据进行写入
        json_writer.flush();
        json_writer.close();
    
        System.out.println();
        // 现在json文件已经成功创建, 我们要读该json文件的'内容', 将读到的'String内容'用Gson实例的.fromJson()方法进行解析;
        Path read_path = Paths.get(shotgun_path.getPath());
        // System.out.println(Files.exists(read_path)); 
        List<String> readed_lines = Files.readAllLines(read_path);
        StringBuilder read_json_content = new StringBuilder(5);
        for(String i: readed_lines){
            read_json_content.append(i);
        }
        System.out.println("读到的json_content的内容为: "+ read_json_content);
        
        // 现在我们拿到了json的'字符内容', 就可以用.fromJson()转Java对象了 (需要传入'对象class类型')
        Gun imported_gun = gson.fromJson(read_json_content.toString(), Gun.class);
        System.out.println("imported_gun的名称为: " + imported_gun.get_gun_name());
        System.out.println("弹药类型为: " + imported_gun.get_ammo_type());
        System.out.println("该枪的id为: " + imported_gun.get_gun_id());
        if (imported_gun.get_gun_id() == 0) {
            System.out.println("未能读取该枪的id, 可能是因为不存在! 已默认设为0");
        }
    }
}
