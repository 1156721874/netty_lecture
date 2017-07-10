package com.ceaser.netty.myprotocalexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by CeaserWang(wangzequan) on 2017/7/10.
 */
public class ClientHandler  extends SimpleChannelInboundHandler<MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyMessage myMessage) throws Exception {
        System.out.println("客户端接受服务端的消息:"+myMessage);
    }
}
