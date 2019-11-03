package com.hhu.client.console;

import com.hhu.codec.Codec;
import com.hhu.protocol.FilePacket;
import io.netty.channel.Channel;

import java.io.File;
import java.util.Scanner;

public class SendFileConsole {

	public static void exec(Channel channel) {
		Scanner sc = new Scanner(System.in);
		System.out.println("please input the file path: ");
		String path = sc.nextLine();
		File file = new File(path);
		FilePacket filePacket = new FilePacket(file);
		channel.writeAndFlush(Codec.INSTANCE.encode(channel.alloc().ioBuffer(), filePacket));

	}

}
