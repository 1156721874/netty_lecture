package com.ceaser.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by Administrator on 2017/9/3.
 */
public class NewClient {
    public static void main(String[] args)  throws  Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8899));
        socketChannel.configureBlocking(true);
        String name = "niofiles/spark-2.2.0-bin-hadoop2.7.tgz";
        FileChannel fileChannel = new FileInputStream(name).getChannel();
        long start = System.currentTimeMillis();
        //零拷贝关键代码
        /**
         * This method is potentially much more efficient than a simple loop
         * that reads from this channel and writes to the target channel.  Many
         * operating systems can transfer bytes directly from the filesystem cache
         * to the target channel without actually copying them.
         */
       long  transCount =  fileChannel.transferTo(0,fileChannel.size(),socketChannel);

        System.out.println("发送字节数:"+transCount+",耗时："+(System.currentTimeMillis()-start));
        fileChannel.close();
    }
}
