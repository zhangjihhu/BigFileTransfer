package com.hhu.util;

import com.hhu.protocol.attribute.Attributes;
import com.hhu.protocol.session.Session;
import io.netty.channel.Channel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SessionUtil {

	private static final Map<String, Channel> NODE_ID_CHANNEL_MAP = new HashMap<>();

	public static void bindSession(Session session, Channel channel) {
		NODE_ID_CHANNEL_MAP.put(session.getNodeId(), channel);
		channel.attr(Attributes.SESSION).set(session);
	}

	public static void unBindSession(Channel channel) {
		if (hasLogin(channel)) {
			Session session = getSession(channel);
			NODE_ID_CHANNEL_MAP.remove(session.getNodeId());
			channel.attr(Attributes.SESSION).set(null);
			System.out.println(new Date() + " " + session + "退出集群");
		}
	}

	private static boolean hasLogin(Channel channel) {
		return channel.hasAttr(Attributes.SESSION);
	}

	private static Session getSession(Channel channel) {
		return channel.attr(Attributes.SESSION).get();
	}

	public static Map getNodeIdChannelMap() {
		return NODE_ID_CHANNEL_MAP;
	}
}
