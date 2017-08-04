package com.laochen.jni.java5.enumtype;

import java.lang.reflect.Constructor;

/**
 * Date:2017/7/29 <p>
 * Author:chenzehao@danale.com <p>
 * Description:枚举：java的一种数据类型，包含固定数量的常量。
 * 枚举常量隐含是static final的
 * 所有枚举类型都继承自{@link java.lang.Enum}
 * 直接new枚举类会产生编译错误
 * 父类Enum的final clone()直接抛异常确保枚举常量不能被cloned
 * 特殊的机制保证不能通过反序列化创建实例
 * 通过反射创建实例也不行（看newInstance()源码）
 * 这4条确保除定义外别处没有枚举实例。
 *
 * http://blog.csdn.net/javazejian/article/details/71333103
 */

public class EnumExample {
    // 定义一个枚举类型Season
    public enum Season {
        WINTER, SPRING, SUMMER, FALL
    }

    public static void main(String[] args) throws Exception {
        // values()方法是编译器内部生成的
        for (Season s : Season.values())
            System.out.println(s);

        Constructor<?>[] declaredConstructors = Season.class.getDeclaredConstructors();
        for (Constructor c : declaredConstructors) {
            // private com.laochen.jni.java5.enumtype.EnumExample$Season(java.lang.String,int)
            System.out.println(c);
        }

        Constructor<Season> constructor = Season.class.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        //  java.lang.IllegalArgumentException: Cannot reflectively create enum objects
        Season autumn = constructor.newInstance("Autumn", 4);

    }
/*
    // 编译器内部是这样声明枚举类Season的
    public static final class EnumExample$Season extends Enum
    {
        private EnumExample$Season(String s, int i)
        {
            super(s, i);
        }

        // 编译器生成的values()方法
        public static EnumExample$Season[] values()
        {
            return (EnumExample$Season[])$VALUES.clone();
        }

        // 编译器生成的valueOf(String)方法
        public static EnumExample$Season valueOf(String s)
        {
            return (EnumExample$Season)Enum.valueOf(EnumExample$Season, s);
        }

        public static final EnumExample$Season WINTER;
        public static final EnumExample$Season SPRING;
        public static final EnumExample$Season SUMMER;
        public static final EnumExample$Season FALL;
        private static final EnumExample$Season $VALUES[];

        static
        {
            WINTER = new EnumExample$Season("WINTER", 0);
            SPRING = new EnumExample$Season("SPRING", 1);
            SUMMER = new EnumExample$Season("SUMMER", 2);
            FALL = new EnumExample$Season("FALL", 3);
            $VALUES = (new EnumExample$Season[] {
                    WINTER, SPRING, SUMMER, FALL
            });
        }

    }
*/
}
