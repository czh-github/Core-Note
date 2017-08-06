package com.laochen.source.java.collection.queue.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Date:2017/7/13 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class ThreadPoolTest {

    public static void main(String[] args) throws Exception {
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        Future<Boolean> future = threadPool.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (System.currentTimeMillis() % 2 == 0) {
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            }
        });

        System.out.println("result:" + future.get());
    }
}
