package com.laochen.source.data_structure;

import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Date:2017/9/23 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class ArrayList<E> implements List<E>, Cloneable, java.io.Serializable, RandomAccess {
    private static final long serialVersionUID = 1L;

    /**
     * ArrayList的所有元素就是存在这个数组里，
     * ArrayList的容量就是这个数组的长度。
     *
     * 为什么声明为transient？
     * 表示ArrayList在序列化的时候，默认不会序列化这些数组元素，因为ArrayList实际上是动态数组，
     * 每次在放满以后自动增长设定的长度值，如果数组自动增长长度设为100，而实际只放了一个元素，
     * 那就会序列化很多null元素，所以ArrayList把元素数组设置为transient。
     * 在{@link #writeObject(ObjectOutputStream)}中定制序列化。
     */
    private transient Object[] elementData;

    /**
     * ArrayList包含的元素数量
     */
    private int size;

    /**
     * 构造函数
     * @param initialCapacity 指定ArrayList的初始容量
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: "+ initialCapacity);
        }
        this.elementData = new Object[initialCapacity];
    }

    /**
     * 无参构造函数，设置初始容量为10
     */
    public ArrayList() {
        this(10);
    }

    /**
     * 用指定集合初构造ArrayList，按照指定集合的迭代器返回元素的顺序。
     */
    public ArrayList(Collection<? extends E> c) {
        if (c == null) {
            throw new NullPointerException("Initial collection is null");
        } else {
            this.elementData = c.toArray();
            this.size = c.size();
        }
    }

    /**
     * 缩小ArrayList的capacity为它的size，最小化ArrayList的存储空间
     */
    public void trimToSize() {
        modCount++;
        int oldCapacity = elementData.length;
        if (size < oldCapacity) {
            elementData = Arrays.copyOf(elementData, size);
        }
    }

    /**
     * 增长ArrayList的容量，使其至少能容纳minCapacity数量的元素
     * @param minCapacity 期望的最小容量
     */
    public void ensureCapacity(int minCapacity) {
        if (minCapacity > 0)
            ensureCapacityInternal(minCapacity);
    }

    private void ensureCapacityInternal(int minCapacity) {
        modCount++;
        if (minCapacity > elementData.length) {
            grow(minCapacity);
        }
    }

    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        // 首先在旧容量基础上扩大0.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
        }
        if (newCapacity > MAX_ARRAY_SIZE) {
            newCapacity = hugeCapacity(minCapacity);
        }
        // 绝大部分情况下，minCapacity跟size比较接近，copy值得
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    /**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // 表明超过Integer.MAX_VALUE，已经溢出
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);
        elementData[size++] = e;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) {
                    fastRemove(i);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elementData[i])) {
                    fastRemove(i);
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Private remove method that skips bounds checking and does not
     * return the value removed.
     */
    private void fastRemove(int index) {
        modCount++;
        int num = size - index - 1;
        if (num > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, num);
        }
        elementData[--size] = null; // Let gc do its work
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);

        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);
        int numMoved = size - index;
        if (numMoved > 0) {
            System.arraycopy(elementData, index, elementData, index + numNew, numMoved);
        }
        System.arraycopy(a, 0, elementData, index, numNew);
        size += numNew;
        return numNew != 0;
    }

    /**
     * 从ArrayList中移除[fromIndex,toIndex)区间内的所有元素。
     * @param fromIndex 开始下标（包含）
     * @param toIndex   结束下标（排除）
     * @throws IndexOutOfBoundsException 如果
     * ({@code fromIndex < 0 || fromIndex >= size() || toIndex > size() || toIndex < fromIndex})
     */
    public void removeRange(int fromIndex, int toIndex) {
        modCount++;
        int numMoved = size - toIndex;
        System.arraycopy(elementData, toIndex, elementData, fromIndex,
                numMoved);

        // Let gc do its work
        int newSize = size - (toIndex-fromIndex);
        while (size != newSize)
            elementData[--size] = null;
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return elementData(index);
    }

    @SuppressWarnings("unchecked")
    private E elementData(int index) {
        return (E) elementData[index];
    }

    /**
     * 之所以不用检查index小于0的情况，因为array[-1]本身会抛IndexOutOfBoundsException
     */
    private void rangeCheck(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            // 重新分配一个数组，填满数据后返回
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        }
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        E old = elementData(index);
        elementData[index] = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        ensureCapacityInternal(size + 1);
        int numMoved = size - index;
        if (numMoved > 0) {
            System.arraycopy(elementData, index, elementData, index + 1, numMoved);
        }
        elementData[index] = element;
        size++;
    }

    /**
     * A version of rangeCheck used by add and addAll.
     */
    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        modCount++;
        E removed = elementData(index);
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null; // Let gc do its work
        return removed;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return batchRemove(c, false);
    }

    /**
     * 遍历一次ArrayList，批量移除指定集合包含的元素。
     * @cool 该方法设计很巧妙！
     * @param c 指定集合
     * @param complement true表示ArrayList只保留指定集合包含的元素，false表示ArrayList只保留指定集合不包含的元素
     * @return true如果ArrayList因此次调用而改变
     */
    private boolean batchRemove(Collection<?> c, boolean complement) {
        final Object[] data = this.elementData;
        int w = 0, r = 0;
        boolean modified = false;
        try {
            for (; r < size; r++) {
                if (c.contains(data[r]) == complement) {
                    data[w++] = data[r];
                }
            }
        } finally {
            // 如果循环里的contains()抛出异常，也就是说操作只进行了一部分，把从r开始往后的所有元素拷贝到data从w开始的位置
            if (r != size) {
                System.arraycopy(data, r, data, w, size - r);
                w += size - r;
            }

            // 需要保留的元素都移到了data的前面，从w开始往后的元素都是无效的
            if (w != size) {
                for (int i = w; i < size; i++) {
                    data[i] = null;
                }
                modCount += size - w;
                size = w;
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return batchRemove(c, true);
    }


    @Override
    public void clear() {
        modCount++;
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (E e : this)
            hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        // ArrayList可能跟List的其它实现equals相等
        if (!(o instanceof List))
            return false;

        ListIterator<E> e1 = listIterator();
        ListIterator e2 = ((List) o).listIterator();
        while (e1.hasNext() && e2.hasNext()) {
            E o1 = e1.next();
            Object o2 = e2.next();
            if (!(o1==null ? o2==null : o1.equals(o2)))
                return false;
        }
        return !(e1.hasNext() || e2.hasNext());
    }

    /**
     * Returns a shallow copy of this <tt>ArrayList</tt> instance.  (The
     * elements themselves are not copied.)
     *
     * @return 当前ArrayList对象的浅拷贝
     */
    @Override
    public Object clone() {
        try {
            @SuppressWarnings("unchecked")
            ArrayList<E> v = (ArrayList<E>) super.clone();
            v.elementData = Arrays.copyOf(elementData, size);
//            v.size = 0;
            v.modCount = 0;
            return v;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError();
        }
    }

    /**
     * 定制序列化过程
     *
     * @serialData The length of the array backing the <tt>ArrayList</tt>
     *             instance is emitted (int), followed by all of its elements
     *             (each an <tt>Object</tt>) in the proper order.
     */
    private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException{
        int expectedModCount = modCount;
        // Write the non-static and non-transient fields of the current class to this stream.
        // 例如，size
        s.defaultWriteObject();

        // Write out array length
        s.writeInt(elementData.length);

        // Write out all elements in the proper order.
        for (int i=0; i<size; i++)
            s.writeObject(elementData[i]);

        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    /**
     * 反序列化
     */
    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        // Read in size, and any hidden stuff
        s.defaultReadObject();

        // Read in array length and allocate array
        int arrayLength = s.readInt();
        Object[] a = elementData = new Object[arrayLength];

        // Read in all elements in the proper order.
        for (int i=0; i<size; i++)
            a[i] = s.readObject();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        // TODO 暂未实现
        return null;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        return new ListItr(index);
    }

    @Override
    public java.util.Iterator<E> iterator() {
        return new Itr();
    }

    /**
     * 记录该List被结构化修改的次数。
     * 所谓结构化修改，是指改变List的size的操作。
     * 这个字段用于Iterator或者ListIterator的实现，如果在迭代器迭代过程中，该字段被意外修改，
     * 那么迭代器的相应方法就会抛出ConcurrentModificationException，这种机制称为fast-fail，
     * 这种机制的目的是防止将风险和不确定性扩散到以后。
     */
    private transient int modCount = 0;

    private class Itr implements java.util.Iterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        public boolean hasNext() {
            return cursor < size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            checkForComodification();
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = ArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                ArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor > 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public E previous() {
            checkForComodification();
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = ArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                ArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            checkForComodification();

            try {
                int i = cursor;
                ArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

}
