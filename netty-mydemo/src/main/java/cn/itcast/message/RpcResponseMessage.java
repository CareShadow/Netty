package cn.itcast.message;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName RpcResponseMessage
 * @Description TODO
 * @Author admin
 * @Date 2022/6/7 19:54
 * @Version 1.0
 **/
@Data
@ToString(callSuper = true)
public class RpcResponseMessage extends Message {
    /**
     * 返回值
     */
    private Object returnValue;
    /**
     * 异常值
     */
    private Exception exceptionValue;

    @Override
    public int getMessageType() {
        return RPC_MESSAGE_TYPE_RESPONSE;
    }
}
