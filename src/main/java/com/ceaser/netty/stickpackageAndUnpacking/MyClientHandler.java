package com.ceaser.netty.stickpackageAndUnpacking;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * Created by Administrator on 2017/5/20.
 */
public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);

        String message = new String(buffer,Charset.forName("utf-8"));
        System.out.println("客户端接收到的消息内容："+message);
        System.out.println("客户端接收到的消息数量："+(++this.count));

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i = 0;i <10;i++){
            ByteBuf buffer  = Unpooled.copiedBuffer("sent from client",Charset.forName("utf-8"));
            ctx.writeAndFlush(buffer);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}

