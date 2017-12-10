package com.ceaser.netty.secondexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/5/20.
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+" --> "+msg);
        ctx.channel().writeAndFlush("from server : "+ UUID.randomUUID());
        ctx.writeAndFlush(msg);

       /* ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(() -> {
            ctx.channel().writeAndFlush("from server : "+ UUID.randomUUID());
        });*/

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
