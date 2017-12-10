package com.ceaser.netty.ByteBuf;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicUpdatorTest {
    public static void main(String[] args) {
        Person person = new Person();
 /*       for(int i=0;i<10;++i){
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println( person.age++);
            });
            thread.start();
        }*/
        //会有严重的问题，会出现多次打印1之类的问题。。这个时候是AtommicIntegerUpdator登场的时候了

        AtomicIntegerFieldUpdater<Person> atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(Person.class,"age");
        for(int i=0;i<10;++i){
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println( atomicIntegerFieldUpdater.getAndIncrement(person));
            });
            thread.start();
        }

    }

}

class Person{
   volatile int age = 1;
}
