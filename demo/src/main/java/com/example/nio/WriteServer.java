package com.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @ClassName WriteServer
 * @Description TODO
 * @Author admin
 * @Date 2022/5/31 9:37
 * @Version 1.0
 **/
public class WriteServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        Selector selector = Selector.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8080));
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        while(true){
            selector.select();
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while(iter.hasNext()){
                SelectionKey key = iter.next();
                iter.remove();
                if(key.isAcceptable()){
                    ServerSocketChannel channel =  (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, SelectionKey.OP_READ);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 30000000; i++) {
                        sb.append("a");
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());
                    int write = sc.write(buffer);
                    System.out.println("实际写入多少字节"+write);
                    if(buffer.hasRemaining()){
                         // 在原来关注事件上加入写事件
                         scKey.interestOps(scKey.interestOps()+SelectionKey.OP_WRITE);
                        scKey.attach(buffer);
                    }
                }else if(key.isWritable()){
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                     SocketChannel sc = (SocketChannel) key.channel();
                     int write = sc.write(buffer);
                    System.out.println("实际写入字节"+write);
                    if(!buffer.hasRemaining()){
                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);
                        key.attach(null);
                    }else{
                        // 如果还未写完的话
                        key.attach(buffer);
                    }
                }else if(key.isReadable()){
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    SocketChannel channel = (SocketChannel) key.channel();
                    int read = channel.read(buffer);
                    System.out.println(read);
                }
            }
        }
    }
}
