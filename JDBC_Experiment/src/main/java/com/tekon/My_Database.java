package com.tekon;

import java.sql.DriverManager; // 本地连接器, 设置连接配置
import java.sql.Connection; // 用于创建各种Statement'执行对象' (纯'中间商', 但是可以设置'事务是否自动提交')
import java.sql.Statement; // 用于执行SQL语句的载体, 返回ResultSet结果集
import java.sql.ResultSet; // 操作'查询结果'

import java.sql.SQLException; // 导入SQL操作的'异常类'

public class My_Database 
{
    public static void main( String[] args ){



        // MySQL JDBC URL地址规范: jdbc:mysql://主机地址/使用的数据库名?可选参数

        try {
            // 使用DriverManager创建"Connection连接对象"
            Connection my_connector = DriverManager.getConnection(local_address, user, password);
            Statement simple_query  = my_connector.createStatement();
            ResultSet query_result = simple_query.executeQuery("select * from tekon_item;");
            while(query_result.next()){
                int item_id = query_result.getInt("item_id");
            int item_count = query_result.getInt("item_count");
            String item_name = query_result.getString("item_name");
            String item_desc = query_result.getString("item_desc");
            System.out.println("物品id: " + item_id + ", 物品名称: " + item_name + ", 物品描述: " + item_desc + ", 物品数量: " + item_count);
            }

        } catch (SQLException e) {
            System.out.println("发生了SQL异常!");
            e.printStackTrace();
        } finally {

        }
        
    }
}
