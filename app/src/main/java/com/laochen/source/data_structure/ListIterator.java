package com.laochen.source.data_structure;

/**
 * Date:2017/9/26 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 * 基于List的迭代器，允许从任意方向遍历List，允许在迭代期间修改List，能够获取迭代器在List中的当前位置。
 * ListIterator没有当前元素，但有当前位置，该位置用cursor表示。
 * 当前cursor的位置总是在previous()返回的元素和next()返回的元素之间，size为n的List中cursor的位置有n+1种可能，如下：
 * <PRE>
 *                      Element(0)   Element(1)   Element(2)   ... Element(n-1)
 * cursor positions:  ^            ^            ^            ^                  ^
 * </PRE>
 * 需要注意的是，remove()和set()方法不是基于cursor的位置工作的，它们基于next()或者previous()返回的最后一个元素工作。
 */

public interface ListIterator<E> extends java.util.Iterator<E> {
    // 查询操作

    /**
     * 返回true如果该ListIterator沿着向前方向遍历List时还有更多的元素。
     */
    boolean hasNext();

    /**
     * 返回cursor位置的下一个元素，同时cursor前进一步。
     * 该方法能够重复调用来遍历List，该方法也能和previous()混合调用在List上往返。
     * @return cursor位置的下一个元素
     * @throws java.util.NoSuchElementException 如果没有下一个元素
     */
    E next();

    /**
     * 返回true如果该ListIterator沿着向后方向遍历List时还有更多的元素。
     */
    boolean hasPrevious();

    /**
     * 返回cursor位置的上一个元素，同时cursor后退一步。
     * 该方法能够重复调用来遍历List，该方法也能和hasNext()混合调用在List上往返。
     * @return cursor位置的上一个元素
     * @throws java.util.NoSuchElementException 如果没有上一个元素
     */
    E previous();

    /**
     * 返回由后续调用next()返回的元素的index，如果已经迭代到了List的末尾，返回List的size.
     */
    int nextIndex();

    /**
     * 返回由后续调用previous()返回的元素的index，如果已经迭代到了List的开头，返回-1.
     */
    int previousIndex();

    // 修改操作

    /**
     * 从List中移除由next()/previous()返回的最后的元素（可选操作）。
     * 该方法只能在上次调用next()/previous()后add()未被调用的情况下调用only一次。
     * @throws UnsupportedOperationException 如果该ListIterator不支持remove操作。
     * @throws IllegalStateException 如果调用该方法之前next()/previous()都未被调用；
     * 或者next()/previous()被调用后remove()/add()已经被调用过了。
     */
    void remove();

    /**
     * 用指定元素替换next()/previous()返回的最后的元素（可选操作）。
     * 该方法只能在上次调用next()/previous()后remove()/add()未被调用的情况下调用。
     * @param e 指定替换元素
     * @throws UnsupportedOperationException 如果该ListIterator不支持set操作。
     * @throws ClassCastException 如果指定元素类型与List元素类型不兼容。
     * @throws IllegalArgumentException 如果指定元素的某些方面阻止其被添加到List。
     * @throws IllegalStateException 如果调用该方法之前next()/previous()都未被调用；
     * 或者next()/previous()被调用后remove()/add()已经被调用过了。
     */
    void set(E e);

    /**
     * 将指定元素插入到List中（可选操作）。插入位置是当前cursor的位置。
     * 举个例子，下面是add前cursor的位置：
     * <PRE>
     *                      Element(0)   Element(1)   Element(2)   ... Element(n-1)
     * cursor positions:  ^            ^            ^(cursor)    ^                  ^
     * </PRE>
     *
     * add后cursor的位置是：
     * * <PRE>
     *                      Element(0)   Element(1)   newElement   Element(2)   ... Element(n-1)
     * cursor positions:  ^            ^            ^            ^(cursor)    ^                  ^
     * </PRE>
     *
     * @param e 指定插入元素
     * @throws UnsupportedOperationException 如果该ListIterator不支持set操作。
     * @throws ClassCastException 如果指定元素类型与List元素类型不兼容。
     * @throws IllegalArgumentException 如果指定元素的某些方面阻止其被添加到List。
     */
    void add(E e);
}
