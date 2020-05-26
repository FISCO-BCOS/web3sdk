package org.fisco.bcos.web3j.crypto.gm;

@Deprecated
public interface KeyInfoInterface {
    public int loadKeyInfo(String keyFile);

    public int storeKeyInfo(String keyFile);
}
