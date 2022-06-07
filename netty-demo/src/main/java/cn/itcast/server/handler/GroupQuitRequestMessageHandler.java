package cn.itcast.server.handler;

import cn.itcast.message.GroupQuitRequestMessage;
import cn.itcast.message.GroupQuitResponseMessage;
import cn.itcast.server.session.Group;
import cn.itcast.server.session.GroupSessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName GroupQuitRequestMessageHandler
 * @Description TODO
 * @Author admin
 * @Date 2022/6/6 21:20
 * @Version 1.0
 **/
@ChannelHandler.Sharable
public class GroupQuitRequestMessageHandler extends SimpleChannelInboundHandler<GroupQuitRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        String username = msg.getUsername();
        Group group = GroupSessionFactory.getGroupSession().removeMember(groupName, username);
        if(group==null){
            ctx.writeAndFlush(new GroupQuitResponseMessage(false, "不存在该"+groupName+"组"));
        }else{
            ctx.writeAndFlush(new GroupQuitResponseMessage(true,"退出"+groupName+"组成功"));
        }
    }
}
