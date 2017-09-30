package com.laochen.source.base;

import java.lang.*;
import java.lang.Object;

/**
 * Date:2017/9/19 <p>
 * Author:chenzehao@danale.com <p>
 * Description:浅拷贝，深拷贝
 * http://blog.csdn.net/zhangjg_blog/article/details/18369201/
 * 如果想要深拷贝一个对象， 这个对象必须要实现Cloneable接口，实现clone方法，并且在clone方法内部，
 * 把该对象引用的其他对象也要clone一份 ， 这就要求这个被引用的对象必须也要实现Cloneable接口并且实现clone方法。
 *
 * 要想实现彻底的深拷贝，必须要求引用链上所有类都实现Cloneable接口并且实现clone方法。
 *
 * clone操作由底层完成，不会调用构造方法。
 */

public class CloneableTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        Person p1 = new Person(1, 10, "ZhangSan");
        Person p2 = (Person) p1.clone();
        System.out.println(p1); // id=1,name=ZhangSan,age=10
        System.out.println(p2); // id=1,name=ZhangSan,age=10

        // int age字段肯定是深拷贝
        // 验证 String name是浅拷贝or深拷贝，实际返回true表示是浅拷贝
        System.out.println(p1.getName() == p2.getName());
    }
}

class Animal {
    protected int id;
    public Animal() {
        System.out.println("Animal无参构造器");
    }
    public Animal(int id) {
        this.id = id;
        System.out.println("Animal有参构造器");
    }
}

class Person extends Animal implements Cloneable {

    private int age ;
    private String name;

    public Person(int id, int age, String name) {
        super(id);
        this.age = age;
        this.name = name;
        System.out.println("Person有参构造器");
    }

    public Person() {
        System.out.println("Person无参构造器");
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // Object中的clone()实现是浅拷贝
        return super.clone();
    }

    @Override
    public String toString() {
        return "id=" + id + ",name=" + name + ",age=" + age;
    }
}


