package org.fisco.bcos.channel.protocol;

@SuppressWarnings("serial")
public class ChannelPrococolExceiption extends Exception {

    public ChannelPrococolExceiption(int v) {
        super("Not support channel protocol, version " + String.valueOf(v));
    }
}
