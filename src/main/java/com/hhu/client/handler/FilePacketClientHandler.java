package com.hhu.client.handler;

import com.hhu.protocol.FilePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.FileOutputStream;

public class FilePacketClientHandler extends SimpleChannelInboundHandler<FilePacket> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FilePacket packet) throws Exception {
		File file = packet.getFile();
		System.out.println("receive file from server: " + file.getName());
		FileReceiveClientHandler.fileLength = file.length();
		FileReceiveClientHandler.outputStream = new FileOutputStream(
				new File("./client-receive-" + file.getName())
		);
		packet.setACK(packet.getACK() + 1);
		ctx.writeAndFlush(packet);
	}
}
