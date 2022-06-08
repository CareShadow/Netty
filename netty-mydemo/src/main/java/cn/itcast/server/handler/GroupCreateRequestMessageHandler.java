package cn.itcast.server.handler;

import cn.itcast.message.GroupCreateRequestMessage;
import cn.itcast.message.GroupCreateResponseMessage;
import cn.itcast.server.session.Group;
import cn.itcast.server.session.GroupSession;
import cn.itcast.server.session.GroupSessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;
import java.util.Set;

/**
 * @ClassName GroupCreateRequestMessageHandler
 * @Description TODO
 * @Author admin
 * @Date 2022/6/6 19:23
 * @Version 1.0
 **/
@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Set<String> members = msg.getMembers();
        // 获取群管理器
        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        // 先获取再创建
        Group group = groupSession.createGroup(groupName, members);
        if(group==null){
            // 发送成功消息
            ctx.writeAndFlush(new GroupCreateResponseMessage(true, groupName+"创建成功"));
            // 发送拉群消息
            List<Channel> channels = groupSession.getMembersChannel(groupName);
            for(Channel channel :  channels){
                // 跳过自己
                if(ctx.channel()==channel) continue;
                channel.writeAndFlush(new GroupCreateResponseMessage(true,"您已被拉入"+groupName));
            }
        }else{
            ctx.writeAndFlush(new GroupCreateResponseMessage(true, groupName+"已经存在"));
        }
    }
}
