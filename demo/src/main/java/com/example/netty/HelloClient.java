package com.example.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * @ClassName HelloClient
 * @Description TODO
 * @Author admin
 * @Date 2022/6/1 10:21
 * @Version 1.0
 **/
public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        new Bootstrap()
                //2. 添加EventLoop 线程+选择器
                .group(new NioEventLoopGroup())
                //3. 选择客户端channel实现
                .channel(NioSocketChannel.class)
                //4. 添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    // 建立后调用处理器
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                    }
                })
                //5. 连接到服务器
                .connect(new InetSocketAddress("localhost",8080))
                .sync()
                .channel()
                .writeAndFlush("hello,world");
    }
}
