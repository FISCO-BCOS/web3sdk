package org.fisco.bcos.channel.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.fisco.bcos.channel.dto.ChannelPush;

public abstract class ChannelPushCallback {
	public abstract void onPush(ChannelPush push);
	
	static Logger logger = LoggerFactory.getLogger(ChannelPushCallback.class);
}