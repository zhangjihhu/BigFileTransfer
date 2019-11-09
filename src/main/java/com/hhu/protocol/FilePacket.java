package com.hhu.protocol;


import java.io.File;

import static com.hhu.protocol.command.Command.FILE_PACKET;

public class FilePacket extends Packet {

	File file;
	boolean complete;

	@Override
	public Byte getCommand() {
		return FILE_PACKET;
	}

	public FilePacket() {
	}

	public FilePacket(File file) {
		this.file = file;
	}

	public FilePacket(File file, boolean complete) {
		this.file = file;
		this.complete = complete;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}
}
