package com.example.advanced;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName HelloWorldServer
 * @Description TODO
 * @Author admin
 * @Date 2022/6/4 22:38
 * @Version 1.0
 **/
@Slf4j
public class Server2 {
    void start() {
        // boss线程用于建立连接
        NioEventLoopGroup boss = new NioEventLoopGroup();
        // worker线程用于处理nio事件
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            // serverBootstrap.option(ChannelOption.SO_RCVBUF,10);
            // serverBootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR,new AdaptiveRecvByteBufAllocator(16,16,16))
            serverBootstrap.group(boss,worker);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    // 添加换行符解码器
                    // socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    // 添加自定义解码器 解决粘包半包问题
                    ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
                    socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,
                            buffer.writeBytes("shadow".getBytes())));
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
        new Server2().start();
    }
}
