package com.ceaser.netty.userdefineprotocal;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyPersonDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyPersonDecoder decode invoked ");
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);

        PersonProtocal personProtocal = new PersonProtocal();
        personProtocal.setLength(length);
        personProtocal.setContent(content);

        out.add(personProtocal);
    }
}
