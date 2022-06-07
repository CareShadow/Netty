package cn.itcast.server.handler;

import cn.itcast.message.GroupJoinRequestMessage;
import cn.itcast.message.GroupJoinResponseMessage;
import cn.itcast.server.session.Group;
import cn.itcast.server.session.GroupSessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName GroupJoinMessageHandler
 * @Description TODO
 * @Author admin
 * @Date 2022/6/6 21:14
 * @Version 1.0
 **/
@ChannelHandler.Sharable
public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        String username = msg.getUsername();
        Group group = GroupSessionFactory.getGroupSession().joinMember(groupName, username);
        if(group==null){
            ctx.writeAndFlush(new GroupJoinResponseMessage(false,"不存在该"+groupName+"组"));
        }else{
            ctx.writeAndFlush(new GroupJoinResponseMessage(true,"加入"+groupName+"成功"));
        }
    }
}
