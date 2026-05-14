package com.tekon;

import java.util.Timer; // 导入 Java Util包中的 Timer定时器类
import java.util.TimerTask; // Timer的'执行类', 在内部书写

public class Schedulers 
{
    public static void main(String[] args){
        Timer myTimer = new Timer(); // 创建一个Timer对象
        // 创建TimerTask执行对象, 在内部写'任务逻辑'
        TimerTask myTask = new TimerTask(){
            @Override
            public void run(){ // 本质上和Thread线程中声明'任务逻辑'是一致的, 都是重写run()方法 (见'继承Thread'类)
                System.out.println("我是TimerTask中的任务逻辑, 本次执行完了ouo!");
            }
        };

        // 利用Timer对象'何时提醒'TimerTask来执行任务
        // 语法: Timer对象.schdule(TimerTask执行对象, 首次执行延迟ms, 周期间隔ms)
        myTimer.schedule(myTask, 500, 4000); // 首次延迟0.5s执行, 等4s执行下一次
    }
}
