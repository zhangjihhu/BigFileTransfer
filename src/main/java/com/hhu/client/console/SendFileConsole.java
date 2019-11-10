package com.hhu.client.console;

import com.hhu.protocol.FilePacket;
import io.netty.channel.Channel;

import java.io.File;
import java.util.Scanner;

public class SendFileConsole {

	public static void exec(Channel channel) {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入文件路径：");
		String path = sc.nextLine();
		File file = new File(path);
		FilePacket filePacket = new FilePacket(file);
		channel.writeAndFlush(filePacket);

	}

}
