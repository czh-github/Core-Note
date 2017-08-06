package com.laochen.source.java5.assertion;

import java.util.Scanner;

public class AssertionExample {
    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your age ");

        int value = scanner.nextInt();
        assert value >= 18; // assert方式一

        assert value>=18:" Not valid"; // assert方式二

        System.out.println("value is " + value);
    }
    // 需要在运行时开启assertion才有效
    // javac com/laochen/jni/java5/assertion/AssertionExample.java
    // java -ea com/laochen/jni/java5/assertion/AssertionExample
}  