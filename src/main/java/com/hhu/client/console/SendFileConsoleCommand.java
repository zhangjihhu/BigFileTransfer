package com.hhu.client.console;

import com.hhu.protocol.FilePacket;
import io.netty.channel.Channel;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class SendFileConsoleCommand implements ConsoleCommand {

    private int byteRead;
    private volatile int start = 0;
    private volatile int lastLength = 0;
    public RandomAccessFile randomAccessFile;

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入文件路径:");
        String filePath = scanner.next();

        // filePath.replaceAll("/", File.separator);
        System.out.println("filePath" + filePath);
        FilePacket filePacket = new FilePacket();
        File file = new File(filePath);
        String fileMd5 = file.getName();
        filePacket.setFile(file);
        filePacket.setFile_md5(fileMd5);
        filePacket.setStartPos(0);

        try {
            fileChannelActive(channel, filePacket);
        } catch (Exception e) {
            System.out.println("SendFileConsoleCommand 发送失败");
            e.printStackTrace();
        }

    }


    public void fileChannelActive(Channel channel, FilePacket filePacket) throws Exception {

        if (filePacket.getFile().exists()) {
            if (!filePacket.getFile().isFile()) {
                System.out.println("Not a file:" + filePacket.getFile());
            }
        }

        randomAccessFile = new RandomAccessFile(filePacket.getFile(), "r");
        // randomAccessFile.seek(filePacket.getStartPos());
        randomAccessFile.seek(0);
        // lastLength = (int) randomAccessFile.length() / 10;
        // lastLength = 500;
        lastLength= Integer.MAX_VALUE / 10;
        byte[] bytes = new byte[lastLength];

        if ((byteRead = randomAccessFile.read(bytes)) != -1) {
            filePacket.setEndPos(byteRead);
            filePacket.setBytes(bytes);
            System.out.println("============" + filePacket.getFile_md5());
            channel.writeAndFlush(filePacket);
            System.out.println("============ client had writeAndFlush");
        } else {
            System.out.println("文件已读完");
        }
    }

}
