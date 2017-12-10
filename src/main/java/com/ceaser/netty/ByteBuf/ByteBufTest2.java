package com.ceaser.netty.ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Iterator;

public class ByteBufTest2 {
    public static void main(String[] args) {
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
        ByteBuf heapBuf = Unpooled.buffer(10);
        ByteBuf directBuf = Unpooled.directBuffer(8);
        compositeByteBuf.addComponents(heapBuf,directBuf);
       // compositeByteBuf.removeComponent(0);
        Iterator iterator = compositeByteBuf.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
        compositeByteBuf.forEach(System.out::println);
    }
}
