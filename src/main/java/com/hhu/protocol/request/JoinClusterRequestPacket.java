package com.hhu.protocol.request;

import com.hhu.protocol.Packet;
import lombok.Data;

import static com.hhu.protocol.command.Command.JOIN_CLUSTER_REQUEST;

@Data
public class JoinClusterRequestPacket extends Packet {

    String nodeName;

    @Override
    public Byte getCommand() {
        return JOIN_CLUSTER_REQUEST;
    }
}
