package com.ceaser.netty.firstexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * Created by Administrator on 2017/5/14.
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipline  = ch.pipeline();

        //这些对象不要搞成单利的，要用多实例的对象，每次都用新的
        pipline.addLast("httpServerCodec",new HttpServerCodec());//编解码作用
        pipline.addLast("testHttpServerHandle",new TestHttpServerHandle());//处理请求
    }
}
