package com.ceaser.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/12.
 * telnet localhost 5000
 * telnet localhost 5001
 * ......
 */
public class NioTest12 {
    public static void main(String[] args) throws Exception {
        int[] ports  = new int[5];
        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;

        Selector selector = Selector.open();

        for (int i = 0; i <ports.length ; i++) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket =  serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(ports[i]));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听端口:"+ports[i]);
        }


        while(true){
            int numbers = selector.select();
            System.out.println("numbers : "+numbers);
            Set<SelectionKey> selectionKeys =  selector.selectedKeys();
            System.out.println("selectionKeys : "+selectionKeys);
            Iterator<SelectionKey> iterator=  selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if(selectionKey.isAcceptable()){
                    ServerSocketChannel serverSocketChannel =  (ServerSocketChannel)selectionKey.channel();
                    SocketChannel socketChannel =  serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);
                    iterator.remove();
                    System.out.println("获得客户端连接："+socketChannel);
                }else  if(selectionKey.isReadable()){
                    SocketChannel socketChannel =  (SocketChannel)selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                    int readBytes = 0;
                    while (true){
                        byteBuffer.clear();
                        int read = socketChannel.read(byteBuffer);
                        if(read<=0){
                            break;
                        }
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                        readBytes +=read;
                        System.out.println("读取客户端数据："+readBytes+"通道："+socketChannel);
                    }

                    iterator.remove();;
                }
            }
        }

    }
}
