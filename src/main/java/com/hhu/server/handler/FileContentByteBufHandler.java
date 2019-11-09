package com.hhu.server.handler;

import com.hhu.codec.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileContentByteBufHandler extends ChannelInboundHandlerAdapter {

	static FileOutputStream outputStream;

	static long fileLength;

	private static long readLength;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf byteBuf = (ByteBuf) msg;
		readLength += byteBuf.readableBytes();
		System.out.println(msg + " " + readLength);
		int type = byteBuf.getInt(0);
		if (type != Codec.TYPE) {
			byte[] bytes = new byte[byteBuf.readableBytes()];
			byteBuf.readBytes(bytes);
			outputStream.write(bytes);
			byteBuf.release();
			sendComplete(readLength);
		} else {
			super.channelRead(ctx, msg);
		}
	}

	private void sendComplete(long readLength) {
		if (readLength >= fileLength) {
			try {
				System.out.println("文件接收完成...");
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
