package org.fisco.bcos.channel.handler;

import java.util.ArrayList;
import java.util.List;

public class ConnectionInfo {

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

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    private String host = "";
    private Integer port = 0;

    private List<String> topics = new ArrayList<String>();

    @Override
    public String toString() {
        return "ConnectionInfo{" + "host='" + host + '\'' + ", port=" + port + '}';
    }
}
