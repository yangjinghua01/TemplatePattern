package com.example.templatepattern;
import androidx.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadPoolTestException {
    static ThreadPoolExecutor threadPoolExecutor;

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new PriorityBlockingQueue<Runnable>(4);
    // Queue 的参数
    // BlockingQueue: 先进先出的一个队列 FIFO
    // SynchronousQueue: 线程安全的队列，它里面是没有固定的缓存的（OKHttp所使用的）
    // PriorityBlockingQueue: 无序的可以根据优先级进行排序 ，指定的对象要实现 Comparable 作比较

    // RejectedExecutionException 报错的原因，其实也是 AsyncTask 存在的一些隐患 ，比如 我要执行 200 Runnable就肯定会报错

    // 线程队列是 4  ， 核心线程数也是 4 ，最大线程数是 10 ，目前加入的Runnable有 20 个
    // 20 个都要放到队列中 ，但是队列只有 4  还有 16 个是没法放，这个时候最大线程数 是 10 非核心线程是 6 ，
    // 那么会拿 6 个出来执行，这个时候会 从新创建 6 个线程，目前线程池就到达 10 个线程，
    // 但是还有 10 个没办法放就只能抛异常了，意味着那 10 个Runnable 没办法执行就会跑异常

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

        /*for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("下载图片显示完毕");
                }
            });
        }*/

        for (int i = 0; i < 20; i++) {
            /*Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("下载图片显示完毕"+Thread.currentThread().getName());
                }
            };*/
            Request request = new Request();
            // 加入线程队列，寻找合适的时机去执行
            threadPoolExecutor.execute(request);
        }
    }
}
