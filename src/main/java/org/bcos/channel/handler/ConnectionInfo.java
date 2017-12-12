package org.bcos.channel.handler;

import java.util.ArrayList;
import java.util.List;

public class ConnectionInfo {
	public String getNodeID() {
		return nodeID;
	}
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	
	public Boolean getConfig() {
		return config;
	}
	public void setConfig(Boolean config) {
		this.config = config;
	}
	
	public List<String> getTopics() {
		return topics;
	}
	public void setTopics(List<String> topics) {
		this.topics = topics;
	}

	private String nodeID = "";
	private String host = "";
	private Integer port = 0;
	private Boolean config = false;
	
	private List<String> topics = new ArrayList<String>();
}
