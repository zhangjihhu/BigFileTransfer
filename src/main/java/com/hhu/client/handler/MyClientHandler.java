package com.hhu.client.handler;

import com.hhu.codec.Codec;
import com.hhu.protocol.FilePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.stream.ChunkedFile;

import java.io.File;

public class MyClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println(msg);
		ByteBuf byteBuf = (ByteBuf) msg;
		FilePacket filePacket = (FilePacket) Codec.INSTANCE.decode(byteBuf);
		File file = filePacket.getFile();
		System.out.println("prepared send: " + file.getName());

		Channel channel = ctx.channel();
		channel.writeAndFlush(new ChunkedFile(filePacket.getFile()));
		// channel.writeAndFlush(new DefaultFileRegion(file, 0, file.length()));

	}
}
