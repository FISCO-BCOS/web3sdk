package org.fisco.bcos.web3j.crypto;

public enum EnumEncryptType {
    NORMAL(0),
    GM(1);

    private int type;

    EnumEncryptType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
