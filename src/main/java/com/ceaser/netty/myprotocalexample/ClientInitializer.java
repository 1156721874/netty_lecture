package com.ceaser.netty.myprotocalexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by CeaserWang(wangzequan) on 2017/7/10.
 */
public class ClientInitializer extends SimpleChannelInboundHandler<MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyMessage myMessage) throws Exception {
        ChannelPipeline channelPipeline = channelHandlerContext.pipeline();
        channelPipeline.addLast(new MyEncoder());
        channelPipeline.addLast(new MyDecoder());
        channelPipeline.addLast(new ClientHandler());


    }
}
