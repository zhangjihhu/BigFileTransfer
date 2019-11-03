package com.hhu.server;

import com.hhu.server.handler.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.stream.ChunkedWriteHandler;

public class Server {

	private static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));

	public static void main(String[] args) throws InterruptedException {
		ServerBootstrap bootstrap = new ServerBootstrap();

		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();

		bootstrap.group(boss, worker)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.option(ChannelOption.TCP_NODELAY, true)
				.childHandler(new ChannelInitializer<NioSocketChannel>() {
					@Override
					protected void initChannel(NioSocketChannel channel) throws Exception {
						ChannelPipeline pipeline = channel.pipeline();
						pipeline.addLast("streamer", new ChunkedWriteHandler());
						pipeline.addLast("handler", new MyServerHandler());
					}
				});

		ChannelFuture future = bootstrap.bind(PORT).sync();
		if (future.isSuccess()) {
			System.out.println("端口绑定成功");
		} else {
			System.out.println("端口绑定失败");
		}

		future.channel().closeFuture().sync();


	}


}
