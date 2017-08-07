package com.laochen.source.java.innerclass;

/**
 * Created by Administrator on 2017/8/5.
 * 局部内部类：定义在方法里的类。
 * 如果局部内部类定义在实例方法里，那么局部内部类会持有外部类的引用。
 * 如果局部内部类定义在static方法里，那么局部内部类不会持有外部类的引用。
 */

public class LocalInnerClass {
    private int data = 30;
    private static int sData = 100;

    void display() {
        // 局部内部类不能有访问修饰符，因为它相当于局部变量，而局部变量不能是private, public or protected
        // 局部内部类不能被方法外调用，它的作用域仅限于从定义处开始到方法结束为止。
        // Java 8之前，局部内部类不能访问非final的局部变量，Java 8之后可以。
        final String s = "hello";
        class Local {
            void msg() {
                System.out.println(s);
            }
        }
        Local l = new Local();
        l.msg();
    }

    static void func() {
        class StaticLocal {
            void msg() {
                System.out.println(sData);
            }
        }
        StaticLocal l = new StaticLocal();
        l.msg();
    }

    public static void main(String args[]) {
        LocalInnerClass obj = new LocalInnerClass();
        obj.display();
        func();
    }
}
/*
编译器生成的内部代码，
class LocalInnerClass$1Local {
    LocalInnerClass$1Local(LocalInnerClass var1) {
        this.this$0 = var1;
    }

    void msg() {
        System.out.println(LocalInnerClass.access$000(this.this$0));
    }
}

class LocalInnerClass$1StaticLocal {
    LocalInnerClass$1StaticLocal() {
    }

    void msg() {
        System.out.println(LocalInnerClass.access$100());
    }
}
 */
