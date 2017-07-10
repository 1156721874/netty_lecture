package com.ceaser.netty.myprotocalexample;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Decoder
 */

public class MyDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {
        byte version  = in.readByte();
        int  contentLength = in.readInt();
        byte[] sessionByte = new byte[36];
        in.readBytes(sessionByte);

        MyHeader myHeader = new MyHeader(version,contentLength,sessionByte);
        byte[] content = in.readBytes(in.readableBytes()).array();

        MyMessage myMessage = new MyMessage(myHeader,content);
        list.add(myMessage);
    }
}
