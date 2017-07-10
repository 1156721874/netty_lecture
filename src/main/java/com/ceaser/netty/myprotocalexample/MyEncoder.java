package com.ceaser.netty.myprotocalexample;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 *  MyMessage Encoder
 */
public class MyEncoder extends MessageToByteEncoder<MyMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MyMessage myMessage, ByteBuf out) throws Exception {
        MyHeader myHeader =  myMessage.getMyHeader();

        out.writeByte(myHeader.getVersion());
        out.writeInt(myHeader.getContentLength());
        out.writeBytes(myHeader.getSessionId());


        out.writeBytes(myMessage.getContent());

    }
}
