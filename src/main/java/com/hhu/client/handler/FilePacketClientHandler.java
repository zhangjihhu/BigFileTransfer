package com.hhu.client.handler;

import com.hhu.codec.Codec;
import com.hhu.protocol.FilePacket;
import com.hhu.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;

import java.io.File;
import java.io.RandomAccessFile;

public class FilePacketClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf byteBuf = (ByteBuf) msg;
		System.out.println(msg);
		int type = byteBuf.getInt(0);
		if (type == Codec.TYPE) {
			Packet packet = Codec.INSTANCE.decode(byteBuf);
			if (packet instanceof FilePacket) {
				FilePacket filePacket = (FilePacket) packet;
				File file = filePacket.getFile();
				DefaultFileRegion fileRegion = new DefaultFileRegion(file, 0, file.length());
				ctx.writeAndFlush(fileRegion).addListener(future -> {
					if (future.isSuccess()) {
						System.out.println("发送完成...");
					}
				});
			} else {
				super.channelRead(ctx, msg);
			}
		}
	}

	// @Override
	// protected void channelRead0(ChannelHandlerContext ctx, FilePacket packet) throws Exception {
	// 	System.out.println("prepare sending file: " + packet.getFile().getName());
	//
	// 	File file = packet.getFile();
	//
	// 	// channel.writeAndFlush(new ChunkedFile(file));
	// 	DefaultFileRegion fileRegion = new DefaultFileRegion(file, 0, file.length());
	// 	ctx.writeAndFlush(fileRegion).addListener(future -> {
	// 		if (future.isSuccess()) {
	// 			System.out.println("发送完成...");
	// 		}
	// 	});
	// }
}
