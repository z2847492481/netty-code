package com.zhq.c01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @DATE: 2024-03-23 20:12
 * @Author: zhq123
 */
@Slf4j
public class NettyServerC01 {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.group(new NioEventLoopGroup());
        // 设置流水线handle
        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                // 解码器
                nioSocketChannel.pipeline().addLast(new StringDecoder());
                // 自定义入栈处理器 tip: 出/入都是站在程序的角度
                nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                    // stringDecoder已经处理了，到这里msg就是string了
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        log.info("msg:{}", msg);
                    }
                });
            }
        });
        // 绑定端口
        serverBootstrap.bind(8080);
    }
}
