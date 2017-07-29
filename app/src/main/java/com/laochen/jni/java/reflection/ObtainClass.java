package com.laochen.jni.java.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Date:2017/7/26 <p>
 * Author:chenzehao@danale.com <p>
 * Description:3种方式获取java.lang.Class的对象
 */

public class ObtainClass implements Edible {

    public static void main(String[] args) throws Exception{
        // 如果编译时知道类型的名字，可以这样获取对应的Class对象
        Class stringClass = String.class;
        System.out.println(stringClass.getName());

        // 如果在运行时知道类的全限定类名字符串，可以这样获取对应的Class对象
        String className = "java.lang.String";
        Class clazz = Class.forName(className);
        System.out.println(clazz.getName());

        // 知道某个类型的对象，可以这样获取对应的Class对象
        String s = "123";
        Class cls = s.getClass();
        System.out.println(cls.getName()); // java.lang.String 全限定类型名称（包括包名）
        System.out.println(cls.getSimpleName()); // String 简单类型（不包括包名）

//        modifiers(String.class);
//        packageInfo(String.class);
//        superClass(ObtainClass.class);
//        getInterfaces(ObtainClass.class);
//        getConstructors(ObtainClass.class);
//        getMethods(ObtainClass.class);
        getFields(ObtainClass.class);
    }

    /**
     * 检查Class对象的修饰符
     */
    public static void modifiers(Class cls) {
        int mod = cls.getModifiers();
        System.out.println(Modifier.isAbstract(mod));
        System.out.println(Modifier.isFinal(mod));
        System.out.println(Modifier.isInterface(mod));
        System.out.println(Modifier.isNative(mod));
        System.out.println(Modifier.isPrivate(mod));
        System.out.println(Modifier.isProtected(mod));
        System.out.println(Modifier.isPublic(mod));
        System.out.println(Modifier.isStatic(mod));
        System.out.println(Modifier.isStrict(mod));
        System.out.println(Modifier.isSynchronized(mod));
        System.out.println(Modifier.isStatic(mod));
        System.out.println(Modifier.isTransient(mod));
        System.out.println(Modifier.isVolatile(mod));
    }

    /**
     * 获取Class对象的包信息
     */
    public static void packageInfo(Class cls) {
        Package pkg = cls.getPackage();
        System.out.println(pkg.getName());
    }

    /**
     * 获取Class对象的直接父类信息
     */
    public static void superClass(Class cls) {
        Class superClass = cls.getSuperclass();
        System.out.println(superClass.getName());
    }

    /**
     * 获取Class对象实现的直接接口信息，不包括其父类实现的接口
     */
    public static void getInterfaces(Class cls) {
        Class[] interfaces = cls.getInterfaces();
        for (Class c : interfaces) {
            System.out.println(c.getName());
        }
    }

    /**
     * 获取Class对象所有public构造方法
     * 如果是数组，基本数据类型，void，返回数组长度为0.
     */
    public static void getConstructors(Class cls) {
        Constructor[] constructors = cls.getConstructors();
        for (Constructor c : constructors) {
            System.out.println(c.getName());
        }
    }

    /**
     * 获取Class对象所有public成员方法。
     * 如果是类，包括自己声明的，从父类继承的，从接口实现的。
     * 如果是接口，包括自己声明的，从父接口继承的。
     * 如果是数组，返回java.lang.Object的所有public成员方法。
     * 如果是基本数据类型或void，返回数组长度为0.
     */
    public static void getMethods(Class cls) {
        Method[] methods = cls.getMethods();
        for (Method m : methods) {
            System.out.println(m.getName());
        }
    }

    /**
     * 获取Class对象所有可访问的public成员变量。
     * 如果是类，包括自己声明的，从父类继承的，实现的接口里定义的常量。
     * 如果是接口，包括自己声明的，从父接口继承的。
     * 如果是数组，基本数据类型，void，返回数组长度为0.
     */
    public static void getFields(Class cls) {
        Field[] fields = cls.getFields();
        for (Field f : fields) {
            System.out.println(f.getName());
        }
    }

    /**
     * 获取Class对象所有注解
     */
    public static void getAnnotations(Class cls) {
        Annotation[] annotations = cls.getAnnotations();
        for (Annotation a : annotations) {
            System.out.println(a);
        }
    }

}
