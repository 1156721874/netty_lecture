package com.ceaser.netty.myprotocalexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.UUID;

/**
 * Created by CeaserWang(wangzequan) on 2017/7/10.
 */
public class MyClient {
    public static void main(String[] args) {
        //step 1
        //接受 请求
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //实际处理请求数据
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //strp 2
            ServerBootstrap serverBootStrap = new ServerBootstrap();

            //step 3
            serverBootStrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ClientInitializer());

            ChannelFuture future = serverBootStrap.bind(8899).sync();//绑定端口


            byte version = 0xFF;
            String sessionId = UUID.randomUUID().toString();
            String content = "I'm the new  protocol!";

            MyHeader header = new MyHeader(version, content.length(), sessionId);
            LuckMessage message = new LuckMessage(header, content);
            ch.writeAndFlush(message);

            ch.close();


            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
