package com.laochen.source.java.reflection;

import java.lang.reflect.Constructor;

/**
 * Date:2017/7/26 <p>
 * Author:chenzehao@danale.com <p>
 * Description:构造方法的反射
 */

public class ReflectConstructor {

    public static void main(String[] args) throws Exception{
        // 获取声明的所有构造方法
        Constructor<?>[] declaredConstructors = Entry.class.getDeclaredConstructors();
        for (Constructor c : declaredConstructors) {
            System.out.println(c);
        }

        // 获取所有public构造方法
        Constructor<?>[] constructors = Entry.class.getConstructors();
        for (Constructor c : constructors) {
            System.out.println(c);
        }

        // 根据参数类型获取某个声明的构造方法
        Constructor<Entry> declaredConstructor = Entry.class.getDeclaredConstructor(int.class);
        System.out.println(declaredConstructor);

        // 根据参数类型获取某个public构造方法
        Constructor<Entry> constructor = Entry.class.getConstructor(float.class);
        System.out.println(constructor);

        // 获取构造方法的参数类型数组
        Class[] paramTypes = constructor.getParameterTypes();
        for (Class cls : paramTypes) {
            // float
            System.out.println(cls.getName());
        }

        // 通过反射调用非public构造方创建对象
        declaredConstructor.setAccessible(true);
        Entry entry = declaredConstructor.newInstance(100);
        entry.method4("");

        // 通过反射调用public构造方创建对象
        constructor.newInstance(1.23f).method4("");
    }
}
