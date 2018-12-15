package com.hhu.client.console;

import com.hhu.protocol.request.JoinClusterRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class JoinConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        JoinClusterRequestPacket joinCluserPacket = new JoinClusterRequestPacket();

        System.out.println("输入节点名加入集群：");

        joinCluserPacket.setNodeName(scanner.nextLine());

        channel.writeAndFlush(joinCluserPacket);
        waitForLoginResponse();


    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {

        }
    }

}
