package com.ceaser.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2017/9/3.
 */
public class OldClient {
    public static void main(String[] args) throws  Exception {
        Socket socket = new Socket("localhost",8899);
        InputStream inputStream = new FileInputStream("niofiles/spark-2.2.0-bin-hadoop2.7.tgz");
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        int totalSend = 0;
        int readCount = 0;
        byte[] buff =new byte[4096];
        long startTime = System.currentTimeMillis();
        while((readCount=inputStream.read(buff))>=0){
            totalSend+=readCount;
            dataOutputStream.write(buff);
        }
        System.out.println("send bytes :"+totalSend+",timecost:"+(System.currentTimeMillis()-startTime));
        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
