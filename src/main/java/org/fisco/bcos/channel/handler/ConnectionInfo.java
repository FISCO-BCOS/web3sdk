package org.fisco.bcos.channel.handler;

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

    private String host = "";
    private Integer port = 0;

    @Override
    public String toString() {
        return "ConnectionInfo{" + "host='" + host + '\'' + ", port=" + port + '}';
    }
}
