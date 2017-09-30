package com.laochen.source.android.activity_detect.step_counter;

public class SensorFilter {

    private SensorFilter() {
    }

    /**
     * 数组求和
     * @param array [1,2,3]
     * @return      1+2+3 = 6
     */
    public static float sum(float[] array) {
        float retval = 0;
        for (int i = 0; i < array.length; i++) {
            retval += array[i];
        }
        return retval;
    }

    /**
     * 叉乘
     * @param arrayA [1,2,3]       [1,2,3]
     * @param arrayB [1,2,3]       [4,5,6]
     * @return       [0,0,0]       [-3,6,-3]
     */
    public static float[] cross(float[] arrayA, float[] arrayB) {
        float[] retArray = new float[3];
        retArray[0] = arrayA[1] * arrayB[2] - arrayA[2] * arrayB[1];
        retArray[1] = arrayA[2] * arrayB[0] - arrayA[0] * arrayB[2];
        retArray[2] = arrayA[0] * arrayB[1] - arrayA[1] * arrayB[0];
        return retArray;
    }

    /**
     * 求模
     * @param array [1,2,3]
     * @return 1*1+2*2+3*3开方 = 3.7
     */
    public static float norm(float[] array) {
        float retval = 0;
        for (int i = 0; i < array.length; i++) {
            retval += array[i] * array[i];
        }
        return (float) Math.sqrt(retval);
    }


    /**
     * 点乘
     * @param a [1,2,3]
     * @param b [4,5,6]
     * @return  1*4+2*5+3*6 = 32
     */
    public static float dot(float[] a, float[] b) {
        float retval = a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
        return retval;
    }

    /**
     *
     * @param a [1,2,3]
     * @return  取模3.7，分别除以模[1/3.7, 2/3.7, 3/3.7]
     */
    public static float[] normalize(float[] a) {
        float[] retval = new float[a.length];
        float norm = norm(a);
        for (int i = 0; i < a.length; i++) {
            retval[i] = a[i] / norm;
        }
        return retval;
    }

}