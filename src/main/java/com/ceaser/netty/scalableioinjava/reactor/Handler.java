package com.ceaser.netty.scalableioinjava.reactor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Description.
 *
 * @author : CeaserWang
 * @version : 1.0
 * @since : 2018/6/11 21:45
 */
public class Handler implements Runnable
{
    final SocketChannel socket;
    final SelectionKey key;
    ByteBuffer input = ByteBuffer.allocate(1024);
    ByteBuffer output = ByteBuffer.allocate(1024);
    static final int READING = 0,SENDING =1;
    int state = READING;

    public Handler(Selector selector, SocketChannel c) throws IOException
    {
        socket = c;
        socket.configureBlocking(false);
        key = socket.register(selector,SelectionKey.OP_READ);
        key.attach(this);
        selector.wakeup();
        System.out.println(selector+" connect success...");
    }
    @Override
    public void run()
    {
        try {
            if(state == READING) {
                read();
            }
            else if(state == SENDING) {
                send();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    boolean inputIsComplete(){
        return input.hasRemaining();
    }
    boolean outputIsComplete(){
        return output.hasRemaining();
    }
    void process(){
        //读数据
        StringBuilder reader = new StringBuilder();
        input.flip();
        while(input.hasRemaining()){
            reader.append((char)input.get());
        }
        System.out.println("[Client-INFO]");
        System.out.println(reader.toString());
        String str = "HTTP/1.1 200 OK\r\nDate: Fir, 10 March 2017 21:20:01 GMT\r\n"+
                "Content-Type: text/html;charset=UTF-8\r\nContent-Length: 24\r\nConnection:close"+
                "\r\n\r\nHelloRector"+System.currentTimeMillis();
        output.put(str.getBytes());
        System.out.println("process .... ");
    }
    void read() throws IOException{
        socket.read(input);
        if(inputIsComplete()){
            process();
            state = SENDING;
            key.interestOps(SelectionKey.OP_WRITE);
        }
    }
    void send() throws IOException {
        output.flip();
        socket.write(output);
        if(outputIsComplete()){
            key.cancel();
        }
        state = READING;
        key.interestOps(SelectionKey.OP_READ);
    }

}