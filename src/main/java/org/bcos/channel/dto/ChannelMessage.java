package org.bcos.channel.dto;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.bcos.channel.handler.Message;
import io.netty.buffer.ByteBuf;

public class ChannelMessage extends Message {
	private static Logger logger = LoggerFactory.getLogger(ChannelMessage.class);
	
	private static final long serialVersionUID = -7276897518418560354L;
	
	public ChannelMessage() {
		
	}
	
	public ChannelMessage(Message msg) {
		length = msg.getLength();
		type = msg.getType();
		seq = msg.getSeq();
		result = msg.getResult();
	}
	
	@Override
	public void readExtra(ByteBuf in) {
		logger.debug("解析channel数据包: {}", result);
		if (result == 0) {
			byte[] toNodeBytes = new byte[128];
			in.readBytes(toNodeBytes, 0, 128);
			toNode = new String(toNodeBytes);
			logger.debug("toNode: {}", toNode);

			byte[] fromNodeBytes = new byte[128];
			in.readBytes(fromNodeBytes, 0, 128);
			fromNode = new String(fromNodeBytes);
			logger.debug("fromNode: {}", fromNode);

			data = new byte[length - Message.HEADER_LENGTH - 128 - 128];
			in.readBytes(data, 0, length - Message.HEADER_LENGTH - 128 - 128);
			logger.debug("data: {} {}", data.length, data);
		}
	}
	
	@Override
	public void writeHeader(ByteBuf out) {
		//先计算总长度
		length = Message.HEADER_LENGTH + toNode.length() + fromNode.length() + data.length;
		
		super.writeHeader(out);
	}
	
	@Override
	public void writeExtra(ByteBuf out) {
		out.writeBytes(toNode.getBytes());
		out.writeBytes(fromNode.getBytes());
		
		out.writeBytes(data);
	}
	
	public String getToNode() {
		return toNode;
	}

	public void setToNode(String toNode) {
		this.toNode = toNode;
	}

	public String getFromNode() {
		return fromNode;
	}

	public void setFromNode(String fromNode) {
		this.fromNode = fromNode;
	}
	
	
	private String toNode;
	private String fromNode;
}
