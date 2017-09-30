package com.laochen.source.data_structure.stack;

/**
 * Date:2017/9/27 <p>
 * Author:chenzehao@danale.com <p>
 * Description:基于数组的Stack的简单实现
 * 有个巨大缺陷是，容量是固定的，不可自增长。
 */

public class ArrayStack<E> implements Stack<E> {
    /**
     * 默认数组容量
     */
    public static final int CAPACITY = 1000;

    /**
     * 盛放元素的数组
     */
    private E[] data;

    /**
     * 栈顶元素在数组{@link #data}中的索引下标
     * 栈底为0，如果栈是empty，该值为-1
     */
    private int t = -1;

    public ArrayStack() {
        this(CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayStack(int capacity) {
        data = (E[]) new Object[capacity];
    }

    @Override
    public void push(E e) throws IllegalStateException {
        if (size() == data.length) {
            throw new IllegalStateException("Stack is full");
        }
        data[++t] = e;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            return null;
        }
        E removed = data[t];
        data[t--] = null;
        return removed;
    }

    @Override
    public E top() {
        if (isEmpty()) {
            return null;
        }
        return data[t];
    }

    @Override
    public int size() {
        return t + 1;
    }

    @Override
    public boolean isEmpty() {
        return t == -1;
    }
}
