package com.hhu.client.handler;

import io.netty.channel.*;
import io.netty.handler.stream.ChunkedFile;

import java.io.File;

public class MyClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		channel.writeAndFlush(new ChunkedFile(new File("F:\\Project\\java\\BigFileTransfer\\pom.xml")));

		/*sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
			@Override
			public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
				if (total < 0) {
					System.err.println(future.channel() + " Transfer progress: " + progress);
				} else {
					System.err.println(future.channel() + " Transfer progress: " + progress + " / " + total);
				}
			}

			@Override
			public void operationComplete(ChannelProgressiveFuture future) throws Exception {
				System.err.println(future.channel() + " Transfer complete.");
			}
		});*/
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println(msg);
	}
}
