package org.bcos.channel.handler;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

public class Message implements Serializable {
	private static Logger logger = LoggerFactory.getLogger(Message.class);
	
	private static final long serialVersionUID = -7276897518418560354L;
	
	public static final int HEADER_LENGTH = 4 + 2 + 32 + 4;
	
	public void readHeader(ByteBuf in) {
		//logger.debug("读取channel消息: {}:", ByteBufUtil.hexDump(in));
		
		length = in.readInt();
		//logger.debug("读取length:{}", length);
		type = in.readShort();
		//logger.debug("读取type:{}", type);
		
		byte[] dst = new byte[32];
		in.readBytes(dst);
		
		try {
			seq = new String(dst, "utf-8");
			//logger.debug("读取seq:{}", seq);
		} catch (UnsupportedEncodingException e) {
			//logger.error("解析seq错误，非法字符", e);
		}
		result = in.readInt();
		//logger.debug("读取result:{}", result);
	}
	
	public void readExtra(ByteBuf in) {
		data = new byte[length - HEADER_LENGTH];
		in.readBytes(data, 0, length - HEADER_LENGTH);
	}
	
	public void writeHeader(ByteBuf out) {
		//先计算总长度
		if(length.equals(0)) {
			length = HEADER_LENGTH + data.length;
		}
		
		out.writeInt(length);
		out.writeShort(type);
		//out.writeInt(seq);
		out.writeBytes(seq.getBytes(), 0, 32);
		out.writeInt(result);
		
		//logger.debug("写入channel消息: {}:", ByteBufUtil.hexDump(out));
	}
	
	public void writeExtra(ByteBuf out) {
		out.writeBytes(data);
	}
	
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
		this.length = data.length + HEADER_LENGTH;
	}
	
	protected Integer length = 0;
	protected Short type = 0;
	protected String seq = "";
	protected Integer result = 0;
	protected byte[] data;
}
