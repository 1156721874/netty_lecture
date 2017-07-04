package com.ceaser.netty.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/7/3.
 */
public class GrpcServer {

    private Server server;

    private void start() throws IOException {
        this.server = ServerBuilder.forPort(8899).addService(new StudentServerImpl()).build().start();
        System.out.println("server started");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("关闭jvm");
                GrpcServer.this.stop();
        }));
        System.out.println("执行到这里");

    }


    private void stop()  {
        if(null!=this.server){
            this.server.shutdown();
        }
    }

    //调用此方法实现阻塞，不然启动之后立马退出
    private void awaitTermination() throws InterruptedException {
        if(null!=server){
            //this.server.awaitTermination(3000,TimeUnit.MILLISECONDS);//等待3000毫秒，时间到了不会等待，即终止服务
            this.server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        GrpcServer server = new GrpcServer();
        server.start();
        server.awaitTermination();
    }

}
