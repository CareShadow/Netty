package com.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @ClassName WriteClient
 * @Description TODO
 * @Author admin
 * @Date 2022/5/31 9:56
 * @Version 1.0
 **/
public class WriteClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);// 设置成非阻塞
        Selector selector = Selector.open();
        sc.register(selector,SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
        sc.connect(new InetSocketAddress("localhost",8080));
        int count = 0;
        while(true){
            selector.select();
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while(iter.hasNext()){
                SelectionKey key = iter.next();
                iter.remove();
                if(key.isConnectable()){
                    sc.finishConnect();
                    ByteBuffer buffer = Charset.defaultCharset().encode("hello world");
                    int read = sc.write(buffer);
                    System.out.println("客户端写完");
                }else if(key.isReadable()){
                    ByteBuffer buffer = ByteBuffer.allocate(1024*1024);
                    count+=sc.read(buffer);
                    buffer.clear();
                    System.out.println(count);
                }
            }
        }
    }
}
