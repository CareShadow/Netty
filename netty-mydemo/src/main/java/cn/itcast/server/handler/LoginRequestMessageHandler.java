package cn.itcast.server.handler;

import cn.itcast.message.LoginRequestMessage;
import cn.itcast.message.LoginResponseMessage;
import cn.itcast.server.service.UserServiceFactory;
import cn.itcast.server.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName LoginRequestMessageHandler
 * @Description TODO
 * @Author admin
 * @Date 2022/6/6 17:34
 * @Version 1.0
 **/
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestMessage loginRequestMessage) throws Exception {
        String username = loginRequestMessage.getUsername();
        String password = loginRequestMessage.getPassword();
        boolean login = UserServiceFactory.getUserService().login(username, password);
        LoginResponseMessage message;
        if(login){
            SessionFactory.getSession().bind(channelHandlerContext.channel(),username);
            message = new LoginResponseMessage(true,"登录成功");
        }else{
            message = new LoginResponseMessage(false,"用户名或密码不正确");
        }
        channelHandlerContext.writeAndFlush(message);
    }
}
