package com.ceaser.netty.myprotocalexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by CeaserWang(wangzequan) on 2017/7/10.
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline =  socketChannel.pipeline();
        pipeline.addLast(new MyEncoder());
        pipeline.addLast(new MyDecoder());
        pipeline.addLast(new ServerHandler());
    }
}
