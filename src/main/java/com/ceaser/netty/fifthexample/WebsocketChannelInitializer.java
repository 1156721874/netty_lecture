package com.ceaser.netty.fifthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by Administrator on 2017/5/28.
 */
public class WebsocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel ch) throws Exception {
      ChannelPipeline channelPipeline =  ch.pipeline();
        channelPipeline.addLast(new HttpServerCodec());
        channelPipeline.addLast( new ChunkedWriteHandler());
        channelPipeline.addLast(new HttpObjectAggregator(8192));
        channelPipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        channelPipeline.addLast(new TextWebSocketChannelHandler());
        //ws://localhost:9988/ws 参数 /ws指的是端口后边的ws
    }
}
