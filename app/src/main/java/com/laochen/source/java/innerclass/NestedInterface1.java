package com.laochen.source.java.innerclass;

/**
 * Created by Administrator on 2017/8/6.
 * 内嵌接口：定义在某个类或某个接口里的接口。
 * 好处是把相关的接口聚集在一起，便于维护。
 * 内嵌接口不能直接访问，只能通过外部类或外部接口间接访问。
 *
 */

public interface NestedInterface1 {
    int i = 10; // 隐含是public static final，不能用其它修饰符显式修饰
    void show(); // 隐含是public的，不能用其它修饰符显式修饰
    // 同理，接口里的内嵌接口隐含是public static的，不能用其它修饰符显式修饰。
    interface Interface1 {
        void msg();
    }
}

class Interface1Impl implements NestedInterface1.Interface1 {
    @Override
    public void msg() {
        System.out.println("Interface1Impl#msg()");
    }
}
