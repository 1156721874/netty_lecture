package com.ceaser.netty.grpc;

import com.ceaser.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;
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

        /**'客户端使用流式那么必须使用异步的方式*/
        StudentServiceGrpc.StudentServiceStub studentServiceStub = StudentServiceGrpc.newStub(managedChannel);


        MyResponse myResponse  =  blockingStub.getRealNameByUserName(MyRequest.newBuilder().setUsername("zhangsan").build());

        System.out.println(myResponse.getRealname());
        System.out.println("-------------------返回流式-------------------");

        Iterator<StudentResponse> iterator =   blockingStub.getStudentsByAge(StudentRequest.newBuilder().setAge(20).build());

        while (iterator.hasNext()) {
            StudentResponse iresponse = iterator.next();
            System.out.println(iresponse.getName()+","+iresponse.getAge()+","+iresponse.getCity());
        }

        System.out.println("------------------请求流式--------------------");

        StreamRequest(studentServiceStub);

        System.out.println("------------------双向流式--------------------");

        StreamObserver<StreamRequest> streamRequestStreamObserver = studentServiceStub.biTalk(new StreamObserver<StreamResponse>() {

            @Override
            public void onNext(StreamResponse value) {
                System.out.println(value.getResponseInfo());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        }) ;

        for(int i=0;i<10;i++){
            streamRequestStreamObserver.onNext(StreamRequest.newBuilder().setRequestInfo(LocalDateTime.now().toString()).build());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
            }
        }

    }

    /*
    请求流式
     */
    private static void StreamRequest(StudentServiceGrpc.StudentServiceStub studentServiceStub) {
        StreamObserver<StudentResponseList> studentResponseStreamObserver = new StreamObserver<StudentResponseList>() {
            @Override
            public void onNext(StudentResponseList value) {
                value.getStudentList().forEach(studentResponse -> {
                    System.out.println(studentResponse.getName());
                    System.out.println(studentResponse.getAge());
                    System.out.println(studentResponse.getCity());
                    System.out.println("---------------");

                });
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        };

        StreamObserver<StudentRequest> studentRequestStreamObserver = studentServiceStub.getStudentsWraperByAge(studentResponseStreamObserver);
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(20).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(30).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(40).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(50).build());

        studentRequestStreamObserver.onCompleted();

        /**
         * 由于是异步的如果我们的程序不去等待一段时间，那么执行完：
         * StreamObserver<StudentRequest> studentRequestStreamObserver = studentServiceStub.getStudentsWraperByAge(studentResponseStreamObserver);
         * 之后会接着往下执行，消息还没有发送出去，jvm就退出了，这样会导致数据无法发送，因此需要线程等待一段时间
         */
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
