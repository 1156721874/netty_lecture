package com.ceaser.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2017/8/6.
 */
public class NioTest9 {
    public static void main(String[] args) throws  Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("niofiles/accessfile.txt","rw");
        FileChannel fileChannel =  randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer =  fileChannel.map(FileChannel.MapMode.READ_WRITE,0,5);
        mappedByteBuffer.put(0,(byte)'a');
        mappedByteBuffer.put(3,(byte)'b');
        randomAccessFile.close();

    }
}
