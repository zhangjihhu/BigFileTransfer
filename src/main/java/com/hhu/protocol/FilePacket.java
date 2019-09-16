package com.hhu.protocol;

import java.io.File;

import static com.hhu.protocol.command.Command.FILE_PACKET;

public class FilePacket extends Packet {

    private File file;
    private int startPos;
    private int byteRead;
    private byte[] bytes;

    public FilePacket() {}

    public FilePacket(File file, int startPos, int byteRead, byte[] bytes) {
        this.file = file;
        this.startPos = startPos;
        this.byteRead = byteRead;
        this.bytes = bytes;
    }

    @Override
    public Byte getCommand() {
        return FILE_PACKET;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    public int getByteRead() {
        return byteRead;
    }

    public void setByteRead(int byteRead) {
        this.byteRead = byteRead;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
