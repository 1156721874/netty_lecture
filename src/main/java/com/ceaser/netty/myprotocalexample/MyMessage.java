package com.ceaser.netty.myprotocalexample;

import java.util.Arrays;

/**
 * message body
 */
public class MyMessage {

    private MyHeader myHeader;
    private byte[] content;

    public MyHeader getMyHeader() {
        return myHeader;
    }

    public void setMyHeader(MyHeader myHeader) {
        this.myHeader = myHeader;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public MyMessage(MyHeader myHeader, byte[] content) {

        this.myHeader = myHeader;
        this.content = content;
    }

    @Override
    public String toString() {
        return "MyMessage{" +
                "myHeader=" + myHeader +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
