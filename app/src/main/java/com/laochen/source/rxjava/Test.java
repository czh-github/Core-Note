package com.laochen.source.rxjava;

import java.util.Arrays;

/**
 * Date:2017/8/21 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class Test {
    public static void main(String[] args) {
        int start = 0;
        int end = 10;
        for (int i = start; i <= end; i++) {
            System.out.println(Arrays.toString(getRange(start, end, i, 3)));
        }
    }

    private static int[] getRange(int start, int end, int target, int maxCount) {
        if (target < start) {
            throw new IllegalArgumentException("target must >= start.(target=" + target + ", start=" + start);
        }
        if (target > end) {
            throw new IllegalArgumentException("target must <= end.(target=" + target + ", end=" + end);
        }
        if (maxCount < 1 || maxCount > end - start + 1) {
            throw new IllegalArgumentException("maxCount illegal.(maxCount=" + maxCount);
        }

        int startRange, endRange;
        int offset = maxCount / 2;
        if (target - offset < start) {
            startRange = start;
            endRange = start + maxCount - 1;
        } else if (target + offset > end) {
            endRange = end;
            startRange = end - maxCount + 1;
        } else {
            startRange = target - offset;
            endRange = startRange + maxCount - 1;
        }
        return new int[] {startRange, endRange};
    }
}
