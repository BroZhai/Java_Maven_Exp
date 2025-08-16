package com.tekon;

import java.sql.DriverManager; // 本地连接器, 设置连接配置
import java.sql.Connection; // 用于创建各种Statement'执行对象' (纯'中间商', 但是可以设置'事务是否自动提交')
import java.sql.Statement; // 用于执行SQL语句的载体, 返回ResultSet结果集 (用于'静态查询')
import java.sql.PreparedStatement; // 高级的'Statement' (用于带参数的'动态查询', 可以防范SQL注入, 且性能更好, 也可以用于'静态查询')
import java.sql.ResultSet; // 操作'查询结果'
import java.sql.SQLException; // 导入SQL操作的'异常类'


// 其他工具类
import java.util.Scanner;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

public class My_Database {

    // 菜单方法实现区
    // 1. 查询tekon表中所有的数据
    public static void show_all(Connection connector) throws SQLException{ // 方法内部先不处理SQLException, 抛到外面的main中统一报错
        Statement sql_query = connector.createStatement();
        ResultSet results = sql_query.executeQuery("select * from tekon_item");
        if(results.next() == false){ // 表中没有数据
            System.out.println("tekon_item表中好像当前毛都没有诶...");
        }else{
            // 表中有数据, 自行遍历每一个数据条并进行输出 
            do{ // 注意上面的if在'有数据'的情况下会'先耗一行数据', 所以下面用先do后while结构, 确保上面的if出来的数据没有'白消耗' XD
                System.out.println("当前tekon_item有: ");
                int item_id = results.getInt("item_id");
                int item_count = results.getInt("item_count");
                String item_name = results.getString("item_name");
                String item_desc = results.getString("item_desc");
                System.out.println("物品id: " + item_id + ", 物品名称: " + item_name + ", 物品描述: " + item_desc + ", 物品数量: " + item_count);
            }while(results.next()); // 调用 ResultSet对象的.next()方法取得第一个以及'往后'的数据 (.next()会返回一个布尔告知还有没有数据)

        }

        System.out.println("\n查询结束!");
    }

    // 2. 追加一条数据
    public static void insert_one(Connection connector){
        
    }
    // 3. 删除一条数据 (根据id)

    // 4. 执行自定义SQL语句

/* ------------------分割线--------------------- */
    public static void main( String[] args ){

        Scanner user_input = new Scanner(System.in);

        // MySQL JDBC URL地址规范: jdbc:mysql://主机地址/使用的数据库名?可选参数
        String local_address = "jdbc:mysql://localhost:3306/tekon";
        System.out.println("欢迎来到tekon的妙妙数据库实验");
        System.out.println("当前要连接的数据库地址是: " + local_address);

        System.out.print("请输入用户名(回车直接为root): ");
        String user = user_input.nextLine();
        if (user.equals("")) {
            user = "root";
            System.out.println("已将user默认设置为root!");
        }
        System.out.print("请输入验证口令: ");
        String password = user_input.nextLine();

        try {
            // 使用DriverManager创建"Connection连接对象"
            Connection my_connector = DriverManager.getConnection(local_address, user, password);
            System.out.println("\n成功验证! 欢迎回来 " + user );
            boolean keep_running = true;
            while (keep_running) {
                System.out.println("\n请选择操作选项: ");
                System.out.println("1. 查询tekon表中所有数据");
                System.out.println("2. 追加一条数据");
                System.out.println("3. 删除一条数据(根据id)");
                System.out.println("4. 输入&执行自定义SQL命令"); // 强烈不推荐, 但是这里仅为实践 XD
                System.out.println("5. 退出程序");

                System.out.print("\n请输入您的选择: ");
                String selected_option = user_input.nextLine();
                switch (Integer.parseInt(selected_option)) { // 这里先不做校验了, 图个方便 XD
                    case 1:
                        show_all(my_connector);
                        break;
                    case 2:
                        
                        break;
                    case 3:
                        
                        break;
                    case 4:
                        
                        break;
                    case 5:
                        keep_running = false;
                        System.out.println("感谢您使用tekon_item数据库, 现在滚犊子吧 :3");
                        break;
                
                    default:
                        break;
                    }
                }
            

        } catch (SQLException e) {
            System.out.println("\n发生了SQL异常!!");
            e.printStackTrace();
        } finally {

        }
        
    }
}
