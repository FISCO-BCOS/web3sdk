package org.bcos.channel.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.bcos.channel.dto.EthereumResponse;
import io.netty.util.Timeout;

public abstract class EthereumResponseCallback {
	private static Logger logger = LoggerFactory.getLogger(EthereumResponseCallback.class);
	
	public abstract void onResponse(EthereumResponse response);
	
	public void onTimeout() {
		logger.error("处理Ethereum消息超时:{}");
		
		EthereumResponse response = new EthereumResponse();
		response.setErrorCode(102);
		response.setErrorMessage("处理Ethereum消息超时");
		
		response.setContent("");
		
		onResponse(response);
	}
	
	public Timeout getTimeout() {
		return timeout;
	}

	public void setTimeout(Timeout timeout) {
		this.timeout = timeout;
	}

	private Timeout timeout;
}