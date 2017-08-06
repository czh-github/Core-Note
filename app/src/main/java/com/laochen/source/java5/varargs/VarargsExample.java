package com.laochen.source.java5.varargs;

/**
 * Date:2017/7/29 <p>
 * Author:chenzehao@danale.com <p>
 * Description:可变参数
 * 有两点需要注意：
 * 1.一个方法最多只能有一个可变参数
 * 2.可变参数必须是最后一个参数
 */

public class VarargsExample {
    public static void display(String... values) {
        System.out.println("display method invoked ");
        for (String s : values) {
            System.out.println(s);
        }
    }

    public static void main(String args[]) {
        display();//zero argument
        display("hello");//one argument
        display("my", "name", "is", "varargs");//four arguments
    }
}
