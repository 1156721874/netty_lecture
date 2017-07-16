package com.ceaser.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * Created by Administrator on 2017/7/16.
 */
public class NioTest1 {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(10);
        for(int i=0;i<intBuffer.capacity();i++){
            int a = new  SecureRandom().nextInt(20);
            intBuffer.put(a);
        }
        intBuffer.flip();

        while(intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }

    }

}
