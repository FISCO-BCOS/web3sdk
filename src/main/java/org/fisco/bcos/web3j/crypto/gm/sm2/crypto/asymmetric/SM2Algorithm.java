package org.fisco.bcos.web3j.crypto.gm.sm2.crypto.asymmetric;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.asn1.*;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.fisco.bcos.web3j.crypto.gm.sm2.crypto.digests.SM3Digest;
import org.fisco.bcos.web3j.crypto.gm.sm2.util.BigIntegers;
import org.fisco.bcos.web3j.crypto.gm.sm2.util.ByteUtils;
import org.fisco.bcos.web3j.crypto.gm.sm2.util.KeyUtils;

/*
 * SM2国密算法加解密
 */
public class SM2Algorithm {

  // SM2算法推荐曲线参数
  public static final BigInteger p =
      new BigInteger("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF", 16);
  public static final BigInteger a =
      new BigInteger("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC", 16);
  public static final BigInteger b =
      new BigInteger("28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93", 16);
  public static final BigInteger n =
      new BigInteger("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123", 16);
  public static final BigInteger gx =
      new BigInteger("32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7", 16);
  public static final BigInteger gy =
      new BigInteger("BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0", 16);

  private static final ECCurve sm2Curve = new ECCurve.Fp(p, a, b);
  private static final ECPoint sm2Point = sm2Curve.createPoint(gx, gy);

  /*
   * SM2加密
   * @param pbk 公钥
   * @param data 待加密的数据
   * @return byte[] 加密后的数据
   */
  public static byte[] encrypt(SM2PublicKey pbk, byte[] data) {
    String buf = KeyUtils.bcdhex_to_aschex(pbk.getEncoded());
    String pbX = buf.substring(0, 64);
    String pbY = buf.substring(64, 128);
    return encrypt(pbX, pbY, data);
  }

  /*
   * SM2解密
   * @param pvk 私钥
   * @param cipher 待解密的数据
   * @return byte[] 解密后的数据
   */
  public static byte[] decrypt(SM2PrivateKey pvk, byte[] cipher) {
    String buf = KeyUtils.bcdhex_to_aschex(pvk.getEncoded());
    return decrypt(buf, cipher);
  }

  /*
   * SM2加密
   * @param pbX 公钥X成份(16进制格式)
   * @param pbY 公钥Y成份(16进制格式)
   * @param data 待加密的数据
   * @return byte[] 加密后的数据
   */
  public static byte[] encrypt(String pbkX, String pbkY, byte[] data) {
    byte[] t = null;
    ECPoint c1 = null;
    BigInteger x2 = null;
    BigInteger y2 = null;
    BigInteger x1 = new BigInteger(pbkX, 16);
    BigInteger y1 = new BigInteger(pbkY, 16);
    while (isEmpty(t)) {
      BigInteger k = generateRand(32);
      c1 = calculateC1(k);
      ECPoint s = calculateS(x1, y1, k);
      x2 = calculateX2(s);
      y2 = calculateY2(s);
      if (x2.toByteArray().length >= 32 && y2.toByteArray().length >= 32) {
        t = kdf(x2, y2, data.length);
      }
    }
    byte[] c2 = calculateC2(data, t);
    byte[] c3 = calculateC3(x2, data, y2);

    //		// 调试用(旧标准)
    //		byte[] c = getC(c1, c2, c3);

    byte[] c = getC(c1, c3, c2);
    return c;
  }

