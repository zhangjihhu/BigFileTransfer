package com.hhu.codec;

import com.hhu.protocol.FilePacket;
import com.hhu.protocol.Packet;
import com.hhu.protocol.request.LoginPacket;
import com.hhu.protocol.response.LoginResponsePacket;
import com.hhu.protocol.serilizer.Serilizer;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import static com.hhu.protocol.command.Command.*;

public class Codec {

	public static final int TYPE = 0x12345678;

	private final Map<Byte, Class<? extends Packet>> packetTypeMap;

	public static Codec INSTANCE = new Codec();

	private Codec() {
		packetTypeMap = new HashMap<>();
		packetTypeMap.put(FILE_PACKET, FilePacket.class);
		packetTypeMap.put(LOGIN_PACKET_REQUEST, LoginPacket.class);
		packetTypeMap.put(LOGIN_PACKET_RESPONSE, LoginResponsePacket.class);
	}

	public void encode(ByteBuf byteBuf, Packet packet) {
		byte[] bytes = Serilizer.DEFAULT.serilize(packet);
		byteBuf.writeInt(TYPE);
		byteBuf.writeByte(packet.getCommand());
		byteBuf.writeInt(bytes.length);
		byteBuf.writeBytes(bytes);
		// return byteBuf;
	}

	public Packet decode(ByteBuf byteBuf) {
		byteBuf.readInt();
		Byte command = byteBuf.readByte();
		int len = byteBuf.readInt();
		byte[] bytes = new byte[len];
		byteBuf.readBytes(bytes);

		Class clazz = packetTypeMap.get(command);
		if (clazz == null) {
			throw new NullPointerException("解析失败，没有该类型的数据包");
		}

		return (Packet) Serilizer.DEFAULT.deSerilize(bytes, clazz);

	}



}
