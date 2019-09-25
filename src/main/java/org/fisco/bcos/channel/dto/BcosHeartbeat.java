package org.fisco.bcos.channel.dto;

public class BcosHeartbeat {
    public int heartBeat;

    public int getHeartBeat() {
        return heartBeat;
    }

    public void setHeartBeat(int HeartBeat) {
        this.heartBeat = HeartBeat;
    }

    @Override
    public String toString() {
        return "BcosHeartbeat [heartBeat=" + heartBeat + "]";
    }
}
