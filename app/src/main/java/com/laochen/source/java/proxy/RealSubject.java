package com.laochen.source.java.proxy;

/**
 * 委托类，或者被代理的类
 */
public class RealSubject implements Subject {
    @Override
    public void doSomething() {
        System.out.println("RealSubject call doSomething()");
    }
}
