package com.example.callback;

/**
 * @ClassName Son
 * @Description TODO
 * @Author admin
 * @Date 2022/6/2 22:13
 * @Version 1.0
 **/
public class Son {
    public void rideTrain(Callable callable){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        callable.call();
    }
}
