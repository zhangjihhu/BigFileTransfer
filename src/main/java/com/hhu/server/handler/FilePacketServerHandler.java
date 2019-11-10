package com.hhu.server.handler;

import com.hhu.protocol.FilePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.FileOutputStream;

@ChannelHandler.Sharable
public class FilePacketServerHandler extends SimpleChannelInboundHandler<FilePacket> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FilePacket packet) throws Exception {
		File file = packet.getFile();
		System.out.println("receive file from client: " + file.getName());
		FileReceiveServerHandler.fileLength = file.length();
		FileReceiveServerHandler.outputStream = new FileOutputStream(
				new File("./server-receive-" + file.getName())
		);
		packet.setACK(packet.getACK() + 1);
		ctx.writeAndFlush(packet);
	}
}
