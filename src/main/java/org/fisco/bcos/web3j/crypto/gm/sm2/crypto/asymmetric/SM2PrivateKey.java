package org.fisco.bcos.web3j.crypto.gm.sm2.crypto.asymmetric;

import java.math.BigInteger;
import java.security.PrivateKey;
import org.fisco.bcos.web3j.crypto.gm.sm2.util.KeyUtils;

public class SM2PrivateKey implements PrivateKey {

  private static final long serialVersionUID = -8642664675083754692L;

  private final String ALGORITHM = "SM2";
  private BigInteger d;

  public SM2PrivateKey() {};

  public SM2PrivateKey(BigInteger d) {
    this.d = d;
  }

  public String getAlgorithm() {
    return ALGORITHM;
  }

  public byte[] getEncoded() {
    return padding(d.toByteArray(), 32);
  }

  public String getPvkHex() {
    return KeyUtils.bcdhex_to_aschex(getEncoded());
  }

  public String getFormat() {
    return getPvkHex();
  }

  public BigInteger getD() {
    return d;
  }

  public void setD(BigInteger d) {
    this.d = d;
  }

  public byte[] padding(byte[] key, int length) {
    if (key.length == length) {
      return key;
    } else if (key.length > length) {
      byte[] dest = new byte[length];
      System.arraycopy(key, key.length - length, dest, 0, length);
      return dest;
    } else {
      byte[] dest = new byte[length];
      for (int i = 0; i < length - key.length; i++) {
        dest[i] = 0x00;
      }
      System.arraycopy(key, 0, dest, length - key.length, key.length);
      return dest;
    }
  }
}
