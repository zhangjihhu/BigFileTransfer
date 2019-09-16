package com.hhu.session;

public class Session {

    private String nodeId;
    private String nodeName;

    public Session(String nodeId, String nodeName) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
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

    @Override
    public String toString() {
        return "Session{" +
                "nodeName='" + nodeName + '\'' +
                '}';
    }
}
