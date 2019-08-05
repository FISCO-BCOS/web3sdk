package org.fisco.bcos.channel.protocol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChannelHandshake {
    @JsonProperty("MaximumSupport")
    private int MaximumSupport = EnumChannelProtocolVersion.getMaximumProtocol().getVersionNumber();

    @JsonProperty("MinimumSupport")
    private int MinimumSupport = EnumChannelProtocolVersion.getMinimumProtocol().getVersionNumber();

    @JsonProperty("ClientType")
    private String ClientType = "java-sdk";

    @JsonIgnore
    public int getMaximumSupport() {
        return MaximumSupport;
    }

    public void setMaximumSupport(int maximumSupport) {
        MaximumSupport = maximumSupport;
    }

    @JsonIgnore
    public int getMinimumSupport() {
        return MinimumSupport;
    }

    public void setMinimumSupport(int minimumSupport) {
        MinimumSupport = minimumSupport;
    }

    @JsonIgnore
    public String getClientType() {
        return ClientType;
    }

    public void setClientType(String clientType) {
        ClientType = clientType;
    }

    @Override
    public String toString() {
        return "ChannelHandshake [MaximumSupport="
                + MaximumSupport
                + ", MinimumSupport="
                + MinimumSupport
                + ", ClientType="
                + ClientType
                + "]";
    }
}
