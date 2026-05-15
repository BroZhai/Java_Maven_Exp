package com.tekon;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService; // 导入Scheduled Executor
import java.util.concurrent.TimeUnit;

public class SchExecutor {
    public static void main(String[] args) {
        ScheduledExecutorService thread_pool = Executors.newScheduledThreadPool(10);
        Runnable task1 = new Runnable() {
            @Override
            public void run(){
                System.out.println("Task 1 Executed!");
            }
        };

        Runnable task2 = new Runnable() {
            @Override
            public void run(){
                System.out.println("Task 2 Executed!");
            }
        };

        thread_pool.scheduleAtFixedRate(task1,1, 1, TimeUnit.SECONDS);
        thread_pool.scheduleAtFixedRate(task2, 1, 1, TimeUnit.SECONDS);
    }
}
