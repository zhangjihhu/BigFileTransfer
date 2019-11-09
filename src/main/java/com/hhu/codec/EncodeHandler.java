package com.hhu.codec;

import com.hhu.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class EncodeHandler extends MessageToByteEncoder {
	@Override
	protected void encode(ChannelHandlerContext ctx, Object o, ByteBuf byteBuf) throws Exception {
		System.out.println("encode: " + o);
		// if (o instanceof Packet) {
		// 	Codec.INSTANCE.encode(byteBuf, (Packet) o);
		// } else {
		// 	ctx.writeAndFlush(o);
		// }
		if (o instanceof ByteBuf || o instanceof DefaultFileRegion) {
			ctx.writeAndFlush(o);
		} else {
			Codec.INSTANCE.encode(byteBuf, (Packet) o);
		}
	}
}
