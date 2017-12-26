package com.ceaser.netty.userdefineprotocal;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyPersonEncoder extends MessageToByteEncoder<PersonProtocal> {

    @Override
    protected void encode(ChannelHandlerContext ctx, PersonProtocal msg, ByteBuf out) throws Exception {
        System.out.println("MyPersonEncoder encode invoked");
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
