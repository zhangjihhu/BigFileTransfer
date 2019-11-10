package com.hhu.server.console.impl;

import com.hhu.protocol.FilePacket;
import com.hhu.server.console.Console;
import com.hhu.util.SessionUtil;
import io.netty.channel.Channel;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SendFileConsole implements Console {
	@Override
	public void exec(Channel channel, Scanner scanner) {
		System.out.println("请输入文件路径：");
		String path = scanner.nextLine();

		File file = new File(path);
		FilePacket filePacket = new FilePacket(file);

		Map<String, Channel> channelMap = SessionUtil.getNodeIdChannelMap();
		for (Map.Entry<String, Channel> entry : channelMap.entrySet()) {
			entry.getValue().writeAndFlush(filePacket);
		}
	}
}
