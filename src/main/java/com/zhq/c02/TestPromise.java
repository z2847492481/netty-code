package com.zhq.c02;

import io.netty.channel.*;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @DATE: 2024-03-30 13:24
 * @Author: zhq123
 */
@Slf4j
public class TestPromise {
    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new DefaultEventLoopGroup(1);
        EventLoop eventLoop = eventLoopGroup.next();
        Promise<String> promise = new DefaultPromise<>(eventLoop);
        // 使用promise时，任务的类型不重要，无论是callable接口还是runnable接口，都能通过promise设置或返回值
        // 而如果仅仅是使用netty的future，则只能使用callable拿到结果，但是感觉很鸡肋，没什么特别的地方
        eventLoop.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                promise.setSuccess("success");
            } catch (InterruptedException e) {
                promise.setFailure(e);
            }
        });
        promise.addListener(new GenericFutureListener<Future<? super String>>() {
            @Override
            public void operationComplete(Future<? super String> future) throws Exception {
                if (future.isSuccess()) {
                    log.info("result:{}", future.get());
                }else {
                    log.info("error:{}", future.cause().getMessage());
                }
            }
        });
    }
}
