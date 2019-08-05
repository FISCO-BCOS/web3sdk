package org.fisco.bcos.channel.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BcosHeartbeat {
    @JsonProperty("Heartbeat")
    public String Heartbeat;

    @JsonIgnore
    public String getHeartbeat() {
        return Heartbeat;
    }

    public void setHeartbeat(String Heartbeat) {
        this.Heartbeat = Heartbeat;
    }

    @Override
    public String toString() {
        return "BcosHeartbeat [Heartbeat=" + Heartbeat + "]";
    }
}
