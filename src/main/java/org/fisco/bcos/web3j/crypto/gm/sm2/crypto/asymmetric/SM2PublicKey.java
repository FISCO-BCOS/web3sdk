package org.fisco.bcos.web3j.crypto.gm.sm2.crypto.asymmetric;

import java.math.BigInteger;
import java.security.PublicKey;
import org.bouncycastle.math.ec.ECPoint;
import org.fisco.bcos.web3j.crypto.gm.sm2.util.KeyUtils;

public class SM2PublicKey implements PublicKey {

  private static final long serialVersionUID = 6196280185307032043L;

  private final String ALGORITHM = "SM2";
  private BigInteger x;
  private BigInteger y;
  private ECPoint p;

  public SM2PublicKey() {}

  @SuppressWarnings("deprecation")
  public SM2PublicKey(ECPoint p) {
    this.p = p;
    this.x = p.getX().toBigInteger();
    this.y = p.getY().toBigInteger();
  }

  public String getAlgorithm() {
    return ALGORITHM;
  }

  public byte[] getEncoded() {
    byte[] xBuf = padding(this.x.toByteArray(), 32);
    byte[] yBuf = padding(this.y.toByteArray(), 32);
    byte[] encoded = new byte[xBuf.length + yBuf.length];
    System.arraycopy(xBuf, 0, encoded, 0, xBuf.length);
    System.arraycopy(yBuf, 0, encoded, xBuf.length, yBuf.length);
    return encoded;
  }

  public String getPbkxHex() {
    return KeyUtils.bcdhex_to_aschex(padding(x.toByteArray(), 32));
  }

  public String getPbkyHex() {
    return KeyUtils.bcdhex_to_aschex(padding(y.toByteArray(), 32));
  }

  public String getFormat() {
    return getPbkxHex() + getPbkyHex();
  }

  public BigInteger getX() {
    return x;
  }

  public BigInteger getY() {
    return y;
  }

  public ECPoint getP() {
    return p;
  }

  public void setP(ECPoint p) {
    this.p = p;
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
