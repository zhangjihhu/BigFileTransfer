package com.hhu.server.handler;

import com.hhu.protocol.FilePacket;
import com.hhu.protocol.response.FileStartPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.RandomAccessFile;

@ChannelHandler.Sharable
public class FileUploadServerHandler extends SimpleChannelInboundHandler<FilePacket> {

    private int byteRead;
    private volatile int start = 0;
    private String file_dir = "C:\\Users\\zhangji\\Desktop\\data\\server";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FilePacket filePacket) throws Exception {
        System.out.println("=============== server channelRead0");
        byte[] bytes = filePacket.getBytes();
        byteRead = filePacket.getEndPos();
        String md5 = filePacket.getFile_md5();
        String path = file_dir + File.separator + md5;
        File file = new File(path);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        randomAccessFile.seek(start);
        randomAccessFile.write(bytes);
        start = start + byteRead;
        if (byteRead > 0) {
            FileStartPacket fileStartPacket = new FileStartPacket();
            fileStartPacket.setStart(start);
            ctx.channel().writeAndFlush(fileStartPacket);
        } else {
            randomAccessFile.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
