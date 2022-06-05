package com.example.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @ClassName SelectorServer
 * @Description TODO
 * @Author admin
 * @Date 2022/5/30 20:52
 * @Version 1.0
 **/
@Slf4j
public class SelectorServer {
    public static void main(String[] args) throws IOException {
        //1. 创建selector,管理多个channel
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8080));

        //2. 建立selector和channel的联系(注册)
        // SelectionKey 就是将来事件发生时.通过它可以知道事件和那个channel的事件
        SelectionKey register = ssc.register(selector,0,null);
        // register只关注accept事件
        /**
         * accept - 会在有连接请求时触发
         * connect - 是客户端,连接建立后触发
         * read - 可读事件
         * write - 可写事件
         */
        register.interestOps(SelectionKey.OP_ACCEPT);

        while(true){
            //3. select方法,没有事件发生,线程阻塞,有事件线程才能运行
            selector.select();
            //4. 处理事件 selectedKeys是一个集合内部包含所有发生的事件 Set
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey next = iterator.next();
                // 区分事件类型
                iterator.remove(); // 一定要移除
                if(next.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel) next.channel();
                    // 进行处理事件,要么关闭事件,否则不能消耗事件造成循环
                    SocketChannel accept = channel.accept();
                    accept.configureBlocking(false);
                    SelectionKey scKey = accept.register(selector, 0, null);
                    // 进行关注读事件
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.debug("{}",accept);
                }else if(next.isReadable()){
                    try {
                        SocketChannel channel = (SocketChannel) next.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
                        int read = channel.read(byteBuffer);
                        // 客户端正常断开
                        if(read==-1) next.cancel();
                        else {
                            byteBuffer.flip();
                            System.out.println(byteBuffer.toString());
                        }
                    }catch (IOException e){
                        // 客户端异常断开
                        e.printStackTrace();
                        next.cancel();
                    }
                }

            }
        }
    }
}

