package com.ceaser.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * Created by Administrator on 2017/6/7.
 */
public class TestMyClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randomint = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage = null;
        if (randomint == 0) {
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.PersonType).
                    setPerosn(MyDataInfo.Person.newBuilder().setName("张三").setAge(20).setAddress("杭州").build()).
                    build();
        } else if (randomint == 1) {
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.DogType).
                    setDog(MyDataInfo.Dog.newBuilder().setName("dogname").setAge(12).setAddress("狗窝").build()).
                    build();
        } else {
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.CatType).
                    setCat(MyDataInfo.Cat.newBuilder().setName("喵喵").setCity("猫窝").build()).
                    build();
        }
        ctx.channel().writeAndFlush(myMessage);
    }
}
