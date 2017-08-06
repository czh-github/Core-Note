package com.laochen.source.java.innerclass;

/**
 * Created by Administrator on 2017/8/5.
 * 匿名内部类：编译器决定名字。
 * 如果匿名内部类出现在外部类的实例成员变量或实例成员方法里，那么匿名内部类会持有外部类对象的引用。
 * 如果匿名内部类出现在外部类的静态成员变量或静态成员方法里，那么匿名内部类不会持有外部类对象的引用。
 */

public class AnonymousInnerClass {
    private int data1 = 30;
    private int data2 = 50;

    private static int sData = 100;
    private static Person sPerson = new Person() { // 不持有外部类对象的引用。
        @Override
        void eat() {
            System.out.println("data:" + sData);
        }
    };

    void display1() {
        Person p = new Person() { // 持有外部类对象的引用。
            @Override
            void eat() {
                System.out.println("data:" + data1);
            }
        };
        p.eat();
    }

    void display2() {
        Eatable e = new Eatable() { // 持有外部类对象的引用。
            @Override
            public void eat() {
                System.out.println("data:" + data2);
            }
        };
        e.eat();
    }

    public static final void main(String[] args) {
        AnonymousInnerClass obj = new AnonymousInnerClass();
        obj.display1();
        Eatable e = new Eatable() { // 不持有外部类对象的引用。
            @Override
            public void eat() {
                System.out.println("main");
            }
        };
        e.eat();
    }
}

abstract class Person{
    abstract void eat();
}

interface Eatable {
    void eat();
}

/*
编译器生成的内部代码，匿名内部类没有外部类对象的引用
final class AnonymousInnerClass$1 extends Person {
    AnonymousInnerClass$1() {
    }

    void eat() {
        System.out.println("data:" + AnonymousInnerClass.access$000());
    }
}

class AnonymousInnerClass$2 extends Person {
    AnonymousInnerClass$2(AnonymousInnerClass var1) {
        this.this$0 = var1;
    }

    void eat() {
        System.out.println("data:" + AnonymousInnerClass.access$100(this.this$0));
    }
}

class AnonymousInnerClass$3 implements Eatable {
    AnonymousInnerClass$3(AnonymousInnerClass var1) {
        this.this$0 = var1;
    }

    public void eat() {
        System.out.println("data:" + AnonymousInnerClass.access$200(this.this$0));
    }
}

final class AnonymousInnerClass$4 implements Eatable {
    AnonymousInnerClass$4() {
    }

    public void eat() {
        System.out.println("main");
    }
}

 */
