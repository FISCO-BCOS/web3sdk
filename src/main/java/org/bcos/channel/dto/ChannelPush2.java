package org.bcos.channel.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.bcos.channel.client.Service;
import io.netty.channel.ChannelHandlerContext;

public class ChannelPush2 extends ChannelPush {
	static Logger logger = LoggerFactory.getLogger(ChannelPush2.class);
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void sendResponse(ChannelResponse response) {
		logger.debug("发送回包 seq:{}", response.getMessageID());
		
		response.setMessageID(seq);
		
		service.sendResponseMessage2(response, ctx, seq, topic);
	}

	private String content; //请求包体
	private String topic;
	
	//回包用字段

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	private Service service;
	private ChannelHandlerContext ctx;
	private String seq;
}
