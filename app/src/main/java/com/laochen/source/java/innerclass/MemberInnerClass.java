package com.laochen.source.java.innerclass;

/**
 * Created by Administrator on 2017/8/5.
 * 成员内部类：在类内部方法外部定义的非static类。
 * 成员内部类持有外部类对象的引用。
 */

public class MemberInnerClass {
    private int data = 30;

    // 类的访问修饰符与其它实例成员的访问修饰符意义相同
    class MemberInner {
        public void msg() {
            System.out.println("data is " + data);
        }
    }
    public static void main(String[] args) {
        MemberInnerClass outer = new MemberInnerClass();
        MemberInnerClass.MemberInner inner = outer.new MemberInner();
        inner.msg();
    }
}
/*
// 编译器生成的内部代码，成员内部类有外部类对象的引用
class MemberInnerClass$MemberInner {
    MemberInnerClass$MemberInner(MemberInnerClass var1) {
        this.this$0 = var1;
    }

    public void msg() {
        System.out.println("data is " + MemberInnerClass.access$000(this.this$0));
    }
}
 */
