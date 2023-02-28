package com.example.templatepattern;

import androidx.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
    static ThreadPoolExecutor threadPoolExecutor;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingDeque<Runnable>(4);

    static {
        threadPoolExecutor = new ThreadPoolExecutor(
                4,// 核心线程数，就是线程池里面的核心线程数量
                10, // 最大线程数，线程池中的最大线程数
                60,// 线程存活的时间，没事干的时候的空闲存活时间，超过这个时间线程就会被销毁
                TimeUnit.SECONDS,// 线程存活时间的单位
                sPoolWorkQueue,// 线程队列
                new ThreadFactory() {// 线程创建工厂，如果线程池需要创建线程就会调用 newThread 来创建
                    @Override
                    public Thread newThread(@NonNull Runnable r) {
                        Thread thread = new Thread(r,"自己线程的名字");
                        thread.setDaemon(false); // 不是守护线程
                        return new Thread(r);
                    }
                });
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("下载图片显示完毕"+Thread.currentThread().getName());
                }
            };
            threadPoolExecutor.execute(runnable);
        }
    }
}
