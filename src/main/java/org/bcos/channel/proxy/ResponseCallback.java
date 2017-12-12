package org.bcos.channel.proxy;

import org.bcos.channel.handler.Message;

public interface ResponseCallback {
	public void onResponse(Message response);
}
