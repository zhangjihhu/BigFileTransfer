package com.hhu.protocol.response;

import com.hhu.protocol.Packet;
import lombok.Data;

import static com.hhu.protocol.command.Command.JOIN_CLUSTER_RESPONSE;

@Data
public class JoinClusterResponsePacket extends Packet {

    private String nodeId;
    private String nodeName;
    private boolean sucess;

    @Override
    public Byte getCommand() {
        return JOIN_CLUSTER_RESPONSE;
    }
}
