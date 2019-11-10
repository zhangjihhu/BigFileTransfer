package com.hhu.codec;

import com.hhu.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class EncodeHandler extends MessageToByteEncoder {
	@Override
	protected void encode(ChannelHandlerContext ctx, Object o, ByteBuf byteBuf) throws Exception {
		Codec.INSTANCE.encode(byteBuf, (Packet) o);
	}
}
