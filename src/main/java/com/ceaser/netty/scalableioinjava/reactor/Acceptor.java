package com.ceaser.netty.scalableioinjava.reactor;

/**
 * Description.
 *
 * @author : CeaserWang
 * @version : 1.0
 * @since : 2018/6/11 21:42
 */
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Acceptor implements Runnable
{
    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    public Acceptor(Selector selector,ServerSocketChannel ser)
    {
        this.selector = selector;
        this.serverSocketChannel = ser;
    }
    @Override
    public void run()
    {
        try{
            System.out.println(selector+"  accept ....");

            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel != null){
                /**
                 *  开启了多个reactor池,一个selector负责接收和处理IO
                 * new Handler(selector, socketChannel);
                 */
                //使用的主从结构,一个主selector负责接收,其他的负责处理IO
                Selector selector2 = Server.nextSubReactor().selector;
                System.out.println(selector2+" clientChannel not null..."+socketChannel);
                new Handler(selector2, socketChannel);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}