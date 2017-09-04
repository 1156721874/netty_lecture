package com.ceaser.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Created by Administrator on 2017/8/19.
 */
public class NioTest13 {
    public static void main(String[] args) throws  Exception {
        String in = "niofiles/NioTest13in.txt";
        String out = "niofiles/NioTest13out.txt";
        RandomAccessFile inRandomAccessFile = new RandomAccessFile(in,"r");
        RandomAccessFile outRandomAccessFile = new RandomAccessFile(out,"rw");

        long length  = new File(in).length();

        FileChannel inoutFileChannel =  inRandomAccessFile.getChannel();
        FileChannel outputFileChannel = outRandomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = inoutFileChannel.map(FileChannel.MapMode.READ_ONLY,0,length);

        Charset.availableCharsets().forEach((k,v) -> {
            System.out.println(k+":"+v);
        });

        Charset charset = Charset.forName("iso-8859-1");
        CharsetEncoder encoder = charset.newEncoder();
        CharsetDecoder decoder  = charset.newDecoder();

        CharBuffer charBuffer =  decoder.decode(mappedByteBuffer);
        ByteBuffer byteBuffer = encoder.encode(charBuffer);

        //byteBuffer = Charset.forName("iso-8859-1").encode(charBuffer);

        outputFileChannel.write(byteBuffer);
        inRandomAccessFile.close();
        outRandomAccessFile.close();


    }
}
