package com.ceaser.netty.sixthexample;

import com.ceaser.protobuf.DataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Administrator on 2017/6/6.
 */
public class TestServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
        if(dataType==MyDataInfo.MyMessage.DataType.PersonType){
            MyDataInfo.Person person =   msg.getPerosn();
            System.out.println(person.getName());
            System.out.println(person.getAge());
            System.out.println(person.getAddress());
        }else if(dataType==MyDataInfo.MyMessage.DataType.DogType){
            MyDataInfo.Dog dog = msg.getDog();
            System.out.println(dog.getName());
            System.out.println(dog.getAge());
            System.out.println(dog.getAddress());
        }else{
            MyDataInfo.Cat cat = msg.getCat();
            System.out.println(cat.getName());
            System.out.println(cat.getCity());
        }
    }
}
