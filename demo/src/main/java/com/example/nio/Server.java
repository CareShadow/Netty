package com.example.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Server
 * @Description TODO
 * @Author admin
 * @Date 2022/5/30 19:26
 * @Version 1.0
 **/
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));
        // 连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while(true){
            log.debug("connecting....");
            // 没连接上 线程在这里阻塞
            SocketChannel sc = ssc.accept();
            if(sc!=null){
                log.debug("connected...{}",sc);
                sc.configureBlocking(false); // 将sc设置成非阻塞
                channels.add(sc);
            }
//            log.debug("connected....{}",sc);
//            channels.add(sc);
            for(SocketChannel channel : channels){
                log.debug("before read....{}",channel);
                // 没读到数据时 线程在这里阻塞
                int read = channel.read(buffer); // 非阻塞,线程会继续运行
                if(read > 0) {
                    log.debug("after read....{}", channel);
                    buffer.flip();
                    buffer.clear();
                }
            }
        }
    }
}
