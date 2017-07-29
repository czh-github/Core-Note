package com.laochen.jni.java.proxy;

/**
 * 代理是一种常用的设计模式，其目的就是为真实对象提供一个代理以控制对这个对象的访问。
 * 代理类负责为委托类预处理消息，过滤消息并转发消息，以及进行消息被委托类执行后的后续处理。
 *
 * 该类是静态代理：代理类是在编译时就实现好的。也就是说 Java 编译完成后代理类是一个实际的 class 文件。
 */
public class ProxySubject implements Subject {
    private Subject realSubject;

    public ProxySubject(Subject realSubject) {
        this.realSubject = realSubject;
    }

    @Override
    public void doSomething() {
        System.out.println("PreProcess");
        realSubject.doSomething();
        System.out.println("PostProcess");
    }
}
