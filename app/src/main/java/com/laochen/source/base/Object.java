package com.laochen.source.base;

/**
 * Date:2017/9/18 <p>
 * Author:chenzehao@danale.com <p>
 * Description:翻译{@link java.lang.Object}
 * Java 7 的源码
 */

public class Object {
    /**
     *
     */
    public final native Class<?> _getClass();

    /**
     * 返回对象的hash code。该方法主要为基于哈希表的数据结构和算法服务，例如HashMap和HashSet。
     * hash code需要满足这些（约定俗称的）规约：
     * 1.Java程序的一次执行过程中，多次执行同一个对象的hashCode()应该返回相同的整数值（假设equals()里用于比较的信息没有修改）。相同程序的不同执行过程不一定要保证。
     * 3.实现equals()方法的同时一定也要实现hashCode()方法。
     * 4.如果两个对象通过{@link java.lang.Object#equals(java.lang.Object)}方法返回true，那么这两个对象的hashCode()返回值必须相同。
     * 5.如果两个对象通过{@link java.lang.Object#equals(java.lang.Object)}方法返回false，那么这两个对象的hashCode()返回值不一定非得不同。
     * 6.如果两个对象的hashCode()返回值相同，那么它们通过equals()比较不一定非得为true。
     * 7.根据实践经验，如果两个对象通过equals()比较不同，定义它们的hashCode()返回值不同，有利于提高hash table的性能（减小hash冲突）。
     *
     * 如果ObjectA.class的equals()相等，hashCode()不等，很可能出现把ObjectA作为key时插入到哈希表后，通过相等的ObjectA查找不到对用value的情况。
     */
    public native int _hashCode();

    /**
     * 判断某个其它对象与该对象是否相等。
     * 实现该方法需要满足这些规约：
     * 1.自反性：对于任意非null引用x，x.equals(x)应该返回true。
     * 2.对称性：对于任意非null引用x和y，x.equals(y)返回true当且仅当y.equals(x)返回true。
     * 3.传递性：对于任意非null引用x和y和z，如果x.equals(y)返回true并且y.equals(z)返回true，那么x.equals(z)应该返回true。
     * 4.一致性：对于任意非null引用x和y，多次调用x.equals(y)应该总是返回相同的结果（假设equals()里用于比较的信息没有修改）。
     * 5.非空性：对于任意非null引用x，x.equals(null)应该总是返回false。
     *
     * {@link java.lang.Object#equals(java.lang.Object)}的默认实现是，任意非null引用x和y，当且仅当x和y指向相同对象才返回true。
     * 该方法通常需要重写，连带hashCode()也需要重写。
     */
    public boolean _equals(Object obj) {
        return (this == obj);
    }

    /**
     * 创建并返回该对象的一个副本。
     * 某个对象要想调用clone()，前提是他的类必须实现了{@link java.lang.Cloneable}接口，否则抛CloneNotSupportedException。
     * Cloneable接口只是一个标志性接口，没有提供clone()方法。
     * 实现了Cloneable接口的类必须合理重写clone()方法。
     * clone()的默认实现是浅拷贝。
     */
    protected native Object _clone() throws CloneNotSupportedException;

    public String _toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

    public final native void _notify();

    public final native void _notifyAll();

    public final native void _wait(long millis) throws InterruptedException;

    public final void _wait(long millis, int nanos) throws InterruptedException {
        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (nanos < 0 || nanos > 999999) {
            throw new IllegalArgumentException(
                    "nanosecond timeout value out of range");
        }

        if (nanos >= 500000 || (nanos != 0 && millis == 0)) {
            millis++;
        }

        _wait(millis);
    }

    public final void _wait() throws InterruptedException {
        _wait(0);
    }

    /**
     * 由谁调用：垃圾回收器。Java语言不能保证哪个线程会调用该方法，但能保证调用线程不会持有任何用户可见的同步锁。
     * 什么时机调用：垃圾回收器确定该对象没有任何引用（任何活着的线程没有任何方式能够访问到该对象），但不能确定是某个具体时间调用。
     * 可以干什么：可以执行任何动作，包括使该对象对其它线程再次可用。主要用来清理资源（关闭数据库连接，关闭文件等）。
     * 注意的细节：
     * 当垃圾回收器调用了对象的finalize()后，该对象就完全销毁了。
     * 对于任意对象，JVM调用该对象的finalize()最多一次。
     * 如果该方法内抛出异常，该方法的执行会终止，但异常会被忽略。
     * 很可能出现finalize()never被调用。原因是对象始终是可达的或者可被回收但JVM已经停止导致垃圾回收器没有机会执行。
     * 因此不要依赖finalize()清理资源。
     *
     * 通过执行Runtime.getRuntime().runFinalization()或者Runtime.runFinalizersOnExit(true)可以主动促使finalize()被调用。
     * 但都有缺陷，前者仅仅是尽最大努力去促进，不能保证一定执行；后者已过时，因为有时会导致在活着的对象上调用finalize()。
     *
     * 也可以调用System.gc() or RunTime.getRunTime().gc()主动请求垃圾回收器执行，但仅仅是一个请求，不能保证垃圾回收器一定执行。
     *
     * 可以显式调用对象的finalize()，该方法会执行，但对象不会销毁。
     */
    protected void _finalize() throws Throwable { }
}
