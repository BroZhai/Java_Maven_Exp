package com.tekon;

// 本地其他类
import com.tekon.my_class.Gun;

// Java内置类
import java.nio.file.*;
import java.io.IOException;

// dependencies中引入的其他第三方库 (json)
import com.alibaba.fastjson2.*; // 通过修改pom.xml导入的第三方json库fastjson2 (一般第三方的'package的声明'可以在对应的说明文档使用例中找到)

// 在maven项目中导入其他类时, 统一都是从'com'下开始, 基本上代码的开发就在src自己'声明的域名'下 (package声明同理) 
public class App {
    public static void main( String[] args ) throws IOException{
        Gun shotgun = new Gun("Super X3", "12 Gague", 114514);
        // System.out.println("已成功创建");

        // 接下来我们用fastjson对Gun对象进行序列化 (参考: https://github.com/alibaba/fastjson2)
        // Java对象转json (这里fastjson提供了toString 和 tobyte 两种类型转换方法)
        String json_text = JSON.toJSONString(shotgun);
        byte[] json_byte = JSON.toJSONBytes(shotgun);
        System.out.println("转换为的json内容为: " + json_text);

        System.out.println(System.getProperty("user.dir"));
        String working_dir = System.getProperty("user.dir");
        
        Path shotgun_path = Paths.get(working_dir,"Maven_Project/src/main/java/com/tekon/Guns_json",shotgun.get_gun_name()+".json"); // 生成'目标文件', 下面进行数据写入
        // System.out.println(Files.exists(shotgun_path));
        Files.write(shotgun_path, json_byte, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);

        System.out.println();
        // 现在json文件已经成功创建, 我们要读该json文件的'内容', 将读到的'内容'放在JSON.parseObject中解析;
        if(Files.exists(shotgun_path)){
            System.out.println("成功存储了 " + shotgun_path.getFileName() + ", 正在对其进行读取...");
            byte[] readed_byte = Files.readAllBytes(shotgun_path);
            JSONObject shotgun_json = JSON.parseObject(null);
            // Gun imported_gun = JSON.parseArray(readed_json, Gun.class); // fastjson从byte[] / String中读取数据, 转回对应的Java对象 (要把对应的类.class给传进去)
            System.out.println("导入的枪名为: " + imported_gun.get_gun_name() + ", 弹药类型为: " + imported_gun.get_ammo_type());
            try {
                System.out.println("导入的枪的id为: " + imported_gun.get_gun_id());
            } catch (Exception e) {
                System.out.println("尝试读取的枪的id为null...");
            }
        }else{
            System.out.println("未能成功存储 " + shotgun_path.getFileName() + ", 程序已终止运行...");
        }
        
        // Gun imported_shotgun =JSON.parseObject()
    }
}
