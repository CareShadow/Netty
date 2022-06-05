package com.example.advanced;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @ClassName HelloWorldServer
 * @Description TODO
 * @Author admin
 * @Date 2022/6/4 22:38
 * @Version 1.0
 **/
@Slf4j
public class HelloWorldServer {
    void start() {
        // boss线程用于建立连接
        NioEventLoopGroup boss = new NioEventLoopGroup();
        // worker线程用于处理nio事件
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss,worker);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(8));
                    socketChannel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(8080);
            log.debug("{} binding....",channelFuture.channel());
            channelFuture.sync();
            channelFuture.channel().closeFuture().sync();
            log.debug("{} bound....",channelFuture.channel());
        }catch (InterruptedException e){
            e.printStackTrace();
            log.debug("server error",e);

        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            log.debug("stop");
        }
    }

    public static void main(String[] args) {
        new HelloWorldServer().start();
    }
}
