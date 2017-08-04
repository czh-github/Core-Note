package com.laochen.jni.java.collection.queue.thread;

/**
 * Date:2017/7/18 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class Main {

    public static void main(String[] args) {
//        MyThread t = new MyThread();
//        t.start();
//
//        Thread.sleep(1000);
//
//        t.interrupt();

//        int[] src = new int[] {1,2,3,4,5,6,7,8,9,10};
//        int[] dest = new int[5];
//        arrayCopy(src, 5, dest, 0, 10);
//        System.out.println(Arrays.toString(dest));

        Main m = new Main();
        for (int i = 0; i < 100000; i++) {
            byte[] data = new byte[i];
            m.function(data, 0);
        }
    }

    public static void arrayCopy(int[] src, int srcPos, int[] dest, int destPos, int length) {
        if (src == null || src.length == 0 || dest == null || dest.length == 0) {
            return;
        }
        if (srcPos < 0 || destPos < 0 || length < 0) {
            return;
        }
        if (srcPos >= src.length || destPos >= dest.length) {
            return;
        }

        int actualLength = length;
        if (srcPos + length > src.length) {
            actualLength = src.length - srcPos;
        }

        if (destPos + actualLength > dest.length) {
            actualLength = dest.length - destPos;
        }
        System.arraycopy(src, srcPos, dest, destPos, actualLength);

    }

    byte[] apmData = new byte[160];
    byte[] tempData = new byte[160];
    int tempOffset = 0; // tempData内的偏移量
    int dataOffset = 0; // data内的偏移量

    private void function(byte[] data, int offset) {
        int num = (data.length - offset) / 160;
        int left = (data.length - offset) % 160;
        if (left == 0) {
            if (tempOffset > 0) { // 有零头，需要拿新数组的部分来拼凑
                int leftLength = 160 - tempOffset;
                if (leftLength > data.length) {
                    leftLength = data.length;
                }
                System.arraycopy(data, 0, tempData, tempOffset, leftLength);
                tempOffset = (tempOffset + leftLength) % 160;
                function(data, leftLength);
            } else {
                for (int i = 0; i < num; i++) {
                    System.arraycopy(data, offset + i * 160, apmData, 0, 160);
                }
            }
        } else {
            if (tempOffset > 0) { // 有零头，需要拿新数组的部分来拼凑
                int leftLength = 160 - tempOffset; // 59
                if (leftLength > data.length) {
                    leftLength = data.length;
                }
//                System.out.println("src=:" + data.length + ",srcPos:" + 0 + ",dest:" + tempData.length + ",destPos:" + tempOffset + ",length:" + leftLength);
                System.arraycopy(data, 0, tempData, tempOffset, leftLength);
                tempOffset = (tempOffset + leftLength) % 160;
                function(data, leftLength);
            } else {
                for (int i = 0; i < num; i++) {
                    System.arraycopy(data, offset + i * 160, apmData, 0, 160);
                }
                System.arraycopy(data, offset + num * 160, tempData, tempOffset, left);
                tempOffset = left;
            }
        }
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                System.out.println(1);
            }
            System.out.println(0);
        }
    }
}