  /*
   * SM2解密
   * @param pk 私钥(16进制格式)
   * @param cipher 待解密的数据
   * @param byte[] 解密后的数据
   */
  @SuppressWarnings("deprecation")
  public static byte[] decrypt(String pvk, byte[] data) {
    String hexCipher = KeyUtils.bcdhex_to_aschex(data);
    String pbX = hexCipher.substring(0, 64);
    String pbY = hexCipher.substring(64, 128);

    //		// 调试用(旧标准)
    //		byte[] c2 = KeyUtils.aschex_to_bcdhex(hexCipher.substring(128, hexCipher.length() - 64));
    //		byte[] c3 = KeyUtils.aschex_to_bcdhex(hexCipher.substring(hexCipher.length() - 64,
    // hexCipher.length()));

    byte[] c3 = KeyUtils.aschex_to_bcdhex(hexCipher.substring(128, 192));
    byte[] c2 = KeyUtils.aschex_to_bcdhex(hexCipher.substring(192, hexCipher.length()));

    ECPoint s =
        calculateS(new BigInteger(pbX, 16), new BigInteger(pbY, 16), new BigInteger(pvk, 16));
    BigInteger x2 = s.getX().toBigInteger();
    BigInteger y2 = s.getY().toBigInteger();

    byte[] t = kdf(x2, y2, c2.length);
    if (isEmpty(t)) {
      return null;
    }

    byte[] m = calculateC2(t, c2);
    if (m == null) return null;
    byte[] cc3 = calculateC3(x2, m, y2);

    boolean sign = true;
    for (int i = 0; i < c3.length; i++) {
      if (c3.length != cc3.length) {
        sign = false;
        break;
      }
      if (c3[i] != cc3[i]) {
        sign = false;
        break;
      }
    }

    //		if(Arrays.equals(c3, cc3)) {  // JAVA可判断两个数组的值是否相等，android不行 - -
    if (sign) {
      return m;
    } else {
      return null;
    }
  }

  /*
   * 第1步:生成随机数 k∈[1, n-1]
   * @param length 需要生成随机数的长度
   * @return BigInteger 生成的随机数
   */
  private static BigInteger generateRand(int length) {
    if (length > 32) {
      return null;
    }
    BigInteger k = BigInteger.ZERO;
    SecureRandom secureRandom = new SecureRandom();
    byte[] buf = new byte[length];
    while (k.compareTo(BigInteger.ZERO) <= 0 || k.compareTo(n) >= 0) {
      secureRandom.nextBytes(buf); // 生成随机数
      k = new BigInteger(1, buf); // 正数(无符号格式)
    }
    return k;
  }

  /*
   * 第2步:计算椭圆曲线点 C1=[k]G=(x1,y1)
   */
  private static ECPoint calculateC1(BigInteger k) {
    return sm2Point.multiply(k);
  }

  /*
   * 第3步:计算椭圆曲线点 S=[k]Pb(Pb为公钥)
   */
  private static ECPoint calculateS(BigInteger x1, BigInteger y1, BigInteger k) {
    return sm2Curve.createPoint(x1, y1).multiply(k);
  }

  /*
   * 第4步:计算 [k]Pb=(x2,y2)
   */
  @SuppressWarnings("deprecation")
  private static BigInteger calculateX2(ECPoint s) {
    return s.getX().toBigInteger();
  }

  @SuppressWarnings("deprecation")
  private static BigInteger calculateY2(ECPoint s) {
    return s.getY().toBigInteger();
  }

  /*
   * 第5步:计算 t = KDF(x2, y2, keyLen)
   */
  private static byte[] kdf(BigInteger x2, BigInteger y2, int keyLen) {
    byte[] t = new byte[keyLen];

    SM3Digest sm3 = new SM3Digest();
    byte[] sm3Ret = new byte[32];
    int ct = 1;

    int value = keyLen / 32;
    int remainder = keyLen % 32;

    byte[] x2Buf = padding(x2.toByteArray());
    byte[] y2Buf = padding(y2.toByteArray());

    int offset = 0;
    for (int i = 0; i < value; i++) {
      sm3.update(x2Buf, 0, x2Buf.length);
      sm3.update(y2Buf, 0, y2Buf.length);
      sm3.update((byte) (ct >> 24 & 0x00ff));
      sm3.update((byte) (ct >> 16 & 0x00ff));
      sm3.update((byte) (ct >> 8 & 0x00ff));
      sm3.update((byte) (ct & 0x00ff));
      sm3.doFinal(t, offset);
      offset += 32;
      ct++;
    }
    if (remainder != 0) {
      sm3.update(x2Buf, 0, x2Buf.length);
      sm3.update(y2Buf, 0, y2Buf.length);
      sm3.update((byte) (ct >> 24 & 0x00ff));
      sm3.update((byte) (ct >> 16 & 0x00ff));
      sm3.update((byte) (ct >> 8 & 0x00ff));
      sm3.update((byte) (ct & 0x00ff));
      sm3.doFinal(sm3Ret, 0);
    }
    System.arraycopy(sm3Ret, 0, t, offset, remainder);
    return t;
  }

