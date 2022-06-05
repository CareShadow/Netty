package com.example.advanced;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @ClassName TestLengthFieldDecoder
 * @Description TODO
 * @Author admin
 * @Date 2022/6/5 15:44
 * @Version 1.0
 **/
public class TestLengthFieldDecoder {
    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024,0,4,4,4),
                new LoggingHandler(LogLevel.DEBUG)
        );
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        send(buffer,"shadow");
        send(buffer,"wen chen xin shi yi ge da sha bi");
        channel.writeInbound(buffer);
    }
    private static void send(ByteBuf buffer, String context) {
        byte[] bytes = context.getBytes(); // 实际内容
        int length = bytes.length; // 实际内容长度
        buffer.writeInt(length);
        buffer.writeInt(1);
        buffer.writeBytes(bytes);
    }

}

