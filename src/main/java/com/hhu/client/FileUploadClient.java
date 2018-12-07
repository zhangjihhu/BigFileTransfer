package com.hhu.client;

import com.hhu.client.handler.FileUploadClientHandler;
import com.hhu.protocol.FilePacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.File;

public class FileUploadClient {

    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            port = Integer.valueOf(args[0]);
        }

        FilePacket filePacket = new FilePacket();
        File file = new File("C:\\Users\\zhangji\\Desktop\\data\\client\\a.txt");
        String fileMd5 = file.getName();
        filePacket.setFile(file);
        filePacket.setFile_md5(fileMd5);
        filePacket.setStartPos(0);
        new FileUploadClient().connect("127.0.0.1", port, filePacket);



    }

    public void connect(String host, int port, final FilePacket filePacket) {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new ObjectEncoder());
                        channel.pipeline().addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
                        channel.pipeline().addLast(new FileUploadClientHandler(filePacket));
                    }
                });
        ChannelFuture future = null;
        try {
            future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }


    }


}
