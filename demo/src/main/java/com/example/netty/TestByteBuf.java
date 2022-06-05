package com.example.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @ClassName TestByteBuf
 * @Description TODO
 * @Author admin
 * @Date 2022/6/3 22:10
 * @Version 1.0
 **/
public class TestByteBuf {
    public static void main(String[] args) {
        // nio的ByteBuffer无法进行扩容,netty的ByteBuf可自己进行扩容
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();// 默认调用的直接内存 
        System.out.println(buffer);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 300; i++) {
            sb.append("a");
        }
        buffer.writeBytes(sb.toString().getBytes());
    }
}
