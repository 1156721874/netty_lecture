package com.ceaser.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Administrator on 2017/8/19.
 */
public class NioServer {
    private static Map<String,SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket =  serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        while(true){
            try{
                selector.select(); //方法一直阻塞，当有任何一个channel准备完成就会返回，返回值是变化的channel的数量
                Set<SelectionKey> selectionKeys = selector.selectedKeys();//当 selector.select()返回 我们就可以得到被选中的channel对应的SelectionKey集合

                selectionKeys.forEach(selectionKey -> {
                  final   SocketChannel client ;
                    try{
                        //针对于刚开始注册的serverSocketChannel属于连接请求，得到这个事件
                            if(selectionKey.isAcceptable()){
                                //获取连接进来的通道
                                ServerSocketChannel server =   (ServerSocketChannel)selectionKey.channel();
                                client = server.accept();
                                client.configureBlocking(false);
                                client.register(selector,SelectionKey.OP_READ);
                                String key = "【"+ UUID.randomUUID()+"】";
                                clientMap.put(key,client);
                            }else  if(selectionKey.isReadable()){
                                client =   (SocketChannel)selectionKey.channel();
                                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                int count = client.read(byteBuffer);
                                if(count>0){
                                    byteBuffer.flip();
                                    Charset charset = Charset.forName("utf-8");
                                    String receiveMessage = String.copyValueOf(charset.decode(byteBuffer).array());

                                    System.out.println(client+":"+receiveMessage);
                                    String sendKey = "";
                                    //找到发送者的key
                                    for(Map.Entry<String,SocketChannel> entry: clientMap.entrySet()){
                                        if(client==entry.getValue()){
                                            sendKey = entry.getKey();
                                            break;
                                        }
                                    }

                                    //推送消息给出推送者是谁，即key
                                    for(Map.Entry<String,SocketChannel> entry: clientMap.entrySet()){
                                        SocketChannel value = entry.getValue();
                                        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                        writeBuffer.put((sendKey+":"+receiveMessage).getBytes());
                                        writeBuffer.flip();
                                        value.write(writeBuffer);
                                    }
                                }
                            }else if(!selectionKey.isConnectable()){
                                System.out.println("closig...............");
                            }
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                });
                //使用完毕清除key，以免下次被使用。一定要删除。
                selectionKeys.clear();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
