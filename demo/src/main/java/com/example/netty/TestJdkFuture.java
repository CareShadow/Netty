package com.example.netty;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @ClassName TestJdkFuture
 * @Description TODO
 * @Author admin
 * @Date 2022/6/3 15:46
 * @Version 1.0
 **/
@Slf4j
public class TestJdkFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1. 建立线程池
        ExecutorService service = Executors.newFixedThreadPool(1);
        //2. 提交任务
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("执行计算");
                Thread.sleep(5000);
                return 100;
            }
        });
        //3. 主线程通过future来获取结果
        log.debug("等待结果");
        //4. 线程未执行完,主线程会阻塞在这.
        log.debug("结果是{}",future.get());
    }
}
