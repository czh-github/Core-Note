package com.laochen.source.java5.staticimport;

import static java.lang.System.out;

/**
 * Date:2017/7/29 <p>
 * Author:chenzehao@danale.com <p>
 * Description:静态导入
 * 普通导入：导入包名后直接使用包中的类，提供了访问类的方式
 * 静态导入：导入类名后直接使用类中的static成员，提供了访问类的静态成员的方式
 * 静态导入的好处是代码量减少，尤其是访问频繁的静态成员。
 * 静态导入的缺点是，滥用会导致程序的可读性和可维护性降低。
 */

public class StaticImportExample {
    public static void main(String[] args) {
        out.println("Hello");
        out.println("World");
    }
}
