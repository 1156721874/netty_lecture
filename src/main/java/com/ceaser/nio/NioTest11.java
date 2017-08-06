package com.ceaser.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/8/6.
 * 命令行输入 telnet localhost 8899
 * hellowor
 *
 */
public class NioTest11 {
    public static void main(String[] args) throws  Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8899));
        int messageLength = 2+3+4;
        ByteBuffer [] byteBuffers = new ByteBuffer[3];
        byteBuffers[0] = ByteBuffer.allocate(2);
        byteBuffers[1] = ByteBuffer.allocate(3);
        byteBuffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel= serverSocketChannel.accept();

        while(true){
            int byteRead = 0;
            while(byteRead<messageLength){
                long r =  socketChannel.read(byteBuffers);
                byteRead +=r;
                System.out.println("byteRead :"+byteRead);
                Arrays.asList(byteBuffers).stream().map(byteBuffer -> "position"+byteBuffer.position()+", limit :"+byteBuffer.limit()).forEach(System.out::println);
            }

            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());

            long byteWrite = 0;
            while(byteWrite<messageLength){
                byteWrite = socketChannel.write(byteBuffers);
            }
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());

            System.out.println("byteRead :"+byteRead+", byteWrite"+byteWrite+",messageLength : "+messageLength);
        }
    }
}
