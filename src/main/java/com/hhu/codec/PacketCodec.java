package com.hhu.codec;

import com.hhu.protocol.FilePacket;
import com.hhu.protocol.Packet;
import com.hhu.protocol.request.JoinClusterRequestPacket;
import com.hhu.protocol.response.FileStartPacket;
import com.hhu.protocol.response.JoinClusterResponsePacket;
import com.hhu.serialize.Serializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import static com.hhu.protocol.command.Command.*;

public class PacketCodec {

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;

    public static final PacketCodec INSTANCE = new PacketCodec();

    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(FILE, FilePacket.class);
        packetTypeMap.put(FILE_START, FileStartPacket.class);
        packetTypeMap.put(JOIN_CLUSTER_REQUEST, JoinClusterRequestPacket.class);
        packetTypeMap.put(JOIN_CLUSTER_RESPONSE, JoinClusterResponsePacket.class);
    }


    public void encode(ByteBuf byteBuf, Packet packet) {
        //1.序列 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        //2.实际编码过程
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    public Packet decode(ByteBuf byteBuf) {

        byte command = byteBuf.readByte();
        //5.数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);

        Serializer serializer = Serializer.DEFAULT;

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;

    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }

}
