package cn.itcast.server.handler;

import cn.itcast.message.GroupMembersRequestMessage;
import cn.itcast.message.GroupMembersResponseMessage;
import cn.itcast.server.session.GroupSessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Collections;
import java.util.Set;

/**
 * @ClassName GroupMembersRequestMessageHandler
 * @Description TODO
 * @Author admin
 * @Date 2022/6/6 21:06
 * @Version 1.0
 **/
@ChannelHandler.Sharable
public class GroupMembersRequestMessageHandler extends SimpleChannelInboundHandler<GroupMembersRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMembersRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        // 获取成员列表
        Set<String> members = GroupSessionFactory.getGroupSession().getMembers(groupName);
        if(members.isEmpty()){
            ctx.writeAndFlush(new GroupMembersResponseMessage(null));
        }else{
            ctx.writeAndFlush(new GroupMembersResponseMessage(members));
        }
    }
}
