package com.ceaser.netty.grpc;

import com.ceaser.proto.*;
import io.grpc.stub.StreamObserver;

import java.util.UUID;

/**
 * Created by Administrator on 2017/7/3.
 */
public class StudentServerImpl extends StudentServiceGrpc.StudentServiceImplBase {

    @Override
    public void getRealNameByUserName(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("接收到客户端信息："+request.getUsername());
        responseObserver.onNext(MyResponse.newBuilder().setRealname("张三").build());
        responseObserver.onCompleted();
    }

    /**
     * 返回流式
     * @param request
     * @param responseObserver
     */
    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("接收到客户端信息："+request.getAge());

        responseObserver.onNext(StudentResponse.newBuilder().setName("张三").setAge(20).setCity("北京").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("李四").setAge(30).setCity("天津").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("王五").setAge(40).setCity("上海").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("赵六").setAge(50).setCity("深圳").build());

        responseObserver.onCompleted();
    }

    /**
     * 入参流式
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<StudentRequest> getStudentsWraperByAge(StreamObserver<StudentResponseList> responseObserver) {
        /**
         * 由于客户端过来的数据是流式，每次的请求都会被回调，直到最后完成才会在responseObserver里边添加返回值，然后返回
         */
        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest value) {
                System.out.println("on next :"+value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                StudentResponse studentResponse1 = StudentResponse.newBuilder().setAge(20).setName("张三").setCity("杭州").build();
                StudentResponse studentResponse2 = StudentResponse.newBuilder().setAge(20).setName("张三").setCity("杭州").build();

                StudentResponseList responseList = StudentResponseList.newBuilder().addStudent(studentResponse1).addStudent(studentResponse2).build();
                responseObserver.onNext(responseList);
                responseObserver.onCompleted();
            }
        };
    }

    /**
     * 双向流式
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<StreamRequest> biTalk(StreamObserver<StreamResponse> responseObserver) {
        return new StreamObserver<StreamRequest>() {
            @Override
            public void onNext(StreamRequest value) {
                System.out.println(value.getRequestInfo());
                responseObserver.onNext(StreamResponse.newBuilder().setResponseInfo(UUID.randomUUID().toString()).build());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
