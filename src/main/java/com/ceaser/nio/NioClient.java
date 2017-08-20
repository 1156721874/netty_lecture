package com.ceaser.nio;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/8/19.
 */
public class NioClient {
    public static void main(String[] args) throws  Exception {
        try{
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            Selector selector  = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("127.0.0.1",8899));

            while (true){
                selector.select();
                Set<SelectionKey>  selectionKeys = selector.selectedKeys();
                for(SelectionKey selectionKey :selectionKeys){
                    if(selectionKey.isConnectable()){
                       SocketChannel client = (SocketChannel) selectionKey.channel();
                       //准备连接
                       if(client.isConnectionPending()){
                           client.finishConnect();//完成连接
                           ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                           writeBuffer.put((LocalDateTime.now()+":连接成功").getBytes());
                           writeBuffer.flip();
                           client.write(writeBuffer);

                           ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                           executorService.submit(() -> {
                               while (true){
                                   try{
                                       writeBuffer.clear();
                                       InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                       BufferedReader bufferedReader = new BufferedReader(inputStreamReader) ;
                                       writeBuffer.put(bufferedReader.readLine().getBytes());
                                       writeBuffer.flip();
                                       client.write(writeBuffer);
                                   }catch (Exception ex){
                                       ex.printStackTrace();
                                   }
                               }
                           });
                       }
                        client.register(selector,SelectionKey.OP_READ);
                    }else if(selectionKey.isReadable()){
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        int count = client.read(readBuffer);
                        if(count>0){
                            String receiveMeassage = new String(readBuffer.array(),0,count);
                            System.out.println(receiveMeassage);
                        }
                    }
                }
                selectionKeys.clear();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
