package com.ceaser.netty.test;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * Created by Administrator on 2017/9/10.
 */
public class Test {
    public static void main(String[] args) {
        int result = Math.max(1, SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        System.out.println(result);
    }
}
