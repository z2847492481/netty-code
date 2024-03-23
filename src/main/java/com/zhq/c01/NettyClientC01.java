package com.zhq.c01;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @DATE: 2024-03-23 20:48
 * @Author: zhq123
 */
@Slf4j
public class NettyClientC01 {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(new NioEventLoopGroup());
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                nioSocketChannel.pipeline().addLast(new StringEncoder());
            }
        });
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(8080));
        // 等待连接成功
        future.sync();
        // 获取channel
        Channel channel = future.channel();
        // 发生数据
        channel.writeAndFlush("hello world");
    }
}
