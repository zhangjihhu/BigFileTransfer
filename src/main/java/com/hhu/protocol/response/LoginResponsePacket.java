package com.hhu.protocol.response;

import com.hhu.protocol.Packet;
import static com.hhu.protocol.command.Command.LOGIN_PACKET_RESPONSE;

public class LoginResponsePacket extends Packet {

	String id;
	String name;

	public LoginResponsePacket() {
	}

	public LoginResponsePacket(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public Byte getCommand() {
		return LOGIN_PACKET_RESPONSE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
