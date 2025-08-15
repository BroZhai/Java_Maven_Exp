package com.tekon;


import java.time.Duration; // 设置'测试时长'的相关类
import org.junit.jupiter.api.*; // 引入Junit 5 的所有'注解'
import static org.junit.jupiter.api.Assertions.*; // Junit 5中的所有'断言' (测试判断)

class CalculatorTest{
    // 大坑: BeforeAll要必须至少有一个Test才能跑起来!
    @BeforeAll
    public static void init(){
        System.out.println("正在启动计算机测试程序, 请稍后...");
    }

    @Test
    public void add_test(){
        System.out.println("加法测试开始...");
        assertEquals(5, Calculator.add(2, 3));
        // assertEquals() 判断两个'值'是否相等
    }

    @Test
    public void sub_test(){
        System.out.println("减法测试开始...");
        int result = Calculator.sub(11, 2);
        assertTrue(result == 9);
        // assertTure() 内部条件'为真'判断
    }

    @Test
    public void multiply_test(){
        System.out.println("乘法测试开始...");
        // assertTimeout() 判断程序是否在指定时间范围内完成, 用Duration类的静态方法来设定时长
        assertTimeout(Duration.ofSeconds(2), () -> {
            assertEquals(9, Calculator.multiply(3, 3));
        });
    }

    @Test
    public void devide_test(){
        System.out.println("除法测试开始, 但是除数为0");
        // assertThrows()看看方法有没有抛出对应的异常
        assertThrows(IllegalArgumentException.class, () -> {
            Calculator.devide(3,0);
        });
    }

    @AfterEach
    public void current_test_end(){
        System.out.println("当前测试结束\n");
    }

    @AfterAll
    public static void test_end(){
        System.out.println("所有测试结束!");
    }
    
}