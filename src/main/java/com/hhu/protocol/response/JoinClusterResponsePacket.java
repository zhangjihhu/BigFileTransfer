package com.hhu.protocol.response;

import com.hhu.protocol.Packet;

import static com.hhu.protocol.command.Command.JOIN_CLUSTER_RESPONSE;

public class JoinClusterResponsePacket extends Packet {

    private String nodeId;
    private String nodeName;
    private boolean success;

    @Override
    public Byte getCommand() {
        return JOIN_CLUSTER_RESPONSE;
    }

    public JoinClusterResponsePacket() {}

    public JoinClusterResponsePacket(String nodeId, String nodeName, boolean sucess) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.success = sucess;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
