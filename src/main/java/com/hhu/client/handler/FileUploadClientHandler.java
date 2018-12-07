package com.hhu.client.handler;

import com.hhu.protocol.FilePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.RandomAccessFile;

@ChannelHandler.Sharable
public class FileUploadClientHandler extends ChannelInboundHandlerAdapter {

    private int byteRead;
    private volatile int start = 0;
    private volatile int lastLength = 0;
    public RandomAccessFile randomAccessFile;
    private FilePacket filePacket;

    public FileUploadClientHandler(FilePacket filePacket) {
        if (filePacket.getFile().exists()) {
            if (!filePacket.getFile().isFile()) {
                System.out.println("Not a file:" + filePacket.getFile());
            }
        }
        this.filePacket = filePacket;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        randomAccessFile = new RandomAccessFile(filePacket.getFile(), "r");
        randomAccessFile.seek(filePacket.getStartPos());
        lastLength = (int) randomAccessFile.length() / 10;
        byte[] bytes = new byte[lastLength];

        if ((byteRead = randomAccessFile.read(bytes)) != -1) {
            filePacket.setEndPos(byteRead);
            filePacket.setBytes(bytes);
            ctx.writeAndFlush(filePacket);
        } else {
            System.out.println("文件已读完");
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Integer) {
            start = (Integer) msg;
            if (start != -1) {
                randomAccessFile = new RandomAccessFile(filePacket.getFile(), "r");
                randomAccessFile.seek(start);
                System.out.println("块儿长度：" + (randomAccessFile.length()/10));
                System.out.println("剩余长度：" + (randomAccessFile.length()-start));
                int a = (int) (randomAccessFile.length() - start);
                int lastLength = (int) (randomAccessFile.length() / 10);
                if (a < lastLength) {
                    lastLength = a;
                }

                byte[] bytes = new byte[lastLength];

                if ((byteRead = randomAccessFile.read(bytes)) != -1 && (randomAccessFile.length()-start) > 0) {
                    System.out.println("byte长度：" + bytes.length);
                    System.out.println("-----------------" + bytes.length);
                    filePacket.setEndPos(byteRead);
                    filePacket.setBytes(bytes);
                    ctx.writeAndFlush(filePacket);
                } else {
                    randomAccessFile.close();
                    ctx.close();
                    System.out.println("文件已读完------" + byteRead);
                }

            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}










