package com.laochen.jni.java.collection.queue.thread;

/**
 * Date:2017/7/18 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class Main {

    public static void main(String[] args) throws Exception {
        MyThread t = new MyThread();
        t.start();

        Thread.sleep(1000);

        t.interrupt();
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                System.out.println(1);
            }
            System.out.println(0);
        }
    }
}
