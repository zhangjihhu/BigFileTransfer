package com.hhu.protocol.response;

import com.hhu.protocol.Packet;
import lombok.Data;

import static com.hhu.protocol.command.Command.FILE_START;

@Data
public class FileStartPacket extends Packet {

    private int start;

    @Override
    public Byte getCommand() {
        return FILE_START;
    }
}
