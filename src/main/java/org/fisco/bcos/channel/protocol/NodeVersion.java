package org.fisco.bcos.channel.protocol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NodeVersion {
    @JsonProperty("HighestSupported")
    private int HighestSupported;

    @JsonProperty("NodeVersion")
    private String NodeVersion;

    @JsonIgnore
    public String getNodeVersion() {
        return NodeVersion;
    }

    public void setNodeVersion(String nodeVersion) {
        NodeVersion = nodeVersion;
    }

    @JsonIgnore
    public int getHighestSupported() {
        return HighestSupported;
    }

    public void setHighestSupported(int highestSupported) {
        HighestSupported = highestSupported;
    }

    @Override
    public String toString() {
        return "NodeVersion [HighestSupported="
                + HighestSupported
                + ", NodeVersion="
                + NodeVersion
                + "]";
    }
}
