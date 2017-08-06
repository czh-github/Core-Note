package com.laochen.source.java.reflection;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Date:2017/7/27 <p>
 * Author:chenzehao@danale.com <p>
 * Description:数组的反射。编译时不知道数组的类型，运行时才知道，就需要用反射来创建数组了。
 */

public class ReflectArray {
    public static void main(String[] args) throws Exception {
        // 创建数组
        int[] intArray = (int[]) Array.newInstance(int.class, 3);
        System.out.println(Arrays.toString(intArray));

        // 访问数组
        Array.setInt(intArray, 0, 123); // 等价于intArray[0] = 100;
        Array.setInt(intArray, 1, 456);
        Array.setInt(intArray, 2, 789);
        System.out.println(Arrays.toString(intArray));
        System.out.println(Array.getInt(intArray, 0)); // 等价于intArray[0]
        System.out.println(Array.getInt(intArray, 1));
        System.out.println(Array.getInt(intArray, 2));

        // 获取数组类型对应的Class object
        System.out.println(String[].class.getName()); // [Ljava.lang.String;
        System.out.println(int[].class.getName()); // [I
        System.out.println(long[].class.getName()); // [J
        System.out.println(float[].class.getName()); // [F
        System.out.println(double[].class.getName()); // [D
        System.out.println(byte[].class.getName()); // [B
        System.out.println(char[].class.getName()); // [C
        System.out.println(short[].class.getName()); // [S
        System.out.println(boolean[].class.getName()); // [Z

        // 获取数组类型的元素类型
        System.out.println(String[].class.getComponentType().getName()); // java.lang.String

    }
}
