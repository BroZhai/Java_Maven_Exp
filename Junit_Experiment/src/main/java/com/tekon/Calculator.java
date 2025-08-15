package com.tekon;

public class Calculator
{
    // 我们来实践一下Junit的简单测试, 这里先正常实现一下计算器的运作逻辑
    static int add(int a, int b){
        return a+b;
    }
    static int sub(int a, int b){
        return a-b;
    }
    static int multiply(int a, int b){
        return a*b;
    }

    static double devide(int a, int b){
        double da = (double) a;
        if(b==0){
            // 除数为0, 手动抛出一个IllegalArgumentException异常 (不是原来的Math异常)
            throw new IllegalArgumentException();
        }
        return da/b;
    }

    
    public static void main( String[] args )
    {
        
    }
}
