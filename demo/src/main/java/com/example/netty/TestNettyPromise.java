package com.example.netty;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @ClassName TestNettyPromise
 * @Description TODO
 * @Author admin
 * @Date 2022/6/3 17:58
 * @Version 1.0
 **/
@Slf4j
public class TestNettyPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop eventLoop = group.next();
        // 多线程结果容器
        DefaultPromise<Integer> defaultPromise = new DefaultPromise<>(eventLoop);
        new Thread(()->{
            log.debug("执行结果");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            defaultPromise.setSuccess(2000);
        }).start();
        log.debug("等待结果");
        log.debug("结果是{}",defaultPromise.get());
    }
}
