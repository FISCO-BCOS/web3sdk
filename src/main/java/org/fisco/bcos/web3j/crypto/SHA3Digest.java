package org.fisco.bcos.web3j.crypto;

import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.fisco.bcos.web3j.utils.Numeric;

/** Created by websterchen on 2018/3/21. */
public class SHA3Digest implements HashInterface {
  public SHA3Digest() {}

  @Override
  public String hash(String hexInput) {
    byte[] bytes = Numeric.hexStringToByteArray(hexInput);
    byte[] result = hash(bytes);
    return Numeric.toHexString(result);
  }

  @Override
  public byte[] hash(byte[] input, int offset, int length) {
    Keccak.DigestKeccak kecc = new Keccak.Digest256();
    kecc.update(input, offset, length);
    return kecc.digest();
  }

  @Override
  public byte[] hash(byte[] input) {
    return hash(input, 0, input.length);
  }
}
