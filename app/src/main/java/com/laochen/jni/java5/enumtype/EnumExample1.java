package com.laochen.jni.java5.enumtype;

/**
 * Date:2017/7/29 <p>
 * Author:chenzehao@danale.com <p>
 * Description:枚举：java的一种数据类型，包含固定数量的常量。
 * 枚举常量隐含是static final的
 * 所有枚举类型都继承自{@link Enum}
 */

public class EnumExample1 {
    // 定义一个枚举类型Season
    public enum Season {
        WINTER(5), SPRING(10), SUMMER(15), FALL(20);
        private int value;
        private Season(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        for (Season s : Season.values()) {
            System.out.println(s.getValue());
            System.out.println(s.ordinal());
        }
    }
/*
    // 编译器内部是这样声明枚举类Season的
   public static final class EnumExample1$Season extends Enum
  {
    private EnumExample1$Season(String s, int i, int value)
    {
        super(s, i);
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    public static EnumExample1$Season[] values()
    {
        return (EnumExample1$Season[])$VALUES.clone();
    }

    public static EnumExample1$Season valueOf(String s)
    {
        return (EnumExample1$Season)Enum.valueOf(EnumExample1$Season, s);
    }

    public static final EnumExample1$Season WINTER;
    public static final EnumExample1$Season SPRING;
    public static final EnumExample1$Season SUMMER;
    public static final EnumExample1$Season FALL;
    private static final EnumExample1$Season $VALUES[];
    private int value;

    static
    {
        WINTER = new EnumExample1$Season("WINTER", 0, 5);
        SPRING = new EnumExample1$Season("SPRING", 1, 10);
        SUMMER = new EnumExample1$Season("SUMMER", 2, 15);
        FALL = new EnumExample1$Season("FALL", 3, 20);
        $VALUES = (new EnumExample1$Season[] {
            WINTER, SPRING, SUMMER, FALL
        });
    }

}
*/
}
