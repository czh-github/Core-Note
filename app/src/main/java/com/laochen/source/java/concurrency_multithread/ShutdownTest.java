package com.laochen.source.java.concurrency_multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Date:2017/8/8 <p>
 * Author:chenzehao@danale.com <p>
 * Description:shutdown/shutdownNow
 * shutdown() will just tell the executor service that it can't accept new tasks, but the already submitted tasks continue to run.
 * shutdownNow() will do the same AND will try to cancel the already submitted tasks by interrupting the relevant threads.
 * Note that if your tasks ignore the interruption, shutdownNow will behave exactly the same way as shutdown.
 */

public class ShutdownTest {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(new Runnable() {

            @Override
            public void run() {
                final Thread t = Thread.currentThread();
                System.out.println(t.getName() + "开始执行");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            if (t.isInterrupted()) {
                                System.out.println("interrupted:" + System.currentTimeMillis());
                                System.out.println(t.getName() + "interrupted");
                                break;
                            } else {
                                System.out.println("running:" + System.currentTimeMillis());
                            }
                        }
                    }
                }).start();
            }
        });
        // 主线程休眠5毫秒
        Thread.sleep(5L);
        System.out.println("shutdownNow:" + System.currentTimeMillis());
        executor.shutdownNow();
        // 一直阻塞直到shutdown请求发出后任务全部执行完成，或者超时发生，或者当前线程被中断，任一个先发生即解除阻塞
        // 如果线程池终止返回true，如果线程池终止之前超时发生返回false
        if (!executor.awaitTermination(10, TimeUnit.MILLISECONDS)) {
            System.out.println("Still waiting after 100ms: calling System.exit(0)...");
            System.exit(0);
        }
        System.out.println("Exiting normally...");

//        int x=1;
//        int y=2;
//        int z=add(x,y);
//        System.out.println(z);

    }

 public static int add(int x,int y){
     return x+y;
 }
}
