package com.hhu.server.handler;

import com.hhu.protocol.FilePacket;
import com.hhu.protocol.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.FileOutputStream;

public class FilePacketServerHandler extends SimpleChannelInboundHandler<FilePacket> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FilePacket packet) throws Exception {
		if (packet.isComplete()) {
			System.out.println("文件接收完毕");
			return;
		}
		System.out.println("receive file: " + packet.getFile().getName());
		FileContentByteBufHandler.fileLength = packet.getFile().length();
		FileContentByteBufHandler.outputStream = new FileOutputStream(
				new File("./receive-" + packet.getFile().getName()));
		ctx.channel().writeAndFlush(packet);
	}
}
