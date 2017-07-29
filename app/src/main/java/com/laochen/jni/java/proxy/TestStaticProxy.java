package com.laochen.jni.java.proxy;

/**
 * 测试静态代理
 */
public class TestStaticProxy {
    public static void main(String args[]) {
        RealSubject realSubject = new RealSubject();
        ProxySubject proxySubject = new ProxySubject(realSubject);
        proxySubject.doSomething();
    }
}