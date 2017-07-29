package com.laochen.jni.java.exception;

/**
 * Date:2017/7/29 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class ThrowError {
    public static void badMethod() {
        throw new Error();
    }

    public static void main(String[] args) {
        try {
            badMethod();
            System.out.print("A");
        } catch (Exception e) {
            System.out.print("B");
        } finally {
            System.out.print("C");
        }
        System.out.print("D");
    }
    // 程序运行结果是：打印C后，程序退出并打印一些错误信息
    // 原因是Error被抛出后无法被识别，因为catch只能捕获Exception及其子类，但Error不是Exception的子类。
    // 程序发生运行时错误，退出之前会执行finally块内的语句。
}
