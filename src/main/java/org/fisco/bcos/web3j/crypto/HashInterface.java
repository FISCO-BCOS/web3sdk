package org.fisco.bcos.web3j.crypto;
/** Created by websterchen on 2018/3/4. */
public interface HashInterface {
  String hash(String hexInput);

  byte[] hash(byte[] input, int offset, int length);

  byte[] hash(byte[] input);
}
