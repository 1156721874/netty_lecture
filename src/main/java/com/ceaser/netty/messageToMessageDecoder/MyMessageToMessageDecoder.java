package com.ceaser.netty.messageToMessageDecoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class MyMessageToMessageDecoder  extends MessageToMessageDecoder<Long> {

    @Override
    protected void decode(ChannelHandlerContext ctx, Long msg, List<Object> out) throws Exception {
        System.out.println("MyMessageToMessageDecoder invoked !");
        out.add(msg+"");
    }
}
