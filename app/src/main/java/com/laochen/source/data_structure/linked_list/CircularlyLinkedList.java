package com.laochen.source.data_structure.linked_list;
import static com.laochen.source.data_structure.linked_list.SinglyLinkedList.Node;

/**
 * Date:2017/9/30 <p>
 * Author:chenzehao@danale.com <p>
 * Description:循环链表。与单向链表的区别在于，last.next 指向 first。
 * 因此成员变量只需要tail
 */

public class CircularlyLinkedList<E> {
    private Node<E> tail;
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E first() {
        if (size == 0) {
            return null;
        } else if(size == 1) {
            return tail.getElement();
        } else {
            return tail.getNext().getElement();
        }
    }

    public E last() {
        if (size == 0) {
            return null;
        } else {
            return tail.getElement();
        }
    }

    public void addFirst(E e) {

    }

    public void addLast(E e) {

    }

    public E removeFirst() {
        return null;
    }
}
