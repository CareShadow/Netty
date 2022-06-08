package cn.itcast.server.service;

/**
 * @ClassName HelloServiceImpl
 * @Description TODO
 * @Author admin
 * @Date 2022/6/7 22:22
 * @Version 1.0
 **/
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String str) {
        return str+"你好";
    }
}
