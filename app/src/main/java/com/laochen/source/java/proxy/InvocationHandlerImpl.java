package com.laochen.source.java.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 调用处理器实现类。
 * 这个类的目的是指定运行时生成的代理类需要完成的具体任务，代理类调用任何方法都会经过这个调用处理器类
 */
public class InvocationHandlerImpl implements InvocationHandler {
    private Object subject;

    public InvocationHandlerImpl(Object subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("PreProcess");
//        System.out.println("proxy:" + proxy.toString());
        System.out.println("method:" + method);
        System.out.println("args:" + Arrays.toString(args));
        //调用真实对象的方法
        Object returnValue = method.invoke(subject, args);
        System.out.println("PostProcess");

        return returnValue;
    }
}