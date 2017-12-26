package com.ceaser.netty.userdefineprotocal;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<PersonProtocal> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocal msg) throws Exception {
        System.out.println("服务端接受到的数据：");
        System.out.println("数据长度:"+msg.getLength());
        System.out.println("数据内容："+ new String(msg.getContent(), Charset.forName("utf-8")) );
        System.out.println("服务端接收到的消息数量:"+(++count));

        String responseMessage = UUID.randomUUID().toString();
        int responseLength = responseMessage.getBytes(Charset.forName("utf-8")).length;
        byte[] responseContent = responseMessage.getBytes(Charset.forName("utf-8"));
        PersonProtocal personProtocal = new PersonProtocal();
        personProtocal.setLength(responseLength);
        personProtocal.setContent(responseContent);
        ctx.writeAndFlush(personProtocal);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
