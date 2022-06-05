package com.example.netty;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName TestNettyFuture
 * @Description TODO
 * @Author admin
 * @Date 2022/6/3 15:56
 * @Version 1.0
 **/
@Slf4j
public class TestNettyFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop eventLoop = group.next();
        Future<Integer> future = eventLoop.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("执行计算");
                return 70;
            }
        });
        log.debug("等待结果");
        //4. 线程未执行完,主线程会阻塞在这.
        // 同步处理
//        log.debug("结果是{}",future.get());
        // 异步处理
        future.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                log.debug("结果是{}",future.getNow());
            }
        });
    }
}
