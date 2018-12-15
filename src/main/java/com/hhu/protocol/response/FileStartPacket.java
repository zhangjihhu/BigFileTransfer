package com.hhu.protocol.response;

import com.hhu.protocol.Packet;
import lombok.Data;

import java.io.File;

import static com.hhu.protocol.command.Command.FILE_START;

@Data
public class FileStartPacket extends Packet {

    private int start;

    private File file;

    @Override
    public Byte getCommand() {
        return FILE_START;
    }
}
