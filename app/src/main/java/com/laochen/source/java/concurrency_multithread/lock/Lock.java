package com.laochen.source.java.concurrency_multithread.lock;

/**
 * Date:2017/9/4 <p>
 * Author:chenzehao@danale.com <p>
 * Description:一个简易的Lock实现
 * 当有多个线程等待进入synchronized代码块时，synchronized代码块不能保证哪个线程能访问。
 * 当有多个线程在wait时，notify()被调用，也无法保证哪个线程会被唤醒。
 * 下面版本的Lock实现，并未实现公平锁。
 */

public class Lock {
    private boolean isLocked = false;
    private Thread lockingThread = null;

    public synchronized void lock() throws InterruptedException {
        while (isLocked) {
            wait();
        }

        isLocked = true;
        lockingThread = Thread.currentThread();
    }

    public synchronized void unlock() {
        if (lockingThread != Thread.currentThread()) {
            throw new IllegalMonitorStateException(
                    "Calling thread has not locked this lock");
        }
        isLocked = false;
        notify();
    }

}
