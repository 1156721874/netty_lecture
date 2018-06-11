package com.ceaser.netty.scalableioinjava.reactor;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * Description.
 *
 * @author : CeaserWang
 * @version : 1.0
 * @since : 2018/6/11 21:44
 */
public class Server
{
    private static Reactor mainReactor;//主要负责接收请求
    private static Reactor[] subReactors;//负责处理IO
    private static final int SUB_SIZE = 3;
    private static final int port = 1234;

    private static AtomicInteger nextIndex = new AtomicInteger();

    public static Reactor nextSubReactor(){
        long nextIndexValue = nextIndex.getAndIncrement();
        if(nextIndexValue < 0){
            nextIndex.set(0);
            nextIndexValue = 0;
        }
        return subReactors[(int) (nextIndexValue%subReactors.length)];
    }

    public static void main(String[] args)
    {
        try
        {
            mainReactor = new Reactor(port, true);
            subReactors = new Reactor[SUB_SIZE];
            for(int i=0; i< subReactors.length; i++){
                subReactors[i] = new Reactor(port, false);
            }
            mainReactor.start();
            for(int i=0; i< subReactors.length; i++){
                subReactors[i].start();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}