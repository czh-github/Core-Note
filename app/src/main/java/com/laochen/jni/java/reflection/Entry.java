package com.laochen.jni.java.reflection;

import com.laochen.jni.java5.annotation.CustomAnnotationClass;
import com.laochen.jni.java5.annotation.CustomAnnotationMethod;

/**
 * Date:2017/7/26 <p>
 * Author:chenzehao@danale.com <p>
 * Description:实体类，用于测试
 */
@CustomAnnotationClass(author = "LaoChen", date = "2017/7/27")
public class Entry {
    public Entry(float f) {

    }

    private Entry(int i) {

    }

    Entry(String s) {

    }

    private int field1 = 1;
    int field2 = 2;
    protected int field3 = 3;
    public int field4 = 4;

    private void method1() {
        System.out.println("method1");
    }

    void method2() {
        System.out.println("method2");
    }

    protected void method3() {
        System.out.println("method3");
    }

    @CustomAnnotationMethod(author = "laochen", date = "2017/7/27", description = "annotate method4")
    public int method4(String s) {
        System.out.println("method4");
        return 0;
    }
}
