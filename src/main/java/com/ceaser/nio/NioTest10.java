package com.ceaser.nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * Created by Administrator on 2017/8/6.
 */
public class NioTest10 {
    public static void main(String[] args)  throws  Exception  {
        RandomAccessFile randomAccessFile = new RandomAccessFile("niofiles/NioTest10.txt","rw");
        FileChannel fileChannel =  randomAccessFile.getChannel();
        FileLock fileLock =  fileChannel.lock(3,6,true);

        System.out.println("valid : "+fileLock.isValid());
        System.out.println("lock type : "+fileLock.isShared());
        fileLock.release();
        randomAccessFile.close();
    }
}
