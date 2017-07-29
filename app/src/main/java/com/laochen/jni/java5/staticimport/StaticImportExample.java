package com.laochen.jni.java5.staticimport;

import static java.lang.System.out;

/**
 * Date:2017/7/29 <p>
 * Author:chenzehao@danale.com <p>
 * Description:静态导入
 * 普通导入：允许Java程序访问某个包中的类而无需指定包限定名，提供了访问类的方式
 * 静态导入：允许Java程序访问某个类的静态成员而无需指定类限定名，提供了访问类的静态成员的方式
 * 静态导入的好处是代码量减少，尤其是访问频繁的静态成员。
 * 静态导入的缺点是，滥用会导致程序的可读性和可维护性降低。
 */

public class StaticImportExample {
    public static void main(String[] args) {
        out.println("Hello");
        out.println("World");
    }
}
