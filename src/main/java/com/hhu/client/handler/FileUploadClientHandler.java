package com.hhu.client.handler;

import com.hhu.protocol.FilePacket;
import com.hhu.protocol.response.FileStartPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.RandomAccessFile;

@ChannelHandler.Sharable
public class FileUploadClientHandler extends SimpleChannelInboundHandler<FileStartPacket> {

    private int byteRead;
    private volatile int start = 0;
    private volatile int lastLength = 0;
    public RandomAccessFile randomAccessFile;
    private FilePacket filePacket;

    public FileUploadClientHandler() {
        filePacket = new FilePacket();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileStartPacket fileStartPacket) throws Exception {
            start = fileStartPacket.getStart();
            if (start != -1) {
                randomAccessFile = new RandomAccessFile(fileStartPacket.getFile(), "r");
                randomAccessFile.seek(start);
                System.out.println("块儿长度：" + (randomAccessFile.length()/10));
                System.out.println("剩余长度：" + (randomAccessFile.length()-start));
                int a = (int) (randomAccessFile.length() - start);
                int lastLength = (int) (randomAccessFile.length() / 10);
                // lastLength = 500;
                if (a < lastLength) {
                    lastLength = a;
                }

                byte[] bytes = new byte[lastLength];

                if ((byteRead = randomAccessFile.read(bytes)) != -1 && (randomAccessFile.length()-start) > 0) {
                    System.out.println("byte长度：" + bytes.length);
                    System.out.println("-----------------" + bytes.length);
                    filePacket.setFile(fileStartPacket.getFile());
                    filePacket.setFile_md5(fileStartPacket.getFile().getName());
                    filePacket.setEndPos(byteRead);
                    filePacket.setBytes(bytes);
                    ctx.writeAndFlush(filePacket);
                } else {
                    randomAccessFile.close();
                    System.out.println("文件已读完------" + byteRead);
                }

            }
    }


}










