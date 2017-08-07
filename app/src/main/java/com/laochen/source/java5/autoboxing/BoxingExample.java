package com.laochen.source.java5.autoboxing;

/**
 * Date:2017/7/29 <p>
 * Author:chenzehao@danale.com <p>
 * Description:自动装箱，自动拆箱
 * 自动装箱：原始数据类型自动转换为对应的包装类型，例如int->Integer
 * 自动拆箱：反过来。例如Integer->int
 */

public class BoxingExample {
    static void f(Integer i) {
        System.out.println("Integer");
    }

    static void f(Integer i, Integer j) {
        System.out.println("Integer Integer");
    }

    static void f(Integer... i) {
        System.out.println("Integer...");
    }

    static void f(int i) {
        System.out.println("int");
    }

    static void f(int i, int j) {
        System.out.println("int int");
    }
    static void f(int... i) {
        System.out.println("int...");
    }

    public static void main(String[] args) {
        int i = 10;
        Integer it = new Integer(i); // 手动装箱
        System.out.println(it);

        Integer it2 = i; // 自动装箱
        System.out.println(it2);

        int j = it; // 自动拆箱
        System.out.println(j);

        short s = 100;
        f(s); // int
        f(s, s); // int int
        f(Integer.valueOf(100)); // Integer
//        f(100, Integer.valueOf(100)); // error
        // 实际参数可以匹配多个重载方法时，基本类型>包装类型>可变参数
    }
}
