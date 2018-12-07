package com.hhu.protocol;

import lombok.Data;

@Data
public abstract class Packet {

    private Byte type;

    public abstract Byte getCommand();

}
