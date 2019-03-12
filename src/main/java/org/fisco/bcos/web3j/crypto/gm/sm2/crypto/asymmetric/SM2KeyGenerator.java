package org.fisco.bcos.web3j.crypto.gm.sm2.crypto.asymmetric;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.SecureRandom;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECFieldElement.Fp;
import org.bouncycastle.math.ec.ECPoint;
import org.fisco.bcos.web3j.crypto.gm.sm2.crypto.digests.SM3Digest;
import org.fisco.bcos.web3j.crypto.gm.sm2.crypto.params.ECDomainParameters;
import org.fisco.bcos.web3j.crypto.gm.sm2.util.KeyUtils;
import org.fisco.bcos.web3j.crypto.gm.sm2.util.encoders.Hex;

public class SM2KeyGenerator {

  // SM2算法推荐曲线参数
  public final BigInteger p =
      new BigInteger("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF", 16);
  public final BigInteger a =
      new BigInteger("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC", 16);
  public final BigInteger b =
      new BigInteger("28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93", 16);
  public final BigInteger n =
      new BigInteger("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123", 16);
  public final BigInteger gx =
      new BigInteger("32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7", 16);
  public final BigInteger gy =
      new BigInteger("BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0", 16);

  private ECDomainParameters ecdp;

  @SuppressWarnings("deprecation")
  public SM2KeyGenerator() {
    ECCurve curve = new ECCurve.Fp(p, a, b);
    ECFieldElement x = new Fp(p, gx);
    ECFieldElement y = new Fp(p, gy);
    ECPoint point = new ECPoint.Fp(curve, x, y);
    ecdp = new ECDomainParameters(curve, point, n);
  }

  /*
   * 根据数据生成SM2私钥
   *
   * @param imei 手机序列号
   *
   * @param pin pin码
   *
   * @param rand 随机数
   *
   * @return String 私钥(16进制字符串,256bits)
   */
  private SM2PrivateKey generatePrivateKey() {
    SecureRandom random = new SecureRandom();
    byte[] r = new byte[32];
    BigInteger k;
    do {
      random.nextBytes(r);
      k = new BigInteger(1, r);
    } while (!checkValidateK(k));

    byte[] in = k.toByteArray();
    SM3Digest digest = new SM3Digest();
    byte[] out = new byte[32];
    digest.update(in, 0, in.length);
    digest.doFinal(out, 0);

    String value = KeyUtils.bcdhex_to_aschex(out);
    return new SM2PrivateKey(new BigInteger(value, 16));
  }

  /*
   * 根据数据生成SM2密钥对
   *
   * @param imei 手机序列号
   *
   * @param pin pin码
   *
   * @param rand 随机数
   *
   * @return KeyPair 密钥对
   */
  public KeyPair generateKeyPair() {
    SM2PrivateKey privateKey = generatePrivateKey();
    ECPoint g = ecdp.getG();
    ECPoint p = g.multiply(privateKey.getD());
    SM2PublicKey publicKey = new SM2PublicKey(p);
    return new KeyPair(publicKey, privateKey);
  }

  public KeyPair generateKeyPair(String privKey) {
    SM2PrivateKey privateKey = new SM2PrivateKey(new BigInteger(privKey, 16));
    byte[] privateKeyBytes = privateKey.getEncoded();
    privateKey.setD(new BigInteger(Hex.toHexString(privateKeyBytes), 16));
    try {
      ECPoint g = ecdp.getG();
      ECPoint p = g.multiply(privateKey.getD());
      // System.out.println("===d:" + privateKey.getD().toString(16));
      SM2PublicKey publicKey = new SM2PublicKey(p);
      return new KeyPair(publicKey, privateKey);
    } catch (Exception e) {
      System.out.println("====generate keypair from priv key failed, error msg:" + e.getMessage());
      return null;
    }
  }

  private static boolean checkValidateK(BigInteger k) { // k ∈[1,n-1]
    BigInteger ecc_n =
        new BigInteger("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123", 16);
    if (k.compareTo(new BigInteger("0")) > 0 && k.compareTo(ecc_n) < 0) {
      return true;
    }
    return false;
  }
}
