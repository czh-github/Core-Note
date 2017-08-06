package com.laochen.source.java5.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * Date:2017/7/31 <p>
 * Author:chenzehao@danale.com <p>
 * Description:泛型
 * <p>
 * Type Parameters命名约定：
 * T - Type
 * E - Element
 * K - Key
 * N - Number
 * V - Value
 * S,U,V etc. - 2nd, 3rd, 4th types
 * <p>
 * 泛型的优点：
 * 1.类型安全：只能存储一种类型的对象。
 * 2.不需要对object类型转换。
 * 3.编译时检查：容易发现问题。
 *
 * 泛型中的继承：
 * 1.String是Object的子类，但List<String>不是List<Object>的子类。
 * 2.ArrayList是List的子类，ArrayList<String>就是List<String>的子类。
 * 3.List<? extends Integer>是<? extends Number>的子类。
 */

public class GenericExample {

    /**
     * 泛型类
     */
    static class MyGen<T> {
        T obj;

        void add(T obj) {
            this.obj = obj;
        }

        T get() {
            return obj;
        }
    }

    /**
     * 泛型方法
     */
    public static <E> void printArray(E[] elements) {
        for (E element : elements) {
            System.out.println(element);
        }
        System.out.println();
    }

    /**
     * 无界通配符?，?代表未知类型。
     */
    public static void print(List<?> list) {
        System.out.println(list.size());
    }

    /**
     * 上界通配符? extends X，其后面可以是类或接口
     * 支持指定多个界，<T extends A & B & C>，A,B,C中最多只能有一个类
     */
    public static void drawShapes(List<? extends Shape> lists) {
        for (Shape s : lists) {
            s.draw();//calling method of Shape class by child class instance
        }
    }

    /**
     * 下界通配符? super X
     */
    public static void addIntegers(List<? super Integer> list){
        list.add(new Integer(50));
    }


    /**
     * 为什么不能写List<Number> numbers = new ArrayList<Integer>();或者说为什么ArrayList<Integer>不是ArrayList<Number>的子类？
     * 因为会破坏类型安全。举个反例：
     * 如果List<Number> numbers = new ArrayList<Integer>();没问题
     * numbers里面的元素应该全都是Integer类型，
     * 又因为Double也是Number的子类，因此numbers.add(Double)编译也没问题
     * 这样numbers里面既有Integer元素又有Double元素，违背了泛型的初衷-类型安全。
     * 因此泛型不支持子类型！
     */
    public static void f1() {
//        List<Long> listLong = new ArrayList<Long>();
//        listLong.add(10L);
//        List<Number> listNumbers = listLong; // compiler error
//        listNumbers.add(1.23);
//        Long l = listLong.get(1); // 运行时错误
    }

    /**
     * 为什么不能创建泛型数组List<Integer>[] array = new ArrayList<Integer>[10];
     * 泛型的一个目的是编译的时候通过增加强制类型转换的代码，来避免用户编写出可能引发ClassCastException的代码。
     * 如果允许了new泛型数组，那么编译器添加的强制类型转换的代码就会有可能是错误的。
     * 以下面的例子举个反例：
     */
    public static void f2() {
//        ArrayList<String>[] stringList = new ArrayList<String>[5]; // compile error
//        Object[] objArray = stringList; // 任何数组都是Object[]的子类
//        List<Double> doubleList = new ArrayList<Double>();
//        doubleList.add(1.23);
//        objArray[0] = doubleList;
//        String s = stringList[0].get(0); // 运行时错误
    }

    /**
     * 为什么不能T[] t = new T[10];
     * 因为在创建数组时需要知道数组元素的确定类型，而且一直都会记得这个类型信息，每次往数组里添加元素，都会做类型检查。
     * 由于Java的泛型擦除，数组无法知道T的类型，因此无法创建。
     */
    public static void f3() {

    }

    public static void main(String[] args) {
        MyGen<Integer> m = new MyGen<Integer>();
        m.add(2);
        System.out.println(m.get());

        Integer[] intArray = {10, 20, 30, 40, 50};
        Character[] charArray = {'J', 'A', 'V', 'A', 'T', 'P', 'O', 'I', 'N', 'T'};
        printArray(intArray);
        printArray(charArray);

        List<Rectangle> list1=new ArrayList<Rectangle>();
        list1.add(new Rectangle());
        List<Circle> list2=new ArrayList<Circle>();
        list2.add(new Circle());
        list2.add(new Circle());
        drawShapes(list1);
        drawShapes(list2);
    }
}
