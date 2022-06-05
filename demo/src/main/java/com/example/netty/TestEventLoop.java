package com.example.netty;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName TestEventLoop
 * @Description TODO
 * @Author admin
 * @Date 2022/6/2 15:46
 * @Version 1.0
 **/
@Slf4j
public class TestEventLoop {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup nioWorks = new NioEventLoopGroup(2);
        log.debug("server start ..");
        // 使用NioEventLoop处理普通任务
        nioWorks.next().submit(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("ok");
        });
        // 使用NioEventLoop处理定时任务
        nioWorks.scheduleAtFixedRate(()->{
            log.debug("runing....");
        },0,1, TimeUnit.SECONDS);
        log.debug("main");
    }
}
