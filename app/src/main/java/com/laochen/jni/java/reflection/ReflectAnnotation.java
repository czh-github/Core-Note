package com.laochen.jni.java.reflection;

import com.laochen.jni.java5.annotation.CustomAnnotationClass;
import com.laochen.jni.java5.annotation.CustomAnnotationMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Date:2017/7/27 <p>
 * Author:chenzehao@danale.com <p>
 * Description:注解的反射
 */

public class ReflectAnnotation {
    public static void main(String[] args) throws Exception {
        // 获得类的所有注解（包括自己声明的和从父类继承的）
        Annotation[] annotations = Entry.class.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }

        // 检查类是否有特定注解
        if (Entry.class.isAnnotationPresent(CustomAnnotationClass.class)) {
            CustomAnnotationClass annotation = Entry.class.getAnnotation(CustomAnnotationClass.class);
            System.out.println(annotation.author());
            System.out.println(annotation.date());
        }

        Method method = Entry.class.getMethod("method4", String.class);
        // 获得方法的所有注解
        Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
        for (Annotation annotation : declaredAnnotations) {
            System.out.println(annotation);
        }

        // 检查方法是否有特定注解
        if (method.isAnnotationPresent(CustomAnnotationMethod.class)) {
            CustomAnnotationMethod annotation = method.getAnnotation(CustomAnnotationMethod.class);
            System.out.println(annotation.author());
            System.out.println(annotation.date());
            System.out.println(annotation.description());
        }

        // 其他元素的注解类似
    }
}
