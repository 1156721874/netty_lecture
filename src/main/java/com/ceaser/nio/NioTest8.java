package com.ceaser.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2017/8/6.
 */
public class NioTest8 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream  = new FileInputStream("niofiles/input2.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("niofiles/output2.txt");

        FileChannel fileInputStreamChannel = fileInputStream.getChannel();

        FileChannel fileOutputStreamChannel =  fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(512);
        while(true){
            byteBuffer.clear();//注释掉会发生什么情况
            int read = fileInputStreamChannel.read(byteBuffer);
            System.out.println("read : "+read);
            if(-1 == read){
                break;
            }
            byteBuffer.flip();
            fileOutputStreamChannel.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
/**
 *
 *         ByteBuffer byteBuffer = ByteBuffer.allocateDirect(512);
 *         直接内存：返回DirectByteBuffer对象，DirectByteBuffer的父类是MappedByteBuffer ，MappedByteBuffer 的父类是ByteBuffer ， 在ByteBuffer的上边是Buffer，在
 *         Buffer里边有一个address 他的声明和注释如下：
 *              // Used only by direct buffers
                // NOTE: hoisted here for speed in JNI GetDirectBufferAddress
                long address;
                address是专门为DirectByteBuffer使用的，存储是堆外内存的地址。在 DirectByteBuffer 的构造器里边，会对 address 进行赋值。
 DirectByteBuffer使用的是直接的对外内存，去除了使用HeapByteBuffer方式的内存拷贝，因此有另外一个说法叫“零拷贝”，address对应的内存区域在os的内存空间，这块内存直接与io设备进行交互，当jvm对
 DirectByteBuffer内存垃圾回收的时候，会通过address调os，os将address对应的区域回收。



 *         ByteBuffer byteBuffer = ByteBuffer.allocate(512);
 *         堆内存：返回 HeapByteBuffer
 *          HeapByteBuffer是在jvm的内存范围之内，然后在调io的操作时会将数据区域拷贝一份到os的内存区域，这样造成了不必要的性能上的降低，这样做是有原因的，试想假设如果os和jvm都是用jvm里边的数据区域，
 *          但是jvm会对这块内存区域进行GC回收，可能会对这块内存的数据进行更改，根据我们的假设，由于这块区域os也在使用，jvm对这块共享数据发生了变更，os那边就会出现数据错乱的情况。那么如果不让jvm对这块
 *          共享区域进行GC是不是可以避免这个问题呢？答案是不行的，也会存在问题，如果jvm不对其进行GC回收，jvm这边可能会出现OOM的内存溢出。因此，最后这个地方非常尴尬，只能拷贝jvm的那一份到os的内存空间，即使jvm那边的
 *          数据区域被改变，但是os里边的不会受到影响，等os使用io结束后会对这块区域进行回收，因为这是os的管理范围之内。
 *
 *
 */