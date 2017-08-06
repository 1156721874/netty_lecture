package com.ceaser.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2017/7/27.
 */
public class NioTest4 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream  = new FileInputStream("niofiles/input.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("niofiles/output.txt");

        FileChannel fileInputStreamChannel = fileInputStream.getChannel();

        FileChannel fileOutputStreamChannel =  fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        while(true){
            byteBuffer.clear();//注释掉会发生什么情况
            int read = fileInputStreamChannel.read(byteBuffer);
            System.out.println("read : "+read);
            if(-1 == read){
               break;
            }
            byteBuffer.flip();
            fileOutputStreamChannel.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
