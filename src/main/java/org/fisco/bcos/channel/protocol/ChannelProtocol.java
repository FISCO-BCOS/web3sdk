package org.fisco.bcos.channel.protocol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChannelProtocol {
    @JsonProperty("Protocol")
    private int Protocol;

    @JsonProperty("NodeVersion")
    private String NodeVersion;

    @JsonIgnore private EnumChannelProtocolVersion EnumProtocol;

    @JsonIgnore
    public String getNodeVersion() {
        return NodeVersion;
    }

    public void setNodeVersion(String nodeVersion) {
        NodeVersion = nodeVersion;
    }

    @JsonIgnore
    public int getProtocol() {
        return Protocol;
    }

    public void setProtocol(int protocol) {
        Protocol = protocol;
    }

    @JsonIgnore
    public EnumChannelProtocolVersion getEnumProtocol() {
        return EnumProtocol;
    }

    public void setEnumProtocol(EnumChannelProtocolVersion enumProtocol) {
        EnumProtocol = enumProtocol;
    }

    @Override
    public String toString() {
        return "ChannelProtocol [Protocol="
                + Protocol
                + ", NodeVersion="
                + NodeVersion
                + ", EnumProtocol="
                + EnumProtocol
                + "]";
    }
}
