package org.fisco.bcos.channel.protocol;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ChannelProtocol {
    private int protocol;

    private String nodeVersion;

    @JsonIgnore private EnumChannelProtocolVersion EnumProtocol;

    public String getNodeVersion() {
        return nodeVersion;
    }

    public void setNodeVersion(String nodeVersion) {
        this.nodeVersion = nodeVersion;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
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
        return "ChannelProtocol [protocol="
                + protocol
                + ", nodeVersion="
                + nodeVersion
                + ", EnumProtocol="
                + EnumProtocol
                + "]";
    }
}
