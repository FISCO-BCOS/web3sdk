package org.fisco.bcos.channel.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BcosHeartbeat {
    @JsonProperty("HeartBeat")
    public int HeartBeat;

    @JsonIgnore
    public int getHeartBeat() {
        return HeartBeat;
    }

    public void setHeartBeat(int HeartBeat) {
        this.HeartBeat = HeartBeat;
    }

    @Override
    public String toString() {
        return "BcosHeartbeat [HeartBeat=" + HeartBeat + "]";
    }
}
