package com.ceaser.nio;

import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2017/8/6.
 * 我们随时可以将一个ByteBuffer调用asReadOnlyBuffer转换为一个只读buffer，但是不能将一个只读buffer转为读写buffer。
 */
public class NioTest7 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte)i);
        }

        ByteBuffer readonlyByteBuffer = byteBuffer.asReadOnlyBuffer();

        System.out.println(readonlyByteBuffer.getClass());

        readonlyByteBuffer.position(1);
        readonlyByteBuffer.put((byte)4);

    }
}
