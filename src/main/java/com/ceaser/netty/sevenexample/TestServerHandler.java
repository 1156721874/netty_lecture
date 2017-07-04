package com.ceaser.netty.sevenexample;

import com.ceaser.proto.MyRequest;
import com.ceaser.proto.MyResponse;
import com.ceaser.proto.StudentServiceGrpc;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Administrator on 2017/6/25.
 */
public class TestServerHandler extends SimpleChannelInboundHandler<MyRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyRequest msg) throws Exception {
        System.out.println("client param : "+msg);
        ctx.channel().writeAndFlush(null);
    }
}
