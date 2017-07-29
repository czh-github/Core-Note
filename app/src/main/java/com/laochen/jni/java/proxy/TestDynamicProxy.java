package com.laochen.jni.java.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Date:2017/7/27 <p>
 * Author:chenzehao@danale.com <p>
 * Description:测试动态代理
 * 动态代理：代理类是在运行时生成的。也就是说 Java 编译完之后并没有实际的 class 文件，而是在运行时动态生成的类字节码，并加载到JVM中。
 */

public class TestDynamicProxy {
    public static void main(String[] args) {
        // 真实对象
        RealSubject realSubject = new RealSubject();

        InvocationHandler handler = new InvocationHandlerImpl(realSubject);

        ClassLoader loader = realSubject.getClass().getClassLoader();
        Class[] interfaces = realSubject.getClass().getInterfaces();

        // 得到代理对象
        Subject proxySubject = (Subject) Proxy.newProxyInstance(loader, interfaces, handler);

        System.out.println("动态代理对象的类型：" + proxySubject.getClass().getName());

        proxySubject.doSomething();
    }
}
