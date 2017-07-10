package com.ceaser.netty.secondexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;


/**
 * Created by Administrator on 2017/5/20.
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipline = ch.pipeline();
        pipline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        pipline.addLast(new LengthFieldPrepender(4));
        pipline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipline.addLast(new MyServerHandler());
    }
}
/**
 * 粘包
 *
 * 一次write对应多次read，为了解决这个问题netty提供了两个重要的handler:LengthFieldPrepender和LengthFieldBasedFrameDecoder
 *
 LengthFieldBasedFrameDecoder
 netty的拆包过程和自己写手工拆包并没有什么不同，都是将字节累加到一个容器里面，判断当前累加的字节数据是否达到了一个包的大小，达到一个包大小就拆开，进而传递到上层业务解码handler
 **/