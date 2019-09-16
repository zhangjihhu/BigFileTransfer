package com.hhu.client.console.impl;

import com.hhu.client.console.ConsoleCommand;
import com.hhu.protocol.FilePacket;
import io.netty.channel.Channel;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 * @author zhangji
 */
public class SendFileConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入文件路径:");
        String path = scanner.nextLine();
        path = path.replace("\\", "\\\\");

        try {
            sendPacket(path, channel);
        } catch (Exception e) {
            System.out.println("文件发送失败");
        }
    }

    private void sendPacket(String path, Channel channel) throws Exception {
        File file = new File(path);
        FilePacket filePacket = new FilePacket();
        filePacket.setFile(file);
        filePacket.setStartPos(0);
        fileChannelActive(channel, filePacket);

    }


    private void fileChannelActive(Channel channel, FilePacket filePacket) throws Exception {
        if (filePacket.getFile().exists()) {
            if (!filePacket.getFile().isFile()) {
                System.out.println("Not a file: " + filePacket.getFile());
            }
        }

        RandomAccessFile raf = new RandomAccessFile(filePacket.getFile(), "r");
        raf.seek(0);
        int lastLength = (int) raf.length() > 0 ? (int) (raf.length() / 10) : Integer.MAX_VALUE / 100;
        byte[] bytes = new byte[lastLength];

        int byteRead;
        if ((byteRead = raf.read(bytes)) != -1) {
            filePacket.setByteRead(byteRead);
            filePacket.setBytes(bytes);
            channel.writeAndFlush(filePacket);
        } else {
            System.out.println("文件已读完");
        }
    }

}
