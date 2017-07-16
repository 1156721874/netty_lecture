package com.ceaser.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2017/7/16.
 */
public class NioTest3 {
    public static void main(String[] args) throws Exception {
        FileOutputStream outputStream = new FileOutputStream("niofiles/NioTest3.txt");
        FileChannel fileChannel = outputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        byte[] message = "hello world , nihao".getBytes();
        for (int i = 0; i <message.length ; i++) {
            byteBuffer.put(message[i]);
        }
        byteBuffer.flip();

        fileChannel.write(byteBuffer);

        outputStream.close();
    }
}
