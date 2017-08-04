package com.laochen.jni.java.collection.queue;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.collect.Queues;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Date:2017/7/10 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class ThumbnailManager {
    private static final String TAG = "ThumbnailManager";
//    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = /*Math.max(2, Math.min(CPU_COUNT - 1, 4))*/ 3;
    private static final int MAX_POOL_SIZE = /*CPU_COUNT * 2 + 1*/ 5;
    private static final int KEEP_ALIVE = 30;
    private static final int POOL_WORK_QUEUE_SIZE = MAX_POOL_SIZE;

    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingDeque<>(POOL_WORK_QUEUE_SIZE);
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sPoolWorkQueue);

    private static final int QUEUE_MAX_SIZE = MAX_POOL_SIZE;

    private static final List<String> DOWNLOAD_SUCCESS_URL_LIST = Collections.synchronizedList(new LinkedList<String>());

    public static final String ACTION_SEND_DOWNLOAD_RESULT = "ACTION_SEND_DOWNLOAD_RESULT";


    /**
     * 任务状态：任务被加入到队列
     */
    public static final int TASK_STATE_ADD_TO_QUEUE = 0;
    /**
     * 任务状态：队列已满，任务被剔除
     */
    public static final int TASK_STATE_EVICT_FROM_QUEUE = 1;
    /**
     * 任务状态：任务进入线程池
     */
    public static final int TASK_STATE_POLL_TO_POOL = 2;
    /**
     * 任务状态：任务执行成功
     */
    public static final int TASK_STATE_EXECUTE_SUCCESS = 3;
    /**
     * 任务状态：任务执行失败
     */
    public static final int TASK_STATE_EXECUTE_FAILED = 4;

    // MinMaxPriorityQueue的特点在于队列的size如果超过了指定的size，会自动移除队列中最大的元素（根据Comparator规则）。
    // 不能将null插入MinMaxPriorityQueue。
    // 通过包装变成线程安全的queue。
    private static final Queue<ThumbnailDownloadTask> TASK_QUEUE = Queues.synchronizedQueue(MinMaxPriorityQueue.orderedBy(new Comparator<ThumbnailDownloadTask>() {
        @Override
        public int compare(ThumbnailDownloadTask task1, ThumbnailDownloadTask task2) {
            long time1 = task1.getTime();
            long time2 = task2.getTime();
            if (time1 > time2) {
                return -1;
            } else if (time1 < time2) {
                return 1;
            } else {
                return 0;
            }
        }
    }).maximumSize(QUEUE_MAX_SIZE).create());

    /**
     * 根据线程池工作队列的size和当前线程数量，判断是否还能往线程池中添加任务
     */
    public static boolean canPoll() {
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
    public static boolean offer(ThumbnailDownloadTask task) {
        if (TASK_QUEUE.contains(task)) {
            TASK_QUEUE.remove(task);
        }
        task.setAsyncImageLoaderListener(new AsyncImageLoader());
        Log.e(TAG, task.getName() + "进队列:" + task.getTime());
        Log.e(TAG, "队列size：" + sPoolWorkQueue.size() + ",当前线程数：" + THREAD_POOL_EXECUTOR.getPoolSize());
        return TASK_QUEUE.offer(task);
    }

    private static class AsyncImageLoader implements AsyncImageLoaderListener {
        @Override
        public void onAsyncImageLoader(String url, int tag, Bitmap bitmap) {
            if (tag != AsyncImageLoaderListener.SUCCESS) {
                removeUrl(url);
            }
            // TODO 把下载结果通过广播发送出去
            Intent intent = new Intent(ACTION_SEND_DOWNLOAD_RESULT);
            intent.putExtra("url", url);
            intent.putExtra("tag", tag);
            intent.putExtra("bitmap", bitmap);
            JniApplication.get().sendBroadcast(intent);
        }
    }

    /**
     * 从队列中取出最小的元素（根据Comparator规则）。如果队列没有元素，返回null
     */
    public static ThumbnailDownloadTask poll() {
        ThumbnailDownloadTask task = TASK_QUEUE.poll();
        if (task != null) {
            Log.e(TAG, task.getName() + "出队列");
        } else {
//            Log.e(TAG, "队列没有元素，取出为null");
        }
        return task;
    }

    /**
     * 线程池执行任务
     */
    public static void execute(ThumbnailDownloadTask task) {
        if (task != null && !containsUrl(task.getUrl())) {
            addUrl(task.getUrl());
            THREAD_POOL_EXECUTOR.execute(task);
        }
    }

    /**
     * 释放资源
     */
    public static void clear() {
        Log.e(TAG, "释放资源");
        THREAD_POOL_EXECUTOR.shutdown();
        TASK_QUEUE.clear();
    }

    public static boolean containsUrl(String url) {
        return DOWNLOAD_SUCCESS_URL_LIST.contains(url);
    }

    public static void addUrl(String url) {
        DOWNLOAD_SUCCESS_URL_LIST.add(url);
    }

    public static void removeUrl(String url) {
        DOWNLOAD_SUCCESS_URL_LIST.remove(url);
    }

}
