package com.ceaser.netty.messageToMessageDecoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * Created by Administrator on 2017/5/20.
 */
public class MyClientIniatializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipline = ch.pipeline();

        pipline.addLast(new MyReplayingDecoder());
        pipline.addLast(new MyLongToByteEncoder());
        pipline.addLast(new MyClientHandler());
    }
}
