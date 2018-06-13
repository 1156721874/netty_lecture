package com.ceaser.nio;

public class ThreadTest {
    public static void main(String[] args)  {
        MyThread myThread = new MyThread();
        Thread startThread = new Thread(myThread);
        startThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("main--------");
            e.printStackTrace();
        }
        startThread.interrupt();
    }
}

class  MyThread implements  Runnable{
    public void run() {
        int i = 0;
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(2000);
                    System.out.println(i++);
                }catch (InterruptedException e){
                   // Thread.currentThread().interrupt();
                    System.out.println("MyThread------------");
                    e.printStackTrace();
                }
            }
    }
}
