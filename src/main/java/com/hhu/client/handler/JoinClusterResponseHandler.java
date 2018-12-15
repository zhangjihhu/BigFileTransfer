package com.hhu.client.handler;

import com.hhu.protocol.response.JoinClusterResponsePacket;
import com.hhu.session.Session;
import com.hhu.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

@ChannelHandler.Sharable
public class JoinClusterResponseHandler extends SimpleChannelInboundHandler<JoinClusterResponsePacket> {



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinClusterResponsePacket joinClusterResponsePacket) throws Exception {
        String nodeId = joinClusterResponsePacket.getNodeId();
        String nodeName = joinClusterResponsePacket.getNodeName();

        if (joinClusterResponsePacket.isSucess()) {
            System.out.println("[" + nodeName + "] 加入集群成功，nodeId: " + nodeId);
            SessionUtil.bindSession(new Session(nodeId, nodeName), ctx.channel());
        } else {
            System.out.println("[" + nodeName + "] 加入集群失败");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "连接被关闭");
    }
}
