package org.fisco.bcos.web3j.crypto.gm;

public interface KeyInfoInterface {
  public int loadKeyInfo(String keyFile);

  public int storeKeyInfo(String keyFile);
}
