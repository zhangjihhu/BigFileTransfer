package com.hhu.protocol.request;

import com.hhu.protocol.Packet;

import static com.hhu.protocol.command.Command.JOIN_CLUSTER_REQUEST;

public class JoinClusterRequestPacket extends Packet {

    String nodeName;

    @Override
    public Byte getCommand() {
        return JOIN_CLUSTER_REQUEST;
    }

    public JoinClusterRequestPacket() {}

    public JoinClusterRequestPacket(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
