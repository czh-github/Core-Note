package com.laochen.source.data_structure.stack;

/**
 * Date:2017/9/27 <p>
 * Author:chenzehao@danale.com <p>
 * Description:栈：LIFO（后进先出）的一种数据结构。
 * 举例，浏览器通过点击返回按钮可以返回到上次打开的页面，编辑器提供的undo/redo（撤销重做）操作。
 * 不同于{@link java.util.Stack}
 */

public interface Stack<E> {
    /**
     * 入栈：添加元素到栈顶
     * @param e 添加到栈顶的元素
     */
    void push(E e);

    /**
     * 出栈：移除并返回栈顶元素，如果栈为empty返回null
     * @return 栈顶元素，如果栈为empty返回null
     */
    E pop();

    /**
     * 返回但不移除栈顶元素，如果栈为empty返回null
     * @return 栈顶元素，如果栈为empty返回null
     */
    E top();

    /**
     * 返回栈里的元素数量
     * @return 返回栈里的元素数量
     */
    int size();

    /**
     * 如果栈里没有元素返回true，否则返回false
     * @return 如果栈里没有元素返回true，否则返回false
     */
    boolean isEmpty();
}
