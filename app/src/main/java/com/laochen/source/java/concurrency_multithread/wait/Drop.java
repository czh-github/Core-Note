package com.laochen.source.java.concurrency_multithread.wait;

public class Drop {
    // Message sent from producer
    // to consumer.
    private String message;
    // True if consumer should wait
    // for producer to send message,
    // false if producer should wait for
    // consumer to retrieve message.
    private boolean empty = true;

    private final Object monitorObject = new Object();

    /**
     * 线程必须在synchronized块内部调用wait()或notify()，如果在别处调，抛出IllegalMonitorStateException
     * 必须调用synchronized的监视器对象的wait()或notify()
     * 当一个线程调用监视器对象的notify()时，等待相同监视器对象的某一个线程被唤醒并继续执行
     * 当一个线程调用监视器对象的notifyAll()时，等待相同监视器对象的所有线程被唤醒（公平竞争获得锁并执行，一次只有一个线程获得锁）
     * 不要用字符串常量当做监视器对象，例如String monitor = "...";因为会导致多个Drop对象的monitor是同一个。
     *
     * monitorObject.wait(long millis);
     * 线程T休眠直到四个事件其中之一发生：
     * 1.别的线程调用monitorObject.notify()并且线程T被线程调度器选中唤醒
     * 2.别的线程调用monitorObject.notifyAll()
     * 3.别的线程调用t.interrupt()中断线程T
     * 4.等待了millis指定的时间后。如果设为0，millis不生效，只能等到别的线程notify。
     * 线程T被唤醒后，会从monitorObject的wait set里面移除，能够重新被线程调度器调度，然后跟等待monitorObject的其它线程
     * 公平竞争锁
     */
    public String take() {
        synchronized (monitorObject) {
            // Wait until message is
            // available.
            while (empty) { // 为什么用while循环而不是if，因为可能出现假唤醒（notify()或notifyAll()没被调用却被唤醒）
                try {
                    monitorObject.wait();
                    // 该线程通过竞争获得锁后才能继续执行。
                } catch (InterruptedException e) {
                }
            }
            // Toggle status.
            empty = true;
            // Notify producer that
            // status has changed.
            notifyAll();
            return message;
        }
    }

    public synchronized void put(String message) {
        synchronized (monitorObject) {
            // Wait until message has
            // been retrieved.
            while (!empty) {
                try {
                    monitorObject.wait();
                } catch (InterruptedException e) {
                }
            }
            // Toggle status.
            empty = false;
            // Store message.
            this.message = message;
            // Notify consumer that status
            // has changed.
            notifyAll();
        }
    }
}