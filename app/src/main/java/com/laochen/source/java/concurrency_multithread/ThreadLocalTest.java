package com.laochen.source.java.concurrency_multithread;

/**
 * Date:2017/9/1 <p>
 * Author:chenzehao@danale.com <p>
 * Description:ThreadLocal
 * ThreadLocal对象只对创建它的线程可见，换句话说，只有创建它的线程能对其进行读写，属于线程私有。
 * InheritableThreadLocal是ThreadLocal的子类，父线程和子线程都能访问父线程创建的InheritableThreadLocal。
 */

public class ThreadLocalTest {
    public static class MyRunnable implements Runnable {

        private ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();
        private ThreadLocal<Integer> threadLocal1 = new ThreadLocal<Integer>() {
            @Override
            protected Integer initialValue() {
                return 0; // 因为没有线程能通过set方法设置对所有线程都有效的初始值，通过该方法可以
            }
        };

        @Override
        public void run() {
            threadLocal.set((int) (Math.random() * 100D));

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
        }
    }


    public static void main(String[] args) throws InterruptedException {
        MyRunnable sharedRunnableInstance = new MyRunnable();

        Thread thread1 = new Thread(sharedRunnableInstance);
        Thread thread2 = new Thread(sharedRunnableInstance);

        thread1.start();
        thread2.start();

        thread1.join(); //main thread wait for thread 1 to terminate
        thread2.join(); //main thread wait for thread 2 to terminate
    }

}
