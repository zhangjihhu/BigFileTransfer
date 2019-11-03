package com.hhu.protocol;


import java.io.File;

import static com.hhu.protocol.command.Command.FILE_PACKET;

public class FilePacket extends Packet {

	File file;

	@Override
	public Byte getCommand() {
		return FILE_PACKET;
	}

	public FilePacket() {
	}

	public FilePacket(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
