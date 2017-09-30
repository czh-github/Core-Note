package com.laochen.source.data_structure.sort_algorithm;

import java.util.Arrays;

/**
 * Date:2017/9/29 <p>
 * Author:chenzehao@danale.com <p>
 * Description:插入排序
 *
 * Input:   B   C   D   A   E   H   G   F
 * cur C    B   C   D   A   E   H   G   F      cur >= B 不动
 * cur D    B   C   D   A   E   H   G   F      cur >= C 不动
 * cur A    B   C       D   E   H   G   F      cur < D，把D挪到cur的位置上，A不用挪到D的位置
 * cur A    B       C   D   E   H   G   F      cur < C，把C往后挪一位，同样A不用挪到C的位置
 * cur A        B   C   D   E   H   G   F      cur < B，把B往后挪一位，同样A不用挪到B的位置
 * cur A    A   B   C   D   E   H   G   F      cur的位置最终定下来，把A挪到相应位置上
 * cur E
 * ...
 *
 * 排序过程：
 * 1.选cur，cur之前的元素是排好序的
 * 2.cur与排在它之前的元素一一比较，比cur大的元素往后挪一位，否则cur应该插入的位置就确定了
 * 3.把cur插入相应的位置，如此，cur及其前面的元素都是有序的
 * 4.选cur后面的元素重复1-3
 *
 * 特点：
 * 1.是稳定排序
 * 2.需要额外O(1)的存储空间存储cur
 */

public class InsertionSortTest {
    public static void insertionSort(char[] data) {
        int n = data.length;
        for (int i = 1; i < n; i++) {
            char cur = data[i];
            int j = i;
            for (; j > 0; j--) {
                if (cur < data[j-1]) {
                    data[j] = data[j-1];
                } else {
                    break;
                }
            }
            data[j] = cur;
        }
    }

    public static void main(String[] args) {
        char[] data = {'B', 'C', 'D', 'A', 'E', 'H', 'G', 'F'};
        System.out.println("排序前：" + Arrays.toString(data));
        insertionSort(data);
        System.out.println("排序后：" + Arrays.toString(data));
    }
}
