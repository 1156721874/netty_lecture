package com.ceaser.netty.forthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by Administrator on 2017/5/20.
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent)evt;
            String evtType = null;
            switch (idleStateEvent.state()){
                case READER_IDLE:
                    evtType = "读空闲";
                    break;
                case WRITER_IDLE:
                    evtType = "写空闲";
                    break;
                case ALL_IDLE:
                    evtType= "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress()+"Time out event "+evtType);
            ctx.channel().close();
        }
    }
}
