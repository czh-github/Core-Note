package com.laochen.jni.java.reflection;

import java.lang.reflect.Field;

/**
 * Date:2017/7/26 <p>
 * Author:chenzehao@danale.com <p>
 * Description:成员变量的反射
 */

public class ReflectField {


    public static void main(String[] args) throws Exception{
        // 获取声明的所有成员变量（包括public, protected, default, private，不包括继承的）
        // 如果Class object没有成员变量，或者其代表的是数组、基本数据类型，void，则返回数组长度为0
        Field[] declaredFields = Entry.class.getDeclaredFields();
        for (Field f : declaredFields) {
            System.out.println(f);
        }

        // 获取所有public成员变量（包括继承的）
        // 如果Class object没有成员变量，或者其代表的是数组、基本数据类型，void，则返回数组长度为0
        Field[] fields = Entry.class.getFields();
        for (Field f : fields) {
            System.out.println(f);
        }

        // 根据名称获取某个声明的成员变量
        Field declaredField = Entry.class.getDeclaredField("field1");
        System.out.println(declaredField);

        // 根据名称获取某个public成员变量
        Field field = Entry.class.getField("field4");
        System.out.println(field);

        Entry entry = new Entry(1.23f);

        // 通过反射set/get public成员变量的值
        field.set(entry, 400); // 如果是static成员变量，第一个参数传null
        System.out.println(field.getInt(entry));

        // 通过反射set/get 无访问权限的成员变量的值
        declaredField.setAccessible(true);
        declaredField.set(entry, 100); // 如果是static成员变量，第一个参数传null
        System.out.println(declaredField.get(entry));

    }
}
