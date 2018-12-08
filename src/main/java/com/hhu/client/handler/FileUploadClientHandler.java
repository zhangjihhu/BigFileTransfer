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
        // lastLength = 500;
        byte[] bytes = new byte[lastLength];

        if ((byteRead = randomAccessFile.read(bytes)) != -1) {
            filePacket.setEndPos(byteRead);
            filePacket.setBytes(bytes);
            System.out.println("----------" + filePacket.getFile_md5());
            ctx.writeAndFlush(filePacket);
            System.out.println("---------- client had writeAndFlush");
        } else {
            System.out.println("文件已读完");
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileStartPacket fileStartPacket) throws Exception {
            start = fileStartPacket.getStart();
            if (start != -1) {
                randomAccessFile = new RandomAccessFile(filePacket.getFile(), "r");
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

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}










