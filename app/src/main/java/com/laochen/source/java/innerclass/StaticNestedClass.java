package com.laochen.source.java.innerclass;

/**
 * Created by Administrator on 2017/8/6.
 * 静态嵌套类：在类内部方法外部定义的static类。
 * 只能访问外部类的静态成员和静态方法，不能访问实例成员和实例方法。
 * 能通过外部类的类名访问。
 * 静态嵌套类不持有外部类对象的引用。
 */

public class StaticNestedClass {
    static int data = 30;

    static class Inner {
        void msg() {
            System.out.println("data is " + data);
        }

        static void print() {
            System.out.println("data is " + data);
        }
    }

    public static void main(String args[]) {
        StaticNestedClass.Inner obj = new StaticNestedClass.Inner();
        obj.msg();
        StaticNestedClass.Inner.print();
    }
}
/*编译器生成的内部代码：
class StaticNestedClass$Inner {
    StaticNestedClass$Inner() {
    }

    void msg() {
        System.out.println("data is " + StaticNestedClass.data);
    }

    static void print() {
        System.out.println("data is " + StaticNestedClass.data);
    }
}
 */