  /*
   * 第6步:计算 C2 = M xor t
   */
  private static byte[] calculateC2(byte[] m, byte[] t) {
    if (m == null || m.length != t.length) {
      return null;
    }
    byte[] bufOut = new byte[m.length];
    for (int i = 0; i < m.length; i++) {
      bufOut[i] = (byte) (m[i] ^ t[i]);
    }
    return bufOut;
  }

  /*
   * 第7步:计算 C3 = Hash(X2 || M || Y2)
   */
  private static byte[] calculateC3(BigInteger x2, byte[] m, BigInteger y2) {
    SM3Digest sm3 = new SM3Digest();
    byte[] c3 = new byte[32];
    byte[] x2Buf = padding(x2.toByteArray());
    byte[] y2Buf = padding(y2.toByteArray());
    sm3.update(x2Buf, 0, x2Buf.length);
    sm3.update(m, 0, m.length);
    sm3.update(y2Buf, 0, y2Buf.length);
    sm3.doFinal(c3, 0);
    return c3;
  }

  /*
   * 第8步:输出密文 C = C1||C3||C2
   * @param c1  公钥部分
   * @param c2  算法加密部分
   * @param c3 消息摘要部分(校验)
   */
  @SuppressWarnings("deprecation")
  private static byte[] getC(ECPoint c1, byte[] c3, byte[] c2) {
    byte[] c = new byte[64 + c3.length + c2.length];

    byte[] c1xBuf = padding(c1.getX().toBigInteger().toByteArray());
    byte[] c1yBuf = padding(c1.getY().toBigInteger().toByteArray());

    System.arraycopy(c1xBuf, 0, c, 0, 32);
    System.arraycopy(c1yBuf, 0, c, 32, 32);
    System.arraycopy(c3, 0, c, 64, c3.length);
    System.arraycopy(c2, 0, c, 64 + c3.length, c2.length);
    return c;
  }

  private static boolean isEmpty(byte[] t) {
    if (t != null) {
      for (int i = 0; i < t.length; i++) {
        if (t[i] != (byte) 0) {
          return false;
        }
      }
    }
    return true;
  }

