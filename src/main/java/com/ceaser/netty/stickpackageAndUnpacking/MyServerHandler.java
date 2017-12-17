package com.ceaser.netty.stickpackageAndUnpacking;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Created by Administrator on 2017/5/20.
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int  count ;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()] ;
        msg.readBytes(buffer);//注意buffer的长度必须和msg.readableBytes()一样，否则报错，这是netty规定的
        String message = new String(buffer, Charset.forName("utf-8"));
        System.out.println("服务端接收到的消息："+message);
        System.out.println("服务端接收到的消息数量："+(++this.count));

        ByteBuf responseMessage = Unpooled.copiedBuffer(UUID.randomUUID().toString(),Charset.forName("utf-8"));
        ctx.writeAndFlush(responseMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
