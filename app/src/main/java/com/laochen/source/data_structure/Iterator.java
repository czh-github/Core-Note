package com.laochen.source.data_structure;

/**
 * Date:2017/9/26 <p>
 * Author:chenzehao@danale.com <p>
 * Description: 基于集合的迭代器接口
 */

public interface Iterator<E> {
    /**
     * 如果迭代器中有更多元素返回true。
     */
    boolean hasNext();

    /**
     * 返回迭代器中的下一个元素
     * @throws java.util.NoSuchElementException 如果迭代器中没有更多元素
     */
    E next();

    /**
     * 从当前集合中移除迭代器最后返回的元素（可选），该方法只能在next()方法后被调用一次。
     * 如果迭代器在迭代过程中集合被remove()之外的其它方式修改了，迭代器的行为是不明确的。
     * @throws UnsupportedOperationException 如果迭代器不支持remove操作
     * @throws IllegalStateException 如果调用remove()之前没有调用next()方法，或者调用next()之后已经调用过一次remove()
     */
    void remove();
}
