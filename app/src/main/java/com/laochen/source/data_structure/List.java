package com.laochen.source.data_structure;

/**
 * Date:2017/9/23 <p>
 * Author:chenzehao@danale.com <p>
 * Description:列表
 */

/**
 * 有序集合（线性表），能够精确控制元素插入的位置，能够通过整型下标访问元素。
 * 允许重复元素。
 * 提供了特殊的迭代器ListIterator，允许插入，替换，双向访问
 */
public interface List<E> extends Collection<E> {

    /**
     * 指定元素添加到集合末尾（可选，因实现而异）
     * <p>
     * {@inheritDoc}
     */
    boolean add(E e);

    /**
     * 指定集合中所有元素按照其迭代器返回元素的顺序添加到集合末尾（可选，因实现而异）
     * @see Collection#addAll(Collection)
     */
    boolean addAll(Collection<? extends E> c);

    /**
     * 从集合中移除第一次出现的指定元素（最小的索引值），（可选操作）
     * @see Collection#remove(Object)
     */
    boolean remove(Object o);

    /**
     * 返回true当且仅当指定对象也是List，有相同的size，对应位置的元素都equal。
     */
    boolean equals(Object o);

    /**
     * <pre>
     *  int hashCode = 1;
     *  for (E e : list)
     *      hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
     * </pre>
     */
    int hashCode();

    /**
     * 以上的方法都在父接口中声明过。
     * 为什么父接口中已经声明了该方法，子接口没有实现方法，却还要重新声明一次呢？
     * 因为父类接口中对该方法的声明是非常抽象的，宽泛的，如果子类接口对该方法的实现有更具体的要求，
     * 就需要在某个地方注明更具体的注释来指示实现者。
     *
     * 不可能在父接口的方法上添加注释，因此只能在子接口中重复声明一次。
     * 按照Java规范，重复声明对程序没有任何影响。
     */

    /**
     * 指定集合中所有元素按照其迭代器返回元素的顺序添加到集合指定位置（可选）
     * @throws IndexOutOfBoundsException 如果index越界（index < 0 || index > size()）
     */
    boolean addAll(int index, Collection<? extends E> c);

    /**
     * 返回集合中指定位置的元素。
     * @param index 指定位置
     * @return 集合中指定位置的元素。
     * @throws IndexOutOfBoundsException 如果index越界（index < 0 || index >= size()）
     */
    E get(int index);

    /**
     * 用指定元素替换集合中指定位置的元素。（可选）
     * @param index 指定位置
     * @param element 指定元素
     * @return 指定位置上替换之前的元素
     * @throws UnsupportedOperationException 如果该List不支持set操作
     * @throws ClassCastException 如果指定元素类型与该List类型不兼容
     * @throws NullPointerException 如果指定元素为null并且该List不容许null元素
     * @throws IllegalArgumentException 如果指定元素的某个属性不允许其被添加到该List中
     * @throws IndexOutOfBoundsException 如果index越界（index < 0 || index >= size()）
     */
    E set(int index, E element);

    /**
     * 将指定元素添加到List的指定位置（可选），指定位置及其右边的元素集体右移（索引增加）
     * @param index 指定位置
     * @param element 指定元素
     * @throws UnsupportedOperationException 如果该List不支持add操作
     * @throws ClassCastException 如果指定元素类型与该List类型不兼容
     * @throws NullPointerException 如果指定元素为null并且该List不容许null元素
     * @throws IllegalArgumentException 如果指定元素的某个属性不允许其被添加到该List中
     * @throws IndexOutOfBoundsException 如果index越界（index < 0 || index > size()）
     */
    void add(int index, E element);

    /**
     * 移除List中指定位置的元素（可选）
     * @param index 指定位置
     * @return 指定位置上之前的元素
     * @throws UnsupportedOperationException 如果该List不支持remove操作
     * @throws IndexOutOfBoundsException 如果index越界（index < 0 || index >= size()）
     */
    E remove(int index);

    /**
     * 返回集合中第一次出现指定元素的位置（最小索引），如果找不到指定元素，返回-1
     * @param o 待搜索的指定元素
     * @return 集合中第一次出现指定元素的位置（最小索引），如果找不到指定元素，返回-1
     * @throws ClassCastException 如果指定元素类型与该List类型不兼容（可选）
     * @throws NullPointerException 如果指定元素为null并且该List不容许null元素（可选）
     */
    int indexOf(Object o);

    /**
     * 返回集合中最后一次出现指定元素的位置（最高索引），如果找不到指定元素，返回-1
     * @param o 待搜索的指定元素
     * @return 集合中最后一次出现指定元素的位置（最高索引），如果找不到指定元素，返回-1
     * @throws ClassCastException 如果指定元素类型与该List类型不兼容（可选）
     * @throws NullPointerException 如果指定元素为null并且该List不容许null元素（可选）
     */
    int lastIndexOf(Object o);

    /**
     * 返回基于集合中所有元素的迭代器（按照适当顺序）
     */
    ListIterator<E> listIterator();

    /**
     * 返回基于集合中从指定索引开始所有元素的迭代器（按照适当顺序）
     * @throws IndexOutOfBoundsException 如果index越界（index < 0 || index > size()）
     */
    ListIterator<E> listIterator(int index);

    /**
     * 返回List的部分视图，从fromIndex（包含）到toIndex（不包含）。
     * 如果fromIndex == toIndex，返回empty List。
     * 例如下面的用法可以移除List某个范围内的所有元素：
     * <pre>
     *      list.subList(from, to).clear();
     * </pre>
     * @throws IndexOutOfBoundsException 如果(fromIndex < 0 || toIndex > size || fromIndex > toIndex)
     */
    List<E> subList(int fromIndex, int toIndex);

}
