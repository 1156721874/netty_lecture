package com.ceaser.zerocopy;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2017/9/3.
 * 非零拷贝方式
 */
public class OldServer  {

    public static void main(String[] args)  throws  Exception{
        ServerSocket serverSocket = new ServerSocket(8899);
        while(true){
            Socket socket  = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            byte[] buff = new byte[4096];

            while(true){
                int readcount = dataInputStream.read(buff,0,buff.length);

                if(-1==readcount){
                    break;
                }
            }
        }
    }
}
