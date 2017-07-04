package com.ceaser.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Created by Administrator on 2017/6/6.
 */
public class ProtobufTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        DataInfo.Student student =   DataInfo.Student.newBuilder().setName("张三").setAge(22).setAddress("杭州").build();

        byte[] student2ByteArray = student.toByteArray();
        DataInfo.Student student2 = DataInfo.Student.parseFrom(student2ByteArray);
        System.out.println(student2);
        System.out.println(student2.getName());
        System.out.println(student2.getAge());
        System.out.println(student2.getAddress());
    }
}
