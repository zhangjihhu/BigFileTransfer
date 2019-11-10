package com.hhu.codec;

import com.hhu.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

@ChannelHandler.Sharable
public class CodecHandler extends MessageToMessageCodec<ByteBuf, Object> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object o, List<Object> list) throws Exception {
		if (o instanceof Packet) {
			ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
			Codec.INSTANCE.encode(byteBuf, (Packet) o);
			list.add(byteBuf);
		} else {
			System.out.println("File ByteBuf need't encode");
			// ctx.writeAndFlush(o);
		}
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
		if (byteBuf.getInt(0) == Codec.TYPE) {
			System.out.println("decode FilePacket");
			list.add(Codec.INSTANCE.decode(byteBuf));
		} else {
			System.out.println("File ByteBuf need't decode");
			// list.add(byteBuf);
			ctx.fireChannelRead(byteBuf);
		}
	}
}
