package com.laochen.jni.java.reflection;

import java.lang.reflect.Method;

/**
 * Date:2017/7/26 <p>
 * Author:chenzehao@danale.com <p>
 * Description:成员方法的反射
 */

public class ReflectMethod {

    public static void main(String[] args) throws Exception {
        // 获得声明的所有成员方法(包括public, protected, default, private，不包括构造方法和继承的方法)
        // 如果Class object没有成员变量，或者其代表的是数组、基本数据类型，void，则返回数组长度为0
        Method[] declaredMethods = Entry.class.getDeclaredMethods();
        for (Method m : declaredMethods) {
            System.out.println(m);
        }

        // 获得所有public成员方法（包括继承的，不包括构造方法）
        // 如果Class object没有成员变量，或者其代表的是数组、基本数据类型，void，则返回数组长度为0
        Method[] methods = Entry.class.getMethods();
        for (Method m : methods) {
            System.out.println(m);
        }

        // 根据名称获取某个声明的成员方法
        Method declaredMethod = Entry.class.getDeclaredMethod("method1", null); // 方法没有参数，传null
        System.out.println(declaredMethod);

        // 根据名称获取某个public成员方法
        Method method = Entry.class.getMethod("method4", String.class);
        System.out.println(method);
        System.out.println(method.getParameterTypes()[0]); // 得到方法的参数类型，class java.lang.String
        System.out.println(method.getReturnType()); // 得到方法的返回类型，int

        Entry entry = new Entry(1.23f);
        // 通过反射调用public方法
        method.invoke(entry, ""); // 如果是static method，第一个参数传null

        // 通过反射调用无访问权限的方法
        declaredMethod.setAccessible(true);
        declaredMethod.invoke(entry, null); // 如果是static method，第一个参数传null

    }

    /**
     * 判断是否为getXxx方法
     */
    public static boolean isGetter(Method method) {
        if (!method.getName().startsWith("get")) return false;
        if (method.getParameterTypes().length != 0) return false;
        if (void.class.equals(method.getReturnType())) return false;
        return true;
    }

    /**
     * 判断是否为setXxx方法
     */
    public static boolean isSetter(Method method) {
        if (!method.getName().startsWith("set")) return false;
        if (method.getParameterTypes().length != 1) return false;
        return true;
    }
}
