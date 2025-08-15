package com.tekon;

import org.junit.jupiter.api.*; // 引入Junit 5 的所有'注解'

class CalculatorTest{
    // 大坑: BeforeAll要必须至少有一个Test才能跑起来!
    @BeforeAll
    public static void init(){
        System.out.println("正在启动计算机测试程序, 请稍后...");
    }

    @Test
    public void add_test(){
        System.out.println("加法测试");
        System.out.println("Go fuck yourself");
    }
    
}