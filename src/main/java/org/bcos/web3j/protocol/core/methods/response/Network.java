package org.bcos.web3j.protocol.core.methods.response;

public  class Network {
    private String remoteAddress;

    public Network(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }
}
