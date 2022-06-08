package cn.itcast.protocol;

/**
 * @ClassName SequenceIdGenerator
 * @Description TODO
 * @Author admin
 * @Date 2022/6/8 16:24
 * @Version 1.0
 **/

import java.util.concurrent.atomic.AtomicInteger;

public abstract class SequenceIdGenerator {
    private static final AtomicInteger id = new AtomicInteger();

    public static int nextId() {
        return id.incrementAndGet();
    }
}

