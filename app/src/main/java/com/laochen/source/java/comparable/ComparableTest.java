package com.laochen.source.java.comparable;

import java.util.ArrayList;
import java.util.List;

import static com.laochen.source.java.innerclass.NestedInterface1.i;

/**
 * Date:2017/8/14 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class ComparableTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(4);
        list.add(6);
        list.add(7);
        list.add(10);

//        int i = binarySearch0(list, 0, list.size(), 11);
        System.out.println(i);
    }

    private static <E extends Comparable<E>> int binarySearch0(List<E> a, int fromIndex, int toIndex, E key) {
        int low = fromIndex;
        int high = toIndex - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            Comparable<E> midVal = a.get(mid);
            int cmp = midVal.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found.
    }
}
