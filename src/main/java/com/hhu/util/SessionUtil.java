package com.hhu.util;

import com.hhu.attribute.Attributes;
import com.hhu.session.Session;
import io.netty.channel.Channel;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {

    private static final Map<String, Channel> nodeIdChannelMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        nodeIdChannelMap.put(session.getNodeId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            Session session = getSession(channel);
            nodeIdChannelMap.remove(session.getNodeId());
            channel.attr(Attributes.SESSION).set(null);
            System.out.println(new Date() + " : " +session + " 退出集群");
        }
    }

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String nodeId) {
        return nodeIdChannelMap.get(nodeId);
    }

}
