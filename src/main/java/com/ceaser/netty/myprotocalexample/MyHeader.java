package com.ceaser.netty.myprotocalexample;

import java.util.Arrays;

/**
 * header class
 */
public class MyHeader {
    //protocal version
    private byte version;
    //data length
    private int contentLength;
    //server name
    private byte[] sessionId;

    public MyHeader(byte version, int contentLength, byte[] sessionId) {
        this.version = version;
        this.contentLength = contentLength;
        this.sessionId = sessionId;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public int getContentLength() {
        return contentLength;
    }

    @Override
    public String toString() {
        return "MyHeader{" +
                "version=" + version +
                ", contentLength=" + contentLength +
                ", sessionId=" + Arrays.toString(sessionId) +
                '}';
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getSessionId() {
        return sessionId;
    }

    public void setSessionId(byte[] sessionId) {
        this.sessionId = sessionId;
    }




}
