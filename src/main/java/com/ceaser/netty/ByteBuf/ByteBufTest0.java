package com.ceaser.netty.ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBufTest0 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.buffer(10);
        for(int i=0;i<byteBuf.capacity();i++){
            byteBuf.writeByte(i);
        }
//绝对方式  不会改变readerIndex
        for(int i=0;i<byteBuf.capacity();i++){
            System.out.println(byteBuf.getByte(i));
        }
//相对方式 会改变writerIndex
        for(int i=0;i<byteBuf.capacity();i++){
            System.out.println(byteBuf.readByte());
        }
    }
}
 /*      +-------------------+------------------+------------------+
         *      | discardable bytes |  readable bytes  |  writable bytes  |
         *      |                   |     (CONTENT)    |                  |
         *      +-------------------+------------------+------------------+
         *      |                   |                  |                  |
         *      0      <=      readerIndex   <=   writerIndex    <=    capacity
*/