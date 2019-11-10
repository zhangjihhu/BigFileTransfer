package com.hhu.client.handler;

import com.hhu.protocol.response.LoginResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

@ChannelHandler.Sharable
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket packet) throws Exception {
		System.out.println(new Date() + " " + packet.getId() + " " + packet.getName() + " 登陆成功");
	}
}
