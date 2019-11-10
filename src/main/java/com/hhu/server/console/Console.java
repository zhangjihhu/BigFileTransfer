package com.hhu.server.console;

import io.netty.channel.Channel;

import java.util.Scanner;

public interface Console {

	void exec(Channel channel, Scanner scanner);

}
