package com.laochen.source.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Date:2017/9/20 <p>
 * Author:chenzehao@danale.com <p>
 * Description:序列化
 *
 * 1.序列化是什么？
 * Java序列化是指把Java对象转换为字节序列的过程；而Java反序列化是指把字节序列恢复为Java对象的过程。
 *
 * 2.为什么要序列化？
 * 通过序列化可以把对象永久保存到硬盘上；通过网络传递序列化数据实现跨进程通信。
 *
 * 3.怎样序列化？
 * 1）类必须实现java.io.Serializable接口
 * 2）ObjectOutputStream.write(Object)和ObjectInputStream.readObject()
 * 3）如果想要定制序列化/反序列化过程，需要重写private void writeObject(java.io.ObjectOutputStream out) throws IOException
 *          和private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException;
 *          如果不重写，则执行默认的序列化/反序列化过程。
 *
 * 在序列化/反序列化过程中，虚拟机会试图调用writeObject(ObjectOutputStream) 和 readObject(ObjectInputStream) 方法，进行用户自定义的序列化和反序列化，
 * 如果没有这样的方法，则默认调用是 ObjectOutputStream 的 defaultWriteObject() 方法以及 ObjectInputStream 的 defaultReadObject 方法。
 * 用户自定义的 writeObject 和 readObject 方法可以允许用户控制序列化的过程，比如可以在序列化的过程中动态改变序列化的数值。
 * 基于这个原理，可以在实际应用中得到使用，用于敏感字段的加密工作。
 *
 * 4.父类的序列化
 * 一个子类实现了 Serializable 接口，但它的父类都没有实现 Serializable 接口，序列化该子类对象，然后反序列化后输出父类定义的某变量的数值，该变量数值与序列化时的数值不同。
 * 原因是要想将父类对象也序列化，就需要让父类也实现Serializable 接口。如果父类不实现的话的，就需要有默认的无参的构造方法。
 * 在父类没有实现 Serializable 接口时，虚拟机是不会序列化父对象的，而一个 Java 对象的构造必须先有父对象，才有子对象，反序列化也不例外。
 * 所以反序列化时，为了构造父对象，只能调用父类的无参构造函数作为默认的父对象。因此当我们取父对象的变量值时，它的值是调用父类无参构造函数后的值。
 * 如果你考虑到这种序列化的情况，在父类无参构造函数中对变量进行初始化，否则的话，父类变量值都 是默认声明的值，
 * 如 int 型的默认是 0，string 型的默认是 null。
 *
 * 5.静态成员变量序列化
 * 序列化不保存静态成员变量的值。原因是序列化保存的是对象的状态，静态变量属于类的状态。
 * 反序列化期间，static成员的值会从本地的Class中加载当前值。
 *
 * 6.transient关键字
 * transient 关键字的作用是控制变量的序列化，在变量声明前加上该关键字，可以阻止该变量被序列化到文件中，
 * 在被反序列化后，transient 变量的值被设为初始值，如 int 型的是 0，对象型的是 null。
 * 把不需要序列化的字段放到未实现Serializable的父类中也可以达到相同目的。
 *
 * 7.serialVersionUID
 * 对象序列化时，类名和serialVersionUID都会被写入字节流中。
 * 反序列化期间，JVM读取字节流检查serialVersionUID，跟本地的同包同名的Class声明的serialVersionUID进行比较，
 * 如果相同则可反序列化成功，否则，会抛出InvalidClassException。
 * 如果类实现了Serializable接口，强烈建议显式声明serialVersionUID的值。因为如果不显式声明，JVM会生成一个默认的，并且该默任值
 * 高度依赖类的详情和编译器的实现，这些不确定性很可能在反序列化时引发InvalidClassException。
 * 类型必须是static final long，强烈建议修饰符为private，因为这样能保证只对本类有效，对子类无影响。
 *
 * 8.序列化存储规则
 * 同一对象两次写入文件，文件只保存一份对象的数据和用若干字节存储引用信息。反序列化依次读取两个对象，发现两个引用指向同一个对象。
 */

public class SerializableTest {
    private static final String PATH = "E:\\AndroidStudioProjects\\Core-Note\\app\\src\\main\\java\\com\\laochen\\source\\base";
    public static void main(String[] args) throws Exception {
        testPerson2();

    }

    private static void testPerson1() throws Exception {
        Person1 p1 = new Person1("ZhangSan", 20, 1);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(PATH, "result.obj")));
        out.writeObject(p1);
        out.close();

        // 序列化后修改静态成员的值
        Person1.count = 10;

        ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(PATH, "result.obj")));
        Person1 p2 = (Person1) in.readObject();
        System.out.println(p2); // [name=null, age=0], sex=1, password=null
        System.out.println(p1 == p2); // false
        System.out.println(p2.count); // 10
    }

    private static void testPerson2() throws Exception {
        Person2 p1 = new Person2("ZhangSan", 20, 1);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(PATH, "result.obj")));
        out.writeObject(p1);
        out.close();

        // 序列化过程中，由于只有子类实现了Serializable，父类没有实现Serializable，因此只有子类对象执行了序列化
        // 反序列化过程中，要构造出子类对象，必须先构造出父类对象，因此父类的无参构造方法会被调用。
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(PATH, "result.obj")));
        Person2 p2 = (Person2) in.readObject();
        System.out.println(p2); // [name=null, age=0], sex=0, password=password
    }

    private static void testSaveRule() throws Exception {
        Person1 p1 = new Person1("ZhangSan", 20, 1);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(PATH, "result.obj")));
        p1.i = 100;
        out.writeObject(p1);
        out.flush();
        System.out.println(new File(PATH, "result.obj").length()); // 70
        p1.i = 200;
        out.writeObject(p1);
        out.close();
        System.out.println(new File(PATH, "result.obj").length()); // 75，多5个字节

        ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(PATH, "result.obj")));
        Person1 o1 = (Person1) in.readObject();
        Person1 o2 = (Person1) in.readObject();
        System.out.println(o1 == o2); // true
        System.out.println(o1.i); // 100
        System.out.println(o2.i); // 100
    }

}

class Parent {
    private String name;
    private int age;

    public Parent() {
        System.out.println("Parent");
    }

    public Parent(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "[name=" + name + ", age=" + age + "]";
    }
}

class Person1 extends Parent implements Serializable {
    private static final long serialVersionUID = 1L;
    private int sex;
    private transient String password = "cabckascbalmo";
    public static int count = 5;
    public int i;

    public Person1(String name, int age, int sex) {
        super(name, age);
        this.sex = sex;
    }

    @Override
    public String toString() {
        return super.toString() + ", sex=" + sex + ", password=" + password;
    }
}

    class Person2 extends Parent implements Serializable {
        private static final long serialVersionUID = 1L;
        private int sex;
        private transient String password = "password";

        public Person2(String name, int age, int sex) {
            super(name, age);
            this.sex = sex;
            System.out.println("Parent2");
        }

        // 控制序列化过程
        private void writeObject(java.io.ObjectOutputStream out) throws IOException {
            // Write the non-static and non-transient fields of the current class to this stream.
            out.defaultWriteObject();

            out.writeUTF(password + "123"); // 模拟加密
        }

        // 控制反序列化过程
        private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();

            String encryption = in.readUTF();
            int i = encryption.indexOf("123");
            password = encryption.substring(0, i); // 模拟解密

        }


        @Override
        public String toString() {
            return super.toString() + ", sex=" + sex + ", password=" + password;
        }

}
