package com.example.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**
 * @ClassName HelloServer
 * @Description TODO
 * @Author admin
 * @Date 2022/6/1 10:28
 * @Version 1.0
 **/
public class HelloServer {
    public static void main(String[] args) {
        //1. 启动类
        new ServerBootstrap()
                //2.EventLoop 线程+选择器
                .group(new NioEventLoopGroup())
                //3. 服务端channel实现
                .channel(NioServerSocketChannel.class)
                //4. boss负责连接worker(child) 负责处理读写,决定worker(child)能执行那些操作(handler)
                .childHandler(
                        //5. channel代表和客户端进行数据读写的通道初始化,负责添加别的handler
                        new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        //6. 添加具体的handler
                        nioSocketChannel.pipeline().addLast(new StringDecoder());// 将ByteBuffer转换为字符串
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override //读事件
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println(msg); // 打印上一步转换好的字符串
                            }
                        });
                    }
                })
                //7. 绑定监听端口
                .bind(8080);
    }
}
