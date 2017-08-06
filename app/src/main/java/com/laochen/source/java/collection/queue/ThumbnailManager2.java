package com.laochen.source.java.collection.queue;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.common.collect.MinMaxPriorityQueue;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Date:2017/8/2 <p>
 * Author:chenzehao@danale.com <p>
 * Description:缩略图下载管理类
 */

public class ThumbnailManager2 {
    private static final String TAG = "ThumbnailManager2";
    //    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = /*Math.max(2, Math.min(CPU_COUNT - 1, 4))*/ 3;
    private static final int MAX_POOL_SIZE = /*CPU_COUNT * 2 + 1*/ 5;
    private static final int KEEP_ALIVE = 30;
    private static final int POOL_WORK_QUEUE_SIZE = MAX_POOL_SIZE;

    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingDeque<>(POOL_WORK_QUEUE_SIZE);
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sPoolWorkQueue);

    private static final int QUEUE_MAX_SIZE = MAX_POOL_SIZE;

    public static final String ACTION_SEND_DOWNLOAD_RESULT = "ACTION_SEND_DOWNLOAD_RESULT";

    /**
     * 1.task插入队列，把其id加入到该list
     * 2.由于队列已满，某个task会被自动从队列中移除，把其id从该list移除
     * 3.队列中的task出队列，进入线程池，该list不变
     * 4.task执行结束（不论成功或失败），把其id从该list移除
     */
    private static final List<String> TASK_LIST = Collections.synchronizedList(new LinkedList<String>());

    // MinMaxPriorityQueue的特点:
    // 1. peek(), poll() and AbstractQueue.remove()得到最小元素
    // 2. peekLast(), pollLast() and removeLast()得到最大元素
    // 3. size超过最大值，自动剔除最大元素
    // 4. 非线程安全，不接受null元素
    private static final MinMaxPriorityQueue<ThumbnailDownloadTask> TASK_QUEUE = MinMaxPriorityQueue.maximumSize(QUEUE_MAX_SIZE).create();

    /**
     * 根据线程池工作队列的size和当前线程数量，判断是否还能往线程池中添加任务
     */
    private static boolean canPoll() {
        if (sPoolWorkQueue.size() < POOL_WORK_QUEUE_SIZE) {
            return true;
        } else if (sPoolWorkQueue.size() == POOL_WORK_QUEUE_SIZE) {
            if (THREAD_POOL_EXECUTOR.getPoolSize() < MAX_POOL_SIZE) {
                return true;
            }
        }
        return false;
    }

    /**
     * 向队列中一个插入元素，插入成功返回true，否则返回false。
     * 插入之前会查重。
     */
    public synchronized static boolean offer(ThumbnailDownloadTask task) {
        if (addTask(task)) {
            task.setAsyncImageLoaderListener(new AsyncImageLoader());
        }
        return TASK_QUEUE.offer(task);
    }

    private static class AsyncImageLoader implements AsyncImageLoaderListener {
        @Override
        public void onAsyncImageLoader(String url, int tag, Bitmap bitmap) {
            // 把下载结果通过广播发送出去
            Intent intent = new Intent(ACTION_SEND_DOWNLOAD_RESULT);
            intent.putExtra("url", url);
            intent.putExtra("tag", tag);
            intent.putExtra("bitmap", bitmap);
            JniApplication.get().sendBroadcast(intent);
        }
    }

    /**
     * 根据情况将task的id加入到list
     * 加入成功返回true，否则返回false
     */
    private static boolean addTask(ThumbnailDownloadTask task) {
        if (TASK_QUEUE.size() == QUEUE_MAX_SIZE) {
            ThumbnailDownloadTask last = TASK_QUEUE.peekLast(); // 队列中的最大元素
            if (task.compareTo(last) <= 0) { // task较小:移除last，加入task
                TASK_LIST.remove(last.getId());
                TASK_LIST.add(task.getId());
                Log.e(TAG, "task插入队列:" + task.getId() + ",last从队列移除:" + last.getId());
                return true;
            } else {
                // task较大：task插入队列后会马上被移除
                // 此处无需处理
                Log.e(TAG, "task插入队列后会马上被移除:" + task);
            }
        } else {
            TASK_LIST.add(task.getId());
            Log.e(TAG, "task插入队列:" + task.getId());
            return true;
        }
        return false;
    }

    /**
     * 从队列中取出最小的元素（根据Comparator规则）。
     * 如果线程池无法接受任务或者队列没有元素，返回null
     */
    public synchronized static ThumbnailDownloadTask poll() {
        if (canPoll()) {
            ThumbnailDownloadTask task = TASK_QUEUE.poll();
            if (task != null) {
                Log.e(TAG, "队列size:" + TASK_QUEUE.size() + ",线程池中正在执行任务的线程数:" + THREAD_POOL_EXECUTOR.getActiveCount());
                Log.e(TAG, "task出队列，进线程池:" + task.getId());
            } else {
                //            Log.e(TAG, "队列没有元素，取出为null");
            }
            return task;
        }
        return null;
    }

    /**
     * 线程池执行任务
     */
    public static void execute(ThumbnailDownloadTask task) {
        if (task == null) {
            return;
        }
        THREAD_POOL_EXECUTOR.execute(task);
    }

    /**
     * 释放资源:确定不再使用才能释放，释放后无法恢复。
     */
    public static void clear() {
        Log.e(TAG, "释放资源");
        Log.e(TAG, TASK_LIST.toString());
        THREAD_POOL_EXECUTOR.shutdown();
        TASK_QUEUE.clear();
        TASK_LIST.clear();
    }

    public static boolean containsTaskId(String id) {
        return TASK_LIST.contains(id);
    }

    public static void removeIdFromList(String id) {
        TASK_LIST.remove(id);
    }
}
