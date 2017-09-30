package com.laochen.source.data_structure;

import java.util.HashSet;
import java.util.Set;

/**
 * Date:2017/9/22 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class Test {
    public static void main(String[] args) {
//        ArrayList<String> list = new ArrayList<>();
//        list.add("123");
//        list.add(1, "456");
//        LinkedList<String> list2 = new LinkedList<>();
//        list2.add("123");
//        int i = 7;
//        System.out.println(i>>1);

//        System.out.println(null instanceof String); // false
        Set<String> set = new HashSet<>();
        set.add(null);
        set.add(null);
        for (String s: set) {
            System.out.println(s);
        }
    }
}
