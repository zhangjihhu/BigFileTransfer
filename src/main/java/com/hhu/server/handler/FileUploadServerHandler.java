package com.hhu.server.handler;

import com.hhu.protocol.FilePacket;
import com.hhu.util.FileUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.RandomAccessFile;

@ChannelHandler.Sharable
public class FileUploadServerHandler extends SimpleChannelInboundHandler<FilePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FilePacket filePacket) throws Exception {
        String fileName = filePacket.getFile().getName();
        String parentPath = FileUtil.getProjectParentPath();
        String path = parentPath + "\\client\\";
        File fileDir = new File(path);
        if (!fileDir.exists()) {fileDir.mkdirs();}
        path += fileName;
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("receive file: " + fileName);
        }

        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        // 将内容写到对应的位置
        byte[] bytes = filePacket.getBytes();
        int byteRead = filePacket.getByteRead();
        int start = filePacket.getStartPos();
        raf.seek(start);
        raf.write(bytes);
        start = start + byteRead;
        if (byteRead > 0) {
            FilePacket packet = new FilePacket();
            packet.setStartPos(start);
            packet.setFile(filePacket.getFile());
            ctx.writeAndFlush(packet);
        }
        raf.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
