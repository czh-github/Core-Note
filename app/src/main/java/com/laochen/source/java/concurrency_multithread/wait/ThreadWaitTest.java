package com.laochen.source.java.concurrency_multithread.wait;

/**
 * Date:2017/9/1 <p>
 * Author:chenzehao@danale.com <p>
 * Description:wait(),notify(),notifyAll()
 */

public class ThreadWaitTest {
    public static void main(String[] args) {
        Drop drop = new Drop();
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop))).start();
    }
}
