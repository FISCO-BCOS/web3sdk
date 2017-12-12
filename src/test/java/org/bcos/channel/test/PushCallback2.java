package org.bcos.channel.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.bcos.channel.client.ChannelPushCallback;
import org.bcos.channel.dto.ChannelPush;
import org.bcos.channel.dto.ChannelResponse;

class PushCallback2 extends ChannelPushCallback {
	static Logger logger = LoggerFactory.getLogger(PushCallback2.class);
	
	@Override
	public void onPush(ChannelPush push) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		logger.debug("收到PUSH消息:" + push.getContent());
		
		System.out.println(df.format(LocalDateTime.now()) + "server:收到PUSH消息:" + push.getContent());
		
		logger.debug("获取块高");
		
		
		ChannelResponse response = new ChannelResponse();
		response.setContent("receive request seq:" + String.valueOf(push.getMessageID()));
		response.setErrorCode(0);
		
		push.sendResponse(response);
	}
}