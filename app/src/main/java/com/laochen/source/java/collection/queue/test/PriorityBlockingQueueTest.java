package com.laochen.source.java.collection.queue.test;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Date:2017/7/12 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class PriorityBlockingQueueTest {

    public static void main(String[] args) {
        PriorityBlockingQueue<Person> queue = new PriorityBlockingQueue<>(5, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                if (o1.age > o2.age) {
                    return -1;
                } else if (o1.age < o2.age) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        queue.put(new Person("444", 4));
        queue.put(new Person("222", 2));
        queue.put(new Person("111", 1));
        queue.put(new Person("555", 5));
        queue.put(new Person("333", 3));
        queue.put(new Person("666", 6));

        System.out.println(queue);

        System.out.println(queue.poll());
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
            return name + ":" + age;
        }
    }
}
