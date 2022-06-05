package com.example.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class TestEventLoopServer {
    public static void main(String[] args) throws InterruptedException {
       new ServerBootstrap()
               .group(new NioEventLoopGroup())
               .channel(NioServerSocketChannel.class)
               .childHandler(new ChannelInitializer<NioSocketChannel>() {
                   @Override
                   protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                       nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                           @Override
                           public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                              ByteBuf buf = (ByteBuf) msg;
                              log.debug(buf.toString(Charset.defaultCharset()));
                           }
                       });
                   }
               }).bind(8080).sync();
    }
}
