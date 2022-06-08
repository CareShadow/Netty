package cn.itcast.message;

/**
 * @ClassName PingMessage
 * @Description TODO
 * @Author admin
 * @Date 2022/6/6 22:38
 * @Version 1.0
 **/
public class PingMessage extends Message{
    @Override
    public int getMessageType() {
        return PingMessage;
    }
}
