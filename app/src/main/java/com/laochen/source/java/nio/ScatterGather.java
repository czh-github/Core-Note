package com.laochen.source.java.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Date:2017/8/17 <p>
 * Author:chenzehao@danale.com <p>
 * Description:分散读/聚集写
 */

public class ScatterGather {
    public static void main(String[] args) {
//        scatterReads();
        gatherWrites();
    }

    /**
     * Scattering Reads:从一个channel读取数据到多个buffer。
     */
    private static void scatterReads() {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile("E:\\AndroidStudioProjects\\Core-Note\\app\\src\\main\\java\\com\\laochen\\source\\java\\nio\\test.txt", "rw");
            FileChannel channel = file.getChannel();
            ByteBuffer header = ByteBuffer.allocate(10);
            ByteBuffer body = ByteBuffer.allocate(10);
            ByteBuffer[] bufferArray = {header, body};
            channel.read(bufferArray); // 先把第一个ByteBuffer填满，再填第二个

            for (ByteBuffer bb : bufferArray) {
                bb.flip();
                while (bb.hasRemaining()) {
                    System.out.print((char) bb.get());
                }
                System.out.println();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            // 关闭文件
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    /**
     * Gathering Writes:从多个buffer写入数据到单个channel。
     */
    private static void gatherWrites() {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile("E:\\AndroidStudioProjects\\Core-Note\\app\\src\\main\\java\\com\\laochen\\source\\java\\nio\\test.txt", "rw");
            FileChannel channel = file.getChannel();
            ByteBuffer header = ByteBuffer.allocate(10);
            ByteBuffer body = ByteBuffer.allocate(10);
            header.put((byte) 1);
            body.put(new byte[] {1,2,3,4,5});
            header.flip();
            body.flip();

            ByteBuffer[] bufferArray = {header, body}; // 先把第一个buffer的position到limit之间的数据写入channel，然后第二个
            channel.write(bufferArray);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            // 关闭文件
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
