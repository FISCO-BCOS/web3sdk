package org.bcos.channel.test;

import org.bcos.channel.client.ChannelResponseCallback;
import org.bcos.channel.dto.ChannelResponse;

public class PerfomanceCallback extends ChannelResponseCallback {
	@Override
	public void onResponseMessage(ChannelResponse response) {
		collector.onMessage(response);
	}
	
	public PerfomanceCollector collector;
}
