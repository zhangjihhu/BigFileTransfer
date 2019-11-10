package com.hhu.server.handler;

import com.hhu.protocol.request.LoginPacket;
import com.hhu.protocol.response.LoginResponsePacket;
import com.hhu.protocol.session.Session;
import com.hhu.util.IDUtil;
import com.hhu.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

@ChannelHandler.Sharable
public class JoinClusterRequestHandler extends SimpleChannelInboundHandler<LoginPacket> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LoginPacket loginPacket) throws Exception {
		String id = IDUtil.randomId();
		System.out.println(new Date() + " [" + loginPacket.getName() + "-" + id + "]加入集群");
		SessionUtil.bindSession(new Session(id, loginPacket.getName()), ctx.channel());

		ctx.writeAndFlush(new LoginResponsePacket(id, loginPacket.getName()));
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		SessionUtil.unBindSession(ctx.channel());
	}
}
