package com.hhu.server.handler;

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
		file = new File("./receive.xml");
		outputStream = new FileOutputStream(file);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf byteBuf = (ByteBuf) msg;
		byte[] bytes = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(bytes);
		outputStream.write(bytes);
		System.out.println(msg);
		byteBuf.release();
	}
}
