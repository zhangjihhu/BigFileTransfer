package com.hhu.client;


import com.hhu.client.console.SendFileConsole;
import com.hhu.client.handler.FilePacketClientHandler;
import com.hhu.client.handler.MyClientHandler;
import com.hhu.codec.CodecHandler;
import com.hhu.codec.DecodeHandler;
import com.hhu.codec.EncodeHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.Scanner;

public class Client {

	private static final String HOST = System.getProperty("host", "127.0.0.1");

	private static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));

	public static void main(String[] args) throws InterruptedException {

		Bootstrap bootstrap = new Bootstrap();

		NioEventLoopGroup group = new NioEventLoopGroup();

		bootstrap.group(group)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<NioSocketChannel>() {
					@Override
					protected void initChannel(NioSocketChannel channel) throws Exception {
						ChannelPipeline pipeline = channel.pipeline();
						pipeline.addLast(new FilePacketClientHandler());
						pipeline.addLast(new DecodeHandler());
						pipeline.addLast(new EncodeHandler());
						pipeline.addLast(new ChunkedWriteHandler());
						// pipeline.addLast(new MyClientHandler());
					}
				});

		ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
		if (future.isSuccess()) {
			System.out.println("连接服务器成功");
			Channel channel = future.channel();
			console(channel);
		} else {
			System.out.println("连接服务器失败");
		}

		future.channel().closeFuture().sync();
	}

	private static void console(Channel channel) {
		new Thread(() -> {
			while (!Thread.interrupted()) {
				SendFileConsole.exec(channel);
			}
		}).start();
	}

}
