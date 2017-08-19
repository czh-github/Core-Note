package com.laochen.source.java.multithread;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Date:2017/8/8 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class LinkedBlockingQueueTest {
    public static void main(String[] args) throws Exception {

//        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(5);
//        queue.clear();
//        queue.add("1");
//        queue.add("2");
//        queue.add("3");
//        queue.add("4");
//        queue.add("5");
//        queue.add("6");
//
//        System.out.println(queue);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                LinkedBlockingQueueTest test = new LinkedBlockingQueueTest();
                test.test();
            }
        }, 0L, 2000L);

    }

    private void test() {

        int remain = 10;
        int groups = 5;
        int[] counts = new int[groups];
        int total = 0;
        int[] results = new int[groups];
        Random random = new Random();
        for (int i = 0; i < groups; i++) {
            counts[i] = random.nextInt(100);
            total += counts[i];
        }
//        total -= counts[2];
//        total -= counts[8];
//        counts[2] = 0;
//        counts[8] = 0;
        System.out.println(Arrays.toString(counts) + "total:" + total + ",remain:" + remain);
        func(remain, counts, total, results);
        System.out.println(Arrays.toString(results));
        System.out.println("------------------------------------------------------------------------");
    }

    private void func(int remain, int[] counts, int total, int[] results) {
        int remain0 = remain;
        int total0 = total;

        for (int i = 0; i < counts.length; i++) {
            if (results[i] == 1) {
                continue;
            }
            if (counts[i] == 0) {
                results[i] = 0;
            } else if (1.0 * remain * counts[i] / total <= 1) {
                results[i] = 1;
                remain0 -= 1;
                total0 -= counts[i];
                counts[i] = 0;
            }
        }
        System.out.println(Arrays.toString(counts) + "total:" + total0 + ",remain:" + remain0);

        boolean tag = false;
        for (int i = 0; i < counts.length; i++) {
            double count = 1.0 * remain0 * counts[i] / total0;
            if (count > 0 && count < 1) {
                tag = true;
                break;
            }
        }

        if (tag) {
            func(remain0, counts, total0, results);
        } else {
            int remain00 = remain0;
            for (int i = 0; i < counts.length; i++) {
                if (counts[i] != 0) {
                    results[i] = (int) Math.round(1.0 * remain0 * counts[i] / total0);
                    remain00 -= results[i];
                }
            }
            System.out.println("final remain:" + remain00);
        }
    }

}
