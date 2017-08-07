package com.laochen.source;

/**
 * Date:2017/6/28 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class Person {
    private String name;
    private int age;
    public char sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public native void modifyFieldFromJNI(int age);
    public native void callInstanceMethod();
}
