package org.fisco.bcos.web3j.utils;

public class Account {

    public String address;
    public String privateKey;
    public String publicKey;
    public String encryptType;

    public Account() {
        super();
    }

    public Account(String address, String privateKey, String publicKey, String encryptType) {
        super();
        this.address = address;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.encryptType = encryptType;
    }

    public String getAddress() {
        return address;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getEncryptType() {
        return encryptType;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setEncryptType(String encryptType) {
        this.encryptType = encryptType;
    }
}
