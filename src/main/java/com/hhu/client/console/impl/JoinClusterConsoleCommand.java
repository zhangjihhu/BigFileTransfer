package com.hhu.client.console.impl;

import com.hhu.client.console.ConsoleCommand;
import com.hhu.protocol.request.JoinClusterRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author zhangji
 */
public class JoinClusterConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        JoinClusterRequestPacket joinCluserPacket = new JoinClusterRequestPacket();

        System.out.println("输入节点名加入集群：");
        String nodeName = scanner.nextLine();
        joinCluserPacket.setNodeName(nodeName);

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
