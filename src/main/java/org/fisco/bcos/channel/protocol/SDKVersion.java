package org.fisco.bcos.channel.protocol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SDKVersion {
    @JsonProperty("HighestSupported")
    private int HighestSupported = Version.getHighestSupported().getVersionNumber();

    @JsonProperty("ClientType")
    private String ClientType = "java-sdk";

    @JsonIgnore
    public int getHighestSupported() {
        return HighestSupported;
    }

    public void setHighestSupported(int highestSupported) {
        HighestSupported = highestSupported;
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
        return "SDKVersion [HighestSupported="
                + HighestSupported
                + ", ClientType="
                + ClientType
                + "]";
    }
}
