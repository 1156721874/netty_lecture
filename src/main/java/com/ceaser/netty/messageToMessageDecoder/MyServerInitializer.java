package com.ceaser.netty.messageToMessageDecoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * Created by Administrator on 2017/5/20.
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipline = ch.pipeline();
        pipline.addLast(new MyReplayingDecoder());//byte to long decoder
        pipline.addLast(new MyMessageToMessageDecoder());    //long to string decoder
        pipline.addLast(new MyLongToByteEncoder());
        pipline.addLast(new MyServerHandler());
    }
}