  /*
   * 填充数据
   */
  private static byte[] padding(byte[] bi) {
    if (bi.length == 32) {
      return bi;
    } else if (bi.length > 32) {
      byte[] dest = new byte[32];
      System.arraycopy(bi, bi.length - 32, dest, 0, 32);
      return dest;
    } else {
      byte[] dest = new byte[32];
      for (int i = 0; i < 32 - bi.length; i++) {
        dest[i] = 0x00;
      }
      System.arraycopy(bi, 0, dest, 32 - bi.length, bi.length);
      return dest;
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static byte[] USER_ID = KeyUtils.hex2byte("31323334353637383132333435363738");
  private static int mFieldSizeInBytes;
  private static ECCurve curve256;
  private static ECPoint g256;

  // 初始化曲线G
  static {
    curve256 = new ECCurve.Fp(p, a, b);
    // 这里算出椭圆曲线
    g256 = curve256.createPoint(gx, gy);
    mFieldSizeInBytes = (p.bitLength() + 7 >> 3);
  }

  /**
   * SM2私钥签名
   *
   * @param md 待签名数据
   * @param privateKeyS
   * @return
   * @author fisco-bcos
   */
  private static BigInteger[] Sign(byte[] md, BigInteger privateKeyS) {
    SM3Digest sm3 = new SM3Digest();
    byte[] z = sm2GetZ(USER_ID, g256.multiply(privateKeyS));
    sm3.update(z, 0, z.length);
    byte[] p = md;
    sm3.update(p, 0, p.length);
    byte[] hashData = new byte[32];
    sm3.doFinal(hashData, 0);
    return SignSm3(hashData, privateKeyS);
  }

  /**
   * SM2私钥签名
   *
   * @param hash 32字节hash
   * @param privateKeyS
   * @return
   * @date 2015年12月3日
   * @author fisco-bcos
   */
  private static BigInteger[] SignSm3(byte[] hash, BigInteger privateKeyS) {
    byte[] hashData = ByteUtils.copyBytes(hash);
    BigInteger e = new BigInteger(1, hashData);
    BigInteger k = null;
    ECPoint kp = null;
    BigInteger r = null;
    BigInteger s = null;
    BigInteger userD = privateKeyS;
    do {
      do {
        k = createRandom();
        kp = g256.multiply(k);
        r = e.add(kp.getX().toBigInteger());
        r = r.mod(n);
      } while (r.equals(BigInteger.ZERO) || r.add(k).equals(n));
      BigInteger da_1 = userD.add(BigInteger.ONE).modInverse(n);
      s = r.multiply(userD);
      s = k.subtract(s);
      s = s.multiply(da_1);
      s = s.mod(n);
    } while (s.equals(BigInteger.ZERO));
    BigInteger[] retRS = {r, s};
    return retRS;
  }

  /**
   * SM2公钥验签
   *
   * @param msg
   * @param signData
   * @param biX
   * @param biY
   * @return
   * @author fisco-bcos
   */
  private static boolean verify(byte[] msg, byte[] signData, BigInteger biX, BigInteger biY) {
    ECPoint userKey = curve256.createPoint(biX, biY);
    byte[] btRS = signData;
    byte[] btR = ByteUtils.subByteArray(btRS, 0, btRS.length / 2);
    byte[] btS = ByteUtils.subByteArray(btRS, btR.length, btRS.length - btR.length);
    BigInteger r = new BigInteger(1, btR);
    // 检验 r ′ ∈[1, n-1]是否成立，若不成立则验证不通过；
    if (!checkValidateK(r)) return false;
    BigInteger s = new BigInteger(1, btS);
    // 检验 s ′ ∈[1, n-1]是否成立，若不成立则验证不通过；
    if (!checkValidateK(s)) return false;

    SM3Digest sm3 = new SM3Digest();
    byte[] z = sm2GetZ(USER_ID, userKey);
    sm3.update(z, 0, z.length);
    byte[] p = msg;
    sm3.update(p, 0, p.length);
    byte[] hashData = new byte[32];
    sm3.doFinal(hashData, 0);

    BigInteger e = new BigInteger(1, hashData);

    BigInteger t = r.add(s).mod(n);
    if (t.equals(BigInteger.ZERO)) return false;
    ECPoint x1y1 = g256.multiply(s);
    x1y1 = x1y1.add(userKey.multiply(t));
    BigInteger R = e.add(x1y1.getX().toBigInteger()).mod(n);

    return r.equals(R);
  }

  /** * 用随机数发生器产生随机数k ∈[1,n-1] */
  private static BigInteger createRandom() {
    SecureRandom random = new SecureRandom();
    byte[] r = new byte[32];
    BigInteger k;
    do {
      random.nextBytes(r);
      k = new BigInteger(1, r);
    } while (!checkValidateK(k));
    return k;
  }

  private static boolean checkValidateK(BigInteger k) { // k ∈[1,n-1]
    if (k.compareTo(new BigInteger("0")) > 0 && k.compareTo(n) < 0) {
      return true;
    }
    return false;
  }

  /**
   * 计算Za
   *
   * @param userId
   * @param publicKey
   * @return
   * @date 2015年12月4日
   * @author fisco-bcos
   */
  private static byte[] sm2GetZ(byte[] userId, ECPoint publicKey) {
    SM3Digest sm3 = new SM3Digest();
    int BitsLength = userId.length << 3;
    sm3.update((byte) (BitsLength >> 8 & 0xFF));
    sm3.update((byte) (BitsLength & 0xFF));

    sm3BlockUpdate(sm3, userId);
    sm3BlockUpdate(sm3, getEncoded(a));
    sm3BlockUpdate(sm3, getEncoded(b));
    sm3BlockUpdate(sm3, getEncoded(gx));
    sm3BlockUpdate(sm3, getEncoded(gy));
    sm3BlockUpdate(sm3, getEncoded(publicKey.getX().toBigInteger()));
    sm3BlockUpdate(sm3, getEncoded(publicKey.getY().toBigInteger()));

    byte[] md = new byte[sm3.getDigestSize()];
    sm3.doFinal(md, 0);

    return md;
  }

  private static void sm3BlockUpdate(SM3Digest sm3, byte[] bytes) {
    sm3.update(bytes, 0, bytes.length);
  }

  public static byte[] getEncoded(BigInteger value) {
    byte[] bytes = BigIntegers.asUnsignedByteArray(value);
    if (bytes.length > mFieldSizeInBytes) {
      byte[] tmp = new byte[mFieldSizeInBytes];
      System.arraycopy(bytes, bytes.length - mFieldSizeInBytes, tmp, 0, mFieldSizeInBytes);
      return tmp;
    }
    if (bytes.length < mFieldSizeInBytes) {
      byte[] tmp = new byte[mFieldSizeInBytes];
      System.arraycopy(bytes, 0, tmp, mFieldSizeInBytes - bytes.length, bytes.length);
      return tmp;
    }

    return bytes;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////
  public static byte[] sign(byte[] data, SM2PrivateKey pvk) throws IOException {
    // return sign(data,pvk.getPvkHex());
    return sign(data, pvk.getD());
  }

  public static byte[] sign(byte[] data, BigInteger privateKeyS) throws IOException {
    // BigInteger privateKeyS = new BigInteger(hexPvk, 16);
    BigInteger[] rs = Sign(data, privateKeyS);
    //		byte[] r = getEncoded(rs[0]);
    //		byte[] s = getEncoded(rs[1]);

    ASN1Integer[] ars = new ASN1Integer[] {new ASN1Integer(rs[0]), new ASN1Integer(rs[1])};
    return new DERSequence(ars).getEncoded(ASN1Encoding.DER);
  }

  public static boolean verify(byte[] data, byte[] signData, SM2PublicKey pbk) throws IOException {
    return verify(data, signData, pbk.getPbkxHex(), pbk.getPbkyHex());
  }

  public static boolean verify(byte[] data, byte[] signData, String hexPbkX, String hexPbkY)
      throws IOException {
    BigInteger biX = new BigInteger(hexPbkX, 16);
    BigInteger biY = new BigInteger(hexPbkY, 16);

    ASN1Sequence as = (ASN1Sequence) ASN1Primitive.fromByteArray(signData);
    BigInteger[] rs =
        new BigInteger[] {
          ((ASN1Integer) as.getObjectAt(0)).getValue(), ((ASN1Integer) as.getObjectAt(1)).getValue()
        };

    byte[] r = getEncoded(rs[0]);
    byte[] s = getEncoded(rs[1]);

    byte[] rsBytes = new byte[r.length + s.length];
    System.arraycopy(r, 0, rsBytes, 0, r.length);
    System.arraycopy(s, 0, rsBytes, r.length, s.length);

    return verify(data, rsBytes, biX, biY);
  }
}
