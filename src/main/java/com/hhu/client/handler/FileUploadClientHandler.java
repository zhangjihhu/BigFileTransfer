package com.hhu.client.handler;

import com.hhu.protocol.FilePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.RandomAccessFile;

@ChannelHandler.Sharable
public class FileUploadClientHandler extends SimpleChannelInboundHandler<FilePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FilePacket filePacket) throws Exception {
        int start = filePacket.getStartPos();
        if (start > 0) {
            RandomAccessFile raf = new RandomAccessFile(filePacket.getFile(), "r");
            raf.seek(start);
            // 文件剩余长度
            int remainLen = (int) (raf.length() - start);
            // 每个batch长度
            int batch = (int) raf.length() > 0 ? (int) (raf.length() / 10) : Integer.MAX_VALUE / 10;
            if (remainLen < batch) {
                batch = remainLen;
            }
            byte[] bytes = new byte[batch];
            int byteRead;
            if ((byteRead = raf.read(bytes)) != -1 && (raf.length() - start) > 0) {
                FilePacket packet = new FilePacket();
                packet.setFile(filePacket.getFile());
                packet.setStartPos(start);
                packet.setByteRead(byteRead);
                packet.setBytes(bytes);
                ctx.writeAndFlush(packet);
            } else {
                System.out.println("文件发送完毕");
            }
            raf.close();
        } else {
            System.out.println("文件发送完毕");
        }
    }

}










