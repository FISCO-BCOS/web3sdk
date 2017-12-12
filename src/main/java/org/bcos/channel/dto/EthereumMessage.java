package org.bcos.channel.dto;

import org.bcos.channel.handler.Message;
import io.netty.buffer.ByteBuf;

public class EthereumMessage extends Message {
	private static final long serialVersionUID = 3763237749437810546L;
	
	public EthereumMessage() {
		
	}
	
	public EthereumMessage(Message msg) {
		length = msg.getLength();
		type = msg.getType();
		seq = msg.getSeq();
		result = msg.getResult();
	}

	@Override
	public void readExtra(ByteBuf in) {
		data = new byte[length - Message.HEADER_LENGTH];
		in.readBytes(data, 0, length - Message.HEADER_LENGTH);
	}
	
	@Override
	public void writeExtra(ByteBuf out) {
		out.writeBytes(data);
	}
}
