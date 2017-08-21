package com.laochen.source.java.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * Date:2017/8/18 <p>
 * Author:chenzehao@danale.com <p>
 * Description:Channel与Channel之间传递数据
 */

public class ChannelTransfer {
    public static void main(String[] args) {

    }

    private static void transferFrom() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("E:\\AndroidStudioProjects\\Core-Note\\app\\src\\main\\java\\com\\laochen\\source\\java\\nio\\fromFile.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("E:\\AndroidStudioProjects\\Core-Note\\app\\src\\main\\java\\com\\laochen\\source\\java\\nio\\toFile.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

        toChannel.transferFrom(fromChannel, position, count);
    }

    private static void transferTo() {

    }
}
