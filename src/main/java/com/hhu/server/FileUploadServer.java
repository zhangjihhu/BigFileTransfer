package com.hhu.server;

import com.hhu.codec.PacketDecoder;
import com.hhu.codec.PacketEncoder;
import com.hhu.server.handler.FileUploadServerHandler;
import com.hhu.server.handler.JoinClusterRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class FileUploadServer {

    private static final int PORT = 8000;

    public static void main(String[] args) {

        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap
                .group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 1, 4));
                        ch.pipeline().addLast(new PacketEncoder());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new JoinClusterRequestHandler());
                        ch.pipeline().addLast(new FileUploadServerHandler());

                    }
                });

        bind(bootstrap, PORT);


    }

    private static void bind(ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功");
            } else {
                System.err.println("端口[" + port + "]绑定失败");
                // bind(serverBootstrap, port + 1);
            }
        });
    }


}
