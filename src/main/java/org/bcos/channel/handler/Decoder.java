package org.bcos.channel.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class Decoder extends ByteToMessageDecoder {
	private static Logger logger = LoggerFactory.getLogger(Decoder.class);
	
	private Integer dataLength = 0;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		logger.debug("接收数据:" + in.readableBytes());
		
		while (true) {
			if (dataLength > 0) {
				if (in.readableBytes() < dataLength - 4) {
					return;
				}

				Short type = in.readShort();
				ByteBuf data = in.readBytes(dataLength - 6);

				Message message = new Message();
				message.setLength(dataLength);
				message.setType(type);
				message.setData(data.array());

				logger.debug("新消息: " + String.valueOf(message.getLength()) + "," + String.valueOf(message.getType()));
				out.add(message);
				dataLength = 0;
			} else {
				if (in.readableBytes() < 4) {
					return;
				}

				dataLength = in.readInt();
				logger.debug("数据长度" + String.valueOf(dataLength));
			}
		}
	}
}
