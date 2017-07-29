package com.laochen.jni.java5.enumtype;

/**
 * Date:2017/7/29 <p>
 * Author:chenzehao@danale.com <p>
 * Description:枚举：java的一种数据类型，包含固定数量的常量。
 * 枚举常量隐含是static final的
 * 所有枚举类型都继承自{@link java.lang.Enum}
 */

public class EnumExample {
    // 定义一个枚举类型Season
    public enum Season {
        WINTER, SPRING, SUMMER, FALL
    }

    public static void main(String[] args) {
        for (Season s : Season.values())
            System.out.println(s);
    }
}
