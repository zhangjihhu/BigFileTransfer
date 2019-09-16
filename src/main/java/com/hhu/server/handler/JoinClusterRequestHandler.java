package com.hhu.server.handler;

import com.hhu.protocol.request.JoinClusterRequestPacket;
import com.hhu.protocol.response.JoinClusterResponsePacket;
import com.hhu.session.Session;
import com.hhu.util.IDUtil;
import com.hhu.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class JoinClusterRequestHandler extends SimpleChannelInboundHandler<JoinClusterRequestPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinClusterRequestPacket joinClusterRequestPacket) throws Exception {

        JoinClusterResponsePacket joinClusterResponsePacket = new JoinClusterResponsePacket();
        joinClusterResponsePacket.setSuccess(true);
        String nodeId = IDUtil.randomId();
        joinClusterResponsePacket.setNodeId(nodeId);
        joinClusterResponsePacket.setNodeName(joinClusterRequestPacket.getNodeName());
        System.out.println("[" + joinClusterRequestPacket.getNodeName() + "]" + ":登陆成功");
        SessionUtil.bindSession(new Session(nodeId, joinClusterRequestPacket.getNodeName()), ctx.channel());

        ctx.channel().writeAndFlush(joinClusterResponsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
