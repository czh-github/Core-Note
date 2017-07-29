package com.laochen.jni.java5.annotation;

/**
 * Date:2017/7/25 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class AnnotatedSubClass extends AnnotatedSuperClass {

    @Override
    public void oneMethod() {
    }

    public static void main(String[] args) {
        System.out.println("is true: " + AnnotatedSuperClass.class.isAnnotationPresent(InheritedAnnotation.class));
        System.out.println("is true: " + AnnotatedSubClass.class.isAnnotationPresent(InheritedAnnotation.class));
    }
}
