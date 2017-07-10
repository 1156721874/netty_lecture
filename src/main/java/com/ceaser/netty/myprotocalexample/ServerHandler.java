package com.ceaser.netty.myprotocalexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by CeaserWang(wangzequan) on 2017/7/10.
 */
    public class ServerHandler extends SimpleChannelInboundHandler<MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyMessage myMessage) throws Exception {
        System.out.println("接收到客户端的请求:"+myMessage.toString());
    }
}
