package com.tekon;

import java.sql.DriverManager; // 本地连接器, 设置连接配置
import java.sql.Connection; // 用于创建各种Statement'执行对象' (纯'中间商', 但是可以设置'事务是否自动提交')
import java.sql.Statement; // 用于执行SQL语句的载体, 返回ResultSet结果集 (用于'静态查询')
import java.sql.PreparedStatement; // 高级的'Statement' (用于带参数的'动态查询', 可以防范SQL注入, 且性能更好, 也可以用于'静态查询')
import java.sql.ResultSet; // 操作'查询结果'
import java.sql.SQLException; // 导入SQL操作的'异常类'

// 其他工具类
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
// 其他异常类
import java.util.InputMismatchException;

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
            System.out.println("当前tekon_item有: ");
            do{ // 注意上面的if在'有数据'的情况下会'先耗一行数据', 所以下面用先do后while结构, 确保上面的if出来的数据没有'白消耗' XD
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
    public static void insert_one(Connection connector, Scanner user_input) throws SQLException{
        System.out.println("准备往数据库中追加一条新的数据");   
        System.out.print("请输入物品名称: ");
        String input_name = user_input.nextLine();
        System.out.print("请输入物品描述: ");
        String input_desc = user_input.nextLine();
        System.out.print("请输入物品数量(整数): ");
        int input_count = user_input.nextInt();
        user_input.nextLine(); // 敲完数字后, Scanner的缓冲区中仍留有一个'\n'回车, 咱得给它耗掉, 防止在菜单选择时突然直接读了剩余的'\n'导致报错 XD

        // 单独定义好插入的SQL语句
        String insert_sql = "insert into tekon_item(item_name, item_desc, item_count) values(?,?,?);";
        PreparedStatement inject = connector.prepareStatement(insert_sql);
        inject.setString(1, input_name);
        inject.setString(2, input_desc);
        inject.setInt(3, input_count);
        int result = inject.executeUpdate(); // 该方法湖返回'受影响'的行数
        if(result!=0){
            System.out.println("\n操作完成, 共有 " + result + " 行受到影响");
        }else{
            System.out.println("\n操作不成功! 没有行受到影响...");
        }
        
    }

    // 3. 追加多条数据
    public static void insert_many(Connection connector) {
        System.out.println("为了测试方便起见, 这里直接用一个内置的List<Item>作为数据源, 省的自己输了XD");
        Item unknown_mouse = new Item("M333-Mouse", "一款奇怪的杂牌透明蓝牙鼠标", 1);
        Item newsmy = new Item("Newsmy A1 Mobile Terminal", "一个杂牌的移动终端", 1);
        Item baka = new Item("Cirno Plushy", "捏一捏就会叫Baka的小玩偶", 1);
        ArrayList<Item> item_list = new ArrayList<>();
        item_list.add(unknown_mouse);
        item_list.add(newsmy);
        item_list.add(baka);

        item_list.stream().forEach( (current_item) -> {
            String insert_sql = "insert into tekon_item(item_name, item_desc, item_count) values(?,?,?);";
            PreparedStatement inject;
            try {
                inject = connector.prepareStatement(insert_sql);
                inject.setString(1, current_item.get_name());
                inject.setString(2, current_item.get_desc());
                inject.setInt(3, current_item.get_count());
                if(inject.executeUpdate()!=0){
                    System.out.println("成功插入了 " + current_item.get_name());
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }
    
    // 4. 删除一条数据 (根据id)
    public static void delete_one(Connection connector, Scanner user_input) throws SQLException{
        System.out.println("欢迎来到数据删除");
        show_all(connector);
        System.out.print("请输入要删除数据的id: ");
        int input_id = user_input.nextInt();
        user_input.nextLine(); // 消耗换行符
        String delete_sql = "delete from tekon_item where item_id = ?;";
        PreparedStatement delete = connector.prepareStatement(delete_sql);
        delete.setInt(1, input_id);
        int result = delete.executeUpdate();
        if(result!=0){
            System.out.println("\n操作完成, 共有 " + result + " 行受到影响");
        }else{
            System.out.println("\n操作不成功! 找不到id为" + input_id + "的数据条, 0行受到影响...");
        }

    }

    // 5. 删除多条数据 (也是用id)
    public static void delete_many(Connection connector, Scanner user_input) throws SQLException{
        show_all(connector);
        System.out.print("请输入要删除的id数, 用空格隔开每个数: ");
        String number_string = user_input.nextLine();
        String[] split_num = number_string.split("\\s"); // 使用字符串的.split("RE表达式")方法对 内容进行指定分割, 得到分隔好的不同的String[]数组
        for(String i : split_num){
            int item_id = Integer.parseInt(i);
            String delete_sql = "delete from tekon_item where item_id = ?;";
            PreparedStatement delete = connector.prepareStatement(delete_sql);
            delete.setInt(1, item_id);
            if(delete.executeUpdate()!=0){
                System.out.println("已成功删除id为 " + item_id + " 的物品");
            }else{
                System.out.println("未能找到id " + item_id + " 的物品...");
            }
        }
    }


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
                System.out.println("3. 测试批量添加多条数据");
                System.out.println("4. 删除一条数据(根据id)");
                System.out.println("5. 批量删除多条数据");
                System.out.println("6. 退出程序");

                System.out.print("\n请输入您的选择: ");
                String selected_option = user_input.nextLine();
                switch (Integer.parseInt(selected_option)) { // 这里先不做校验了, 图个方便 XD
                    case 1:
                        show_all(my_connector);
                        break;
                    case 2:
                        insert_one(my_connector, user_input);
                        break;
                    case 3:
                        insert_many(my_connector);
                        break;
                    case 4:
                        delete_one(my_connector, user_input);
                        break;
                    case 5:
                        delete_many(my_connector, user_input);
                        break;
                    case 6:
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
        } catch (InputMismatchException e){
            System.out.println("检测到输入的数据类型不匹配!");
        }
        
        finally {
            // my_connector.close() // 正常来说要按顺序关闭 ResultSet -> Statement -> Connection, 以后注意!
        }
        
    }
}
