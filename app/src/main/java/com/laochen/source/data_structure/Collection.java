package com.laochen.source.data_structure;

/**
 * Date:2017/9/20 <p>
 * Author:chenzehao@danale.com <p>
 * Description:翻译{@link java.util.Collection}
 * （可选）的含义是，对于不符合要求的元素进行操作，可能抛出异常，也可能操作成功，因实现的不同而异。
 */

public interface Collection<E> extends Iterable<E> {
    /**
     * 返回集合中元素的数量。
     * 如果数量超过 Integer.MAX_VALUE，返回Integer.MAX_VALUE。
     * @return 集合中元素的数量
     */
    int size();

    /**
     * 如果集合中没有元素返回true，否则返回false。
     * @return true如果集合中没有元素。
     */
    boolean isEmpty();

    /**
     * 如果集合中包含指定元素返回true，否则返回false。
     * @param o 待检测的元素
     * @return true如果集合中包含指定元素。
     * @throws ClassCastException 如果指定元素的类型与集合类型不兼容（可选）
     * @throws NullPointerException 如果指定元素为null并且集合不容许null元素（可选）
     */
    boolean contains(Object o);

    /**
     * 返回此集合中的所有元素的迭代器，不一定保证返回元素的顺序。
     * @return 返回此集合中的所有元素的迭代器
     */
    java.util.Iterator<E> iterator();

    /**
     * 返回包含了集合中所有元素的数组。
     * 如果集合对迭代器返回元素的顺序有保证，那么数组中的元素顺序应该与之保持一致。
     * 要求该方法必须创建新数组。
     * @return 包含了集合中所有元素的数组。
     */
    Object[] toArray();

    /**
     * 返回包含了集合中所有元素的数组；返回的数组的运行时类型跟参数数组类型一样。
     * 如果参数数组能够容纳集合中的所有元素，那么返回的数组即为参数数组；
     * 否则，一个新的数组会被创建，数组的类型是参数数组的运行时类型，数组的size是集合的size。
     * 如果集合对迭代器返回元素的顺序有保证，那么返回的数组中的元素顺序应该与之保持一致。
     * <tt>toArray(new Object[0])</tt>的功能与<tt>toArray()</tt>相同。
     * @param a 如果数组长度足够那么集合中的元素会存入该数组，否则相同运行时type，size为集合size的数组被创建。
     * @param <T> 数组的泛型类型。
     * @return 返回包含了集合中所有元素的数组。
     * @throws ArrayStoreException 如果参数数组的运行时type不是集合中元素类型的父类型
     * @throws NullPointerException 如果参数数组为null
     */
    <T> T[] toArray(T[] a);

    /**
     * 保证集合包含了指定元素（可选操作，因为有些集合对添加元素有限制或者不支持添加元素，需要在文档中说明）。
     * 返回true如果集合因此次调用而改变；返回false如果集合已经包含了指定元素并且不支持重复元素。
     * 如果一个集合不是因为已经包含了指定元素而拒绝添加该元素，它必须抛异常而不是返回false。
     * @param e 保证该元素存在集合中。
     * @return true如果集合因此次调用而改变
     * @throws UnsupportedOperationException 如果集合不支持add操作
     * @throws ClassCastException 如果指定元素类型与集合类型不兼容
     * @throws NullPointerException 如果指定元素为null并且集合不容许null元素
     * @throws IllegalArgumentException 如果指定元素的某个属性不允许其被添加到集合中
     * @throws IllegalStateException 如果指定元素无法被添加到集合中因为插入限制
     */
    boolean add(E e);

    /**
     * 从集合中移除与指定元素equals()比较相等的单个元素，或者移除null元素。
     * @param o 希望从集合中移除的元素。
     * @return true如果指定元素从集合中移除。
     * @throws ClassCastException 如果指定元素类型与集合类型不兼容（可选）
     * @throws NullPointerException 如果指定元素为null并且集合不容许null元素（可选）
     * @throws UnsupportedOperationException 如果集合不支持remove操作
     */
    boolean remove(Object o);

    /**
     * 返回true如果该集合包含了指定集合中的所有元素。
     * @param c 指定集合
     * @return true如果该集合包含了指定集合中的所有元素。
     * @throws ClassCastException 如果指定集合中有元素类型与集合类型不兼容（可选）
     * @throws NullPointerException 如果指定集合中有元素为null并且集合不容许null元素（可选），或者指定集合为null
     */
    boolean containsAll(Collection<?> c);

    /**
     * 把指定集合中的所有元素添加到该集合（可选）。
     * 如果在进行添加操作过程中指定集合被修改了，结果是未定义的。
     * 也就是说一个集合自己addAll自己，结果是未定义的。
     * @param c 指定集合
     * @return true如果集合因此次调用而改变
     * @throws UnsupportedOperationException 如果集合不支持addAll操作
     * @throws ClassCastException 如果指定集合中有元素类型与集合类型不兼容
     * @throws NullPointerException 如果指定集合中有元素为null并且集合不容许null元素，或者指定集合为null
     * @throws IllegalArgumentException 如果指定集合中有元素的某个属性不允许其被添加到集合中
     * @throws IllegalStateException 如果指定集合中有元素无法被添加到集合中因为插入限制
     */
    boolean addAll(Collection<? extends E> c);

    /**
     * 移除集合中被包含在指定集合中的元素（可选）。
     * 操作结束后，该集合与指定集合没有任何公共元素。
     * @param c 指定集合
     * @return true如果集合因此次调用而改变
     * @throws UnsupportedOperationException 如果集合不支持removeAll操作
     * @throws ClassCastException 如果指定集合中有元素类型与集合类型不兼容（可选）
     * @throws NullPointerException 如果指定集合中有元素为null并且集合不容许null元素（可选），或者指定集合为null
     */
    boolean removeAll(Collection<?> c);

    /**
     * 仅仅保留集合中被包含在指定集合中的元素（可选）。
     * @param c 指定集合
     * @return true如果集合因此次调用而改变
     * @throws UnsupportedOperationException 如果集合不支持retainAll操作
     * @throws ClassCastException 如果指定集合中有元素类型与集合类型不兼容（可选）
     * @throws NullPointerException 如果指定集合中有元素为null并且集合不容许null元素（可选），或者指定集合为null
     */
    boolean retainAll(Collection<?> c);

    /**
     * 移除集合中的所有元素（可选）。
     * @throws UnsupportedOperationException 如果集合不支持clear操作
     */
    void clear();

    /**
     * 比较指定对象与该集合是否相等。
     */
    boolean equals(Object o);

    /**
     * 返回该集合的hash code值。
     * @return
     */
    int hashCode();
}
