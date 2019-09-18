package org.fisco.bcos.channel.protocol;

public class ChannelHandshake {
    private int maximumSupport = EnumChannelProtocolVersion.getMaximumProtocol().getVersionNumber();

    private int minimumSupport = EnumChannelProtocolVersion.getMinimumProtocol().getVersionNumber();

    private String clientType = "java-sdk";

    public int getMaximumSupport() {
        return maximumSupport;
    }

    public void setMaximumSupport(int maximumSupport) {
        this.maximumSupport = maximumSupport;
    }

    public int getMinimumSupport() {
        return minimumSupport;
    }

    public void setMinimumSupport(int minimumSupport) {
        this.minimumSupport = minimumSupport;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    @Override
    public String toString() {
        return "ChannelHandshake [maximumSupport="
                + maximumSupport
                + ", minimumSupport="
                + minimumSupport
                + ", clientType="
                + clientType
                + "]";
    }
}
