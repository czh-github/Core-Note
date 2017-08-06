package com.laochen.source.java.innerclass;

/**
 * Created by Administrator on 2017/8/6.
 * 类里面定义嵌套接口
 */

public class NestedInterface2 {

    // 隐含是static的，能用任何访问修饰符来修饰，跟类的成员一样。
    interface Interface2 {
        void show();
    }

    public static void main(String[] args) {
        Interface1Impl impl = new Interface1Impl();
        impl.msg();

        Interface2Impl impl2 = new Interface2Impl();
        impl2.show();
    }
}

class Interface2Impl implements NestedInterface2.Interface2 {
    @Override
    public void show() {
        System.out.println("Interface2Impl#show()");
    }
}
