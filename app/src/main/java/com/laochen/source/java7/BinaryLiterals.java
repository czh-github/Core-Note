package com.laochen.source.java7;

/**
 * Date:2017/8/1 <p>
 * Author:chenzehao@danale.com <p>
 * Description:二进制字面量
 * 可以表示整型 (byte, short, int, and long)
 * 以前缀0B或0b开始
 */

public class BinaryLiterals {
    public static void main(String[] args) {
        // 0B后面的01序列并不是对应内存单元中的bit，仅仅代表一个无符号整数
        // 个人觉得这种形式的二进制常量没有太大意义。
        byte b1 = 0b101;
        byte b2 = 0B01111111; // byte能表示的最大值是127，127的二进制表示是01111111，0B10000000会引发编译错误
        byte b3 = -0B10000000; // byte能表示的最小值是-128,128的二进制表示是10000000，-0B10000001会引发编译错误
        char b4 = 0B0100_0001; // 可以用下划线分割，65代表'A'
        System.out.println("----------Binary Literal in Byte----------------");
        System.out.println("max byte = " + Byte.MAX_VALUE);
        System.out.println("min byte = " + Byte.MIN_VALUE);
        System.out.println("b1 = " + b1); // 5
        System.out.println("b2 = " + b2); // 127
        System.out.println("b3 = " + b3); // -128
        System.out.println("b4 = " + b4); // A
    }
}
