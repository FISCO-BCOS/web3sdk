package org.bcos.channel.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.bcos.channel.dto.ChannelPush;
import org.bcos.channel.dto.ChannelResponse;
import org.bcos.channel.handler.ConnectionInfo;
import io.netty.channel.ChannelHandlerContext;

public abstract class ChannelPushCallback {
	public abstract void onPush(ChannelPush push);
	
	static Logger logger = LoggerFactory.getLogger(ChannelPushCallback.class);
}