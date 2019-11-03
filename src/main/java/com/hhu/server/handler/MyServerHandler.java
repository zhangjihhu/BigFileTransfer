package com.hhu.server.handler;

import com.hhu.codec.Codec;
import com.hhu.protocol.FilePacket;
import com.hhu.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.stream.ChunkedFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MyServerHandler extends ChannelInboundHandlerAdapter {

	File file;
	FileOutputStream outputStream;

	public MyServerHandler() throws FileNotFoundException {

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf byteBuf = (ByteBuf) msg;
		int type = byteBuf.getInt(0);
		if (type != Codec.TYPE) {
			byte[] bytes = new byte[byteBuf.readableBytes()];
			byteBuf.readBytes(bytes);
			outputStream.write(bytes);
			System.out.println(msg);
			byteBuf.release();
		} else {
			Packet packet = Codec.INSTANCE.decode(byteBuf);
			if (packet instanceof FilePacket) {
				FilePacket filePacket = (FilePacket) packet;
				System.out.println("receive file from client: " + filePacket.getFile().getName());
				outputStream = new FileOutputStream(new File("./receive-" + filePacket.getFile().getName()));
				ctx.channel().writeAndFlush(Codec.INSTANCE.encode(ctx.channel().alloc().ioBuffer(), filePacket));
			}
		}

	}
}
