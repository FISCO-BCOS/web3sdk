package org.fisco.bcos.channel.protocol;

public class SDKVersion {

    private int HighestSupported = Version.getHighestSupported().getVersionNumber();
    private String ClientType = "web3sdk";

    public int getHighestSupported() {
        return HighestSupported;
    }

    public void setHighestSupported(int highestSupported) {
        HighestSupported = highestSupported;
    }

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
