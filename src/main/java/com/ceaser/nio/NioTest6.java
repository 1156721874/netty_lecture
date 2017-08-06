package com.ceaser.nio;

import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2017/8/6.
 * sliceByteBuffer和byteBuffer共享底层的数组
 */
public class NioTest6 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        for(int i=0;i<byteBuffer.capacity();i++){
            byteBuffer.put((byte)i);
        }

        byteBuffer.position(2);
        byteBuffer.limit(7);

        ByteBuffer sliceByteBuffer = byteBuffer.slice();

        for(int i=0;i<sliceByteBuffer.capacity();i++){
            byte b = sliceByteBuffer.get();
            b *=2;
            sliceByteBuffer.put(i,b);
        }

        byteBuffer.position(0);
        byteBuffer.limit(byteBuffer.capacity());

        while(byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.get());
        }

    }
}
