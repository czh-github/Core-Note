package com.laochen.source.data_structure.linked_list;

/**
 * Date:2017/9/30 <p>
 * Author:chenzehao@danale.com <p>
 * Description:单（向）链表。特点是要操作某个节点，只能从头节点开始遍历。
 */

public class SinglyLinkedList<E> {
    public static class Node<E> {
        private E element;
        private Node<E> next;
        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }
        public E getElement() {
            return element;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> n) {
            next = n;
        }
    }

    private Node<E> head;
    private Node<E> tail;
    private int size = 0;

    public SinglyLinkedList() {

    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E first() {
        if (isEmpty()) {
            return null;
        } else {
            return head.getElement();
        }
    }

    public E last() {
        if (isEmpty()) {
            return null;
        } else {
            return tail.getElement();
        }
    }

    public void addFirst(E e) {
        head = new Node<>(e, head);
        if (isEmpty()) {
            tail = head;
        }
        size++;
    }

    public void addLast(E e) {
        Node<E> newTail = new Node<>(e, null);
        if (isEmpty()) {
            head = newTail;
        } else {
            tail.setNext(newTail);
        }
        tail = newTail;
        size++;
    }

    public E removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            E removed = head.getElement();
            head = head.getNext();
            size--;
            if (isEmpty()) {
                tail = null;
            }
            return removed;
        }
    }

}
