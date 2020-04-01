package org.fisco.bcos.channel.handler;

import java.util.ArrayList;
import java.util.List;

public class ConnectionInfo {
    @Deprecated
    public String getNodeID() {
        return nodeID;
    }

    @Deprecated
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

    @Deprecated
    public Boolean getConfig() {
        return config;
    }

    @Deprecated
    public void setConfig(Boolean config) {
        this.config = config;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    @Deprecated private String nodeID = "";
    @Deprecated private Boolean config = false;

    private String host = "";
    private Integer port = 0;

    private List<String> topics = new ArrayList<String>();

    @Override
    public String toString() {
        return "ConnectionInfo{" + "host='" + host + '\'' + ", port=" + port + '}';
    }
}
