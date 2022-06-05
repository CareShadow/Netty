package com.example.callback;

/**
 * @ClassName Mother
 * @Description TODO
 * @Author admin
 * @Date 2022/6/2 22:10
 * @Version 1.0
 **/
// 定义类Mother实现Callable接口 实现call()回调函数的具体内容
public class Mother implements Callable{
    private Son son;
    public Mother(Son son){
        this.son = son;
    }
    // 表示mother和son分别的函数,son在这期间搭乘火车离开
    public void parting(){
        System.out.println("开始执行同步回调函数");
//        son.rideTrain(this);
        new Thread(()->{
            son.rideTrain(this);
        }).start();
        System.out.println("同步回调函数执行完成");
    }
    @Override
    public void call() {
        System.out.println("儿子到学校了");
    }
}
