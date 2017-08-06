package com.ceaser.nio;

import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2017/8/6.
 * 类型化的put和get方法 用于协议的传输
 */
public class NioTest5 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        byteBuffer.putShort((short)3);
        byteBuffer.putInt(5);
        byteBuffer.putChar('中');
        byteBuffer.putFloat(2.4f);
        byteBuffer.putDouble(68.5);
        byteBuffer.putLong(59);

        byteBuffer.flip();

        System.out.println(byteBuffer.getShort());
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getFloat());
        System.out.println(byteBuffer.getDouble());
        System.out.println(byteBuffer.getLong());


    }
}
