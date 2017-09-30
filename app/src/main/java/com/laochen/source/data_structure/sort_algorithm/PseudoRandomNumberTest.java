package com.laochen.source.data_structure.sort_algorithm;

import java.util.Random;

/**
 * Date:2017/9/29 <p>
 * Author:chenzehao@danale.com <p>
 * Description:伪随机数
 * 之所以说是伪随机数，因为如果每次设置的seed相同，会导致每次生成的第一个随机数相同，
 * 由于伪随机算法当前随机数的生成依赖之前的随机数，加上算法是固定的，导致后续生成的A随机数序列
 * 和B随机数序列是完全相同的。
 *
 * 为了解决这种尴尬，可以把每次的seed设为不同，例如设为System.currentTimeMills.
 */

public class PseudoRandomNumberTest {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Random rand = new Random();
            rand.setSeed(1L);
            for (int j = 0; j < 3; j++) {
                System.out.println(rand.nextInt(100));
            }
        }
    }
}
