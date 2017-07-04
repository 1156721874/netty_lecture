package com.ceaser.netty.grpc;

import com.ceaser.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

/**
 * Created by Administrator on 2017/7/3.
 */
public class GrpcClient {
    public static void main(String[] args) {
        System.out.println("-------------------普通-------------------");
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost",8899)
                .usePlaintext(true).build();//usePlaintext 使用无加密的文本形式传输数据

        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.
                newBlockingStub(managedChannel);

        MyResponse myResponse  =  blockingStub.getRealNameByUserName(MyRequest.newBuilder().setUsername("zhangsan").build());

        System.out.println(myResponse.getRealname());
        System.out.println("-------------------返回流式-------------------");

        Iterator<StudentResponse> iterator =   blockingStub.getStudentsByAge(StudentRequest.newBuilder().setAge(20).build());

        while (iterator.hasNext()) {
            StudentResponse iresponse = iterator.next();
            System.out.println(iresponse.getName()+","+iresponse.getAge()+","+iresponse.getCity());
        }

        System.out.println("------------------请求流式--------------------");



    }

}
