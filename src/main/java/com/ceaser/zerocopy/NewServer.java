package com.ceaser.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by Administrator on 2017/9/3.
 * 零拷贝实现
 */
public class NewServer {
    public static void main(String[] args) throws  Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket =  serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));
        serverSocket.setReuseAddress(true);

        ByteBuffer byteBuffer =  ByteBuffer.allocate(4096);

        while(true){

            try {
                SocketChannel socketChannel =  serverSocketChannel.accept();
                socketChannel.configureBlocking(true);

                int readcount = 0;
                while(-1!=readcount){
                    readcount = socketChannel.read(byteBuffer);
                    byteBuffer.rewind();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
