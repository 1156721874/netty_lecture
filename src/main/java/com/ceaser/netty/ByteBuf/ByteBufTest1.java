package com.ceaser.netty.ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class ByteBufTest1 {
    public static void main(String[] args) {

        /**
         * ByteBuf有2种维度，一种是堆内还是堆外
         * 另一种是池化还是非池化
         */
        //utf-8字符编码，一个汉字占3个字节
        ByteBuf byteBuf = Unpooled.copiedBuffer("张hello world", Charset.forName("utf-8"));

        //如果是在堆上的返回true
        if(byteBuf.hasArray()){
            //ByteBuf内部的堆数组
            byte[] cotent =  byteBuf.array();
            System.out.println(new String(cotent,Charset.forName("utf-8")));

            //ByteBuf实际实现类的类型
            System.out.println(byteBuf);
            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());

            int length  = byteBuf.readableBytes();
            for (int i=0;i<length;i++){
                System.out.println((char)byteBuf.getByte(i));
            }
            System.out.println(byteBuf.getCharSequence(0,4,Charset.forName("utf-8")));
            //输出"张h"
        }
    }
}
