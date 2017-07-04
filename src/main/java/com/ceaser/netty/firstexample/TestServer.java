package com.ceaser.netty.firstexample;

import com.sun.corba.se.internal.CosNaming.BootstrapServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by Administrator on 2017/5/14.
 * 建立服务端程序，浏览器访问和curl方式访问。
 */
public class TestServer {
    public static void main(String[] args) throws InterruptedException {
        //netty程序 所有的都是这样的流程
        //step 1
        //接受 请求
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //实际处理请求数据
        EventLoopGroup  workerGroup = new NioEventLoopGroup();

        try {
            //strp 2
            ServerBootstrap serverBootStrap = new ServerBootstrap();

            //step 3
            serverBootStrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).childHandler(new TestServerInitializer());

            ChannelFuture future = serverBootStrap.bind(8899).sync();//绑定端口
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
