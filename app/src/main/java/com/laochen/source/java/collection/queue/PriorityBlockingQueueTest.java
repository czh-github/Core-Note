package com.laochen.source.java.collection.queue;

import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Date:2017/7/10 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class PriorityBlockingQueueTest {
    public static void main(String[] args) {
        drainToTest();
    }

    public static void drainToTest() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        ArrayList<Integer> polledElements = new ArrayList<>();

        queue.add(1);
        queue.add(5);
        queue.add(2);
        queue.add(3);
        queue.add(4);


//        queue.drainTo(polledElements);
//        System.out.println(polledElements.toString());

        for (int i = 0; i < 6; i++) {
        Integer polled = queue.poll();
        System.out.println(polled);

        }
    }
}
