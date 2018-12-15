package com.hhu.session;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Session {

    private String nodeId;
    private String nodeName;

    public Session(String nodeId, String nodeName) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
    }

    @Override
    public String toString() {
        return "Session{" +
                "nodeName='" + nodeName + '\'' +
                '}';
    }
}
