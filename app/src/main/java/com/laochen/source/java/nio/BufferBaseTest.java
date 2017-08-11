package com.laochen.source.java.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Date:2017/8/10 <p>
 * Author:chenzehao@danale.com <p>
 * Description:Buffer的基本用法
 */

public class BufferBaseTest {
    public static void main(String[] args) {
        f();
    }

    public static void f() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        byte i = 0;
        while (buffer.hasRemaining()) {
            buffer.put(i++);
        }
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
    }

    public static void base() {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile("E:\\AndroidStudioProjects\\Core-Note\\app\\src\\main\\java\\com\\laochen\\source\\java\\nio\\test.txt", "rw");
            FileChannel channel = file.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 读数据：从channel到buffer
            int bytesRead = channel.read(buffer);
            while (bytesRead != -1) {
                // 把buffer的写模式翻转为读模式
                buffer.flip();
                while (buffer.hasRemaining()) {
                    // 一次读一个byte并打印
                    System.out.print((char) buffer.get());
                }
                // 可以向buffer写入数据
                buffer.clear();
                bytesRead = channel.read(buffer);
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
}
