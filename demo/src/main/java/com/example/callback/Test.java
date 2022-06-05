package com.example.callback;

/**
 * @ClassName Test
 * @Description TODO
 * @Author admin
 * @Date 2022/6/2 22:15
 * @Version 1.0
 **/
public class Test {
    // 同步回调实现
    public static void main(String[] args) {
        Son jack = new Son();
        Mother mother = new Mother(jack);
        mother.parting();
    }
}
