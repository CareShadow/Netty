package com.example.text;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @ClassName EchoServer
 * @Description TODO
 * @Author admin
 * @Date 2022/6/4 19:34
 * @Version 1.0
 **/
@Slf4j
public class EchoServer {
    public static void main(String[] args) {
        ChannelFuture chanelFuture = new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buffer = (ByteBuf) msg;
                                log.debug("转换为字符串{}", buffer.toString(Charset.defaultCharset()));
                                // 建议使用ctx.alloc()创建ByteBuf
                                ByteBuf response = ctx.alloc().buffer();
                                response.writeBytes(buffer);
                                ctx.writeAndFlush(response);
//                                String response = (String) msg;
//                                log.debug("转换为字符串{}",response);
//                                ctx.writeAndFlush(response);
                            }
                        });
                    }
                }).bind(8080);
        Channel channel = chanelFuture.channel();
        new Thread(()->{}).start();

    }
}
