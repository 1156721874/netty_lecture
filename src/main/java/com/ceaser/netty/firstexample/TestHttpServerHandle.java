package com.ceaser.netty.firstexample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * SimpleChannelInboundHandler 请求进来的的处理
 * SimpleChanneOutboundHandler 请求出去的的处理
 */
public class TestHttpServerHandle extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println(msg.getClass());
        System.out.println(ctx.channel().remoteAddress());

        if( msg instanceof HttpRequest){
            HttpRequest httpRequest = (HttpRequest)msg;
            System.out.println("请求方法名："+httpRequest.method().name());

            URI uri = new URI(httpRequest.getUri());
            if("/favicon".equals(uri.getPath())){
                System.out.println("请求favicon");
                return ;
            }
            ByteBuf content = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
            ctx.writeAndFlush(response);
           // ctx.channel().close();
            /**
             * 加入 ctx.channel().close();打印 结果，会出现销毁的打印。
             * handlerAdded invoked...
             handlerAdded invoked...
             handlerAdded invoked...
             channelRegistered invoked...
             handlerAdded invoked...
             handlerAdded invoked...
             channelRegistered invoked...
             channelActive invoked...
             channelRegistered invoked...
             channelRegistered invoked...
             channelActive invoked...
             channelRegistered invoked...
             channelActive invoked...
             channelActive invoked...
             channelActive invoked...
             请求方法名：GET
             请求方法名：GET
             */
        }
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded invoked...");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered invoked...");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive invoked...");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive invoked...");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered invoked...");
        super.channelUnregistered(ctx);
    }
}
    /**
     * curl -X post  "http://localhost:8899" 调用顺序：
     *  handlerAdded invoked...
     channelRegistered invoked...
     channelActive invoked...
     请求方法名：delete
     channelInactive invoked...
     channelUnregistered invoked...

     如果是在浏览器访问：
     http://localhost:8899/ ：
     handlerAdded invoked...
     channelRegistered invoked...
     channelActive invoked...
     请求方法名：GET
     请求方法名：GET
     请求方法名：GET

     浏览器的方式不会有channelInactive、channelUnregistered的打印
     netty会在默认的超时时间到达时主动关闭连接，那个时候会调用打印。
     */
