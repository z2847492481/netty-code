package com.zhq.c02;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.Future;

import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * @DATE: 2024-03-30 13:36
 * @Author: zhq123
 */
public class TestNettyFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoopGroup eventLoopGroup = new DefaultEventLoopGroup(1);
        EventLoop eventLoop = eventLoopGroup.next();
        eventLoop.submit(() -> {
            Random random = new Random(100);
            if (random.nextInt(150) > 111) {
                throw new RuntimeException("big");
            }
        });
    }
}
