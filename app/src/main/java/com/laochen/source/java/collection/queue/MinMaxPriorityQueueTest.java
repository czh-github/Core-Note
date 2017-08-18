package com.laochen.source.java.collection.queue;

import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.collect.Queues;

import java.util.Comparator;
import java.util.Queue;

/**
 * Date:2017/7/10 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class MinMaxPriorityQueueTest {
    private static Queue<Person> queue = Queues.synchronizedQueue(MinMaxPriorityQueue.orderedBy(new Comparator<Person>() {
        @Override
        public int compare(Person thiz, Person other) {
            return thiz.age - other.age;
        }
    })
            .maximumSize(5)
            .create());

//    public static Integer autoRemoved(Integer added) {
//        if (queue.size() == 5) {
//            if (queue.) {
//
//            }
//        }
//    }

    public static void main(String[] args) {
        queue.offer(new Person("111", 10));
        queue.offer(new Person("222", 10));
        queue.offer(new Person("333", 10));
        queue.offer(new Person("444", 10));
        queue.offer(new Person("555", 10));

        queue.offer(new Person("666", 10)); // size 超过maximumSize，auto remove greatest
        System.out.println(queue.toString());
        queue.offer(new Person("555", 10)); // 移除2
        System.out.println(queue.toString());
        queue.offer(new Person("444", 10)); // 移除3
        System.out.println(queue.toString());

        System.out.println("peek:" + queue.peek()); // get not remove smallest

        System.out.println("poll:" + queue.poll()); // get and remove smallest
        System.out.println("peek:" + queue.peek());
        System.out.println("poll:" + queue.poll());
        System.out.println("peek:" + queue.peek());
        System.out.println("poll:" + queue.poll());
        System.out.println("peek:" + queue.peek());
        System.out.println("poll:" + queue.poll());
        System.out.println("peek:" + queue.peek());
        System.out.println("poll:" + queue.poll());
        System.out.println("peek:" + queue.peek());
        System.out.println("poll:" + queue.poll());

//        BlockingQueue<String> bq = new LinkedBlockingDeque<>(10);
//        bq.offer("a");
//        System.out.println(bq.size());


//        System.out.println(queue.poll()); // 取出并移除最小的元素，没有元素返回null
//        System.out.println(queue.poll()); // 取出并移除最小的元素，没有元素返回null
//        System.out.println(queue.poll()); // 取出并移除最小的元素，没有元素返回null
//        System.out.println(queue.poll()); // 取出并移除最小的元素，没有元素返回null
//        System.out.println(queue.poll()); // 取出并移除最小的元素，没有元素返回null
//        System.out.println(queue.poll()); // 取出并移除最小的元素，没有元素返回null
//        System.out.println(queue.peek()); // 取出但不移除最小的元素，没有元素返回null
//        System.out.println(queue.remove()); // 与poll()的唯一区别是，没有元素抛异常NoSuchElementException

//        System.out.println(queue.pollFirst()); // 与poll()完全等价
//        System.out.println(queue.peekFirst()); // peek()完全等价
//        System.out.println(queue.removeFirst()); // remove()完全等价

//        System.out.println(queue.pollLast()); // 取出并移除最大的元素，没有元素返回null
//        System.out.println(queue.peekLast()); // 取出但不移除最大的元素，没有元素返回null
//        System.out.println(queue.removeLast()); // 与pollLast()的唯一区别是，没有元素抛异常NoSuchElementException

//        System.out.println(queue.toString());
    }

    public static class Person {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
