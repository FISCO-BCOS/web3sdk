package org.fisco.bcos.channel.handler;

import java.util.List;

public class GroupChannelConnectionsConfig {

	public List<ChannelConnections> getAllChannelConnections() {
		return allChannelConnections;
	}

	public void setAllChannelConnections(List<ChannelConnections> allChannelConnections) {
		this.allChannelConnections = allChannelConnections;
	}

	private List<ChannelConnections> allChannelConnections;
}
