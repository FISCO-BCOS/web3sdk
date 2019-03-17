package org.fisco.bcos.web3j.crypto.gm.sm2.util;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;

public class KeyUtils {
  protected static final String DEFAUTL_CHARTSET = "ISO-8859-1";

  /**
   * get key Object to p12 file
   *
   * @param storeType JKS or JCEKS
   * @param storePath p12 film path
   * @param keyTyep public or private
   * @param password
   * @return
   */
  public static Key getKey(String storeType, String storePath, String keyTyep, String password) {
    Key pvtKey = null;
    FileInputStream fs = null;
    try {
      fs = new FileInputStream(storePath);
      KeyStore keyStore = KeyStore.getInstance(storeType);
      keyStore.load(fs, password.toCharArray());
      pvtKey = (Key) keyStore.getKey(keyTyep, password.toCharArray());
      if (fs != null) {
        try {
          fs.close();
        } catch (Exception e) {
          System.out.println("close " + storePath + " Failed, error msg:" + e.getMessage());
          return pvtKey;
        }
      }
    } catch (Exception ex) {
      if (fs != null) {
        try {
          fs.close();
        } catch (Exception e) {
          System.out.println("close " + storePath + " Failed, error msg:" + e.getMessage());
          return pvtKey;
        }
      }
      ex.printStackTrace();
      return pvtKey;
    }
    return pvtKey;
  }

  public static Certificate getCertificate(
      String storeType, String storePath, String name, String password) {
    Certificate cert = null;
    FileInputStream storeFs = null;
    try {
      storeFs = new FileInputStream(storePath);
      KeyStore keyStore = KeyStore.getInstance(storeType);
      keyStore.load(storeFs, password.toCharArray());
      cert = keyStore.getCertificate(name);
      if (storeFs != null) {
        try {
          storeFs.close();
        } catch (Exception e) {
          System.out.println("close " + storePath + " Failed, error msg:" + e.getMessage());
          return cert;
        }
      }
    } catch (Exception ex) {
      if (storeFs != null) {
        try {
          storeFs.close();
        } catch (Exception e) {
          System.out.println("close " + storePath + " Failed, error msg:" + e.getMessage());
          return cert;
        }
      }
      ex.printStackTrace();
      return cert;
    }
    return cert;
  }

  /**
   * hex to ascii array
   *
   * @param aschex
   * @return
   */
  public static byte[] aschex_to_bcdhex(String aschex) {
    byte[] aschexByte = aschex.getBytes();
    int j = 0;
    if (aschexByte.length % 2 == 0) {
      j = aschexByte.length / 2;
      byte[] resTmp = new byte[j];
      for (int i = 0; i < j; i++) {
        resTmp[i] = ascToHex(aschexByte[2 * i], aschexByte[2 * i + 1]);
      }
      return resTmp;

    } else {
      j = aschexByte.length / 2 + 1;
      byte[] resTmp = new byte[j];
      for (int i = 0; i < j - 1; i++) {
        resTmp[i] = ascToHex((byte) aschexByte[2 * i], (byte) aschexByte[2 * i + 1]);
      }
      resTmp[j - 1] = ascToHex((byte) aschexByte[2 * (j - 1)], (byte) 0);
      return resTmp;
    }
  }

  public static byte ascToHex(byte ch1, byte ch2) {
    byte ch;
    if (ch1 >= 'A') ch = (byte) ((ch1 - 0x37) << 4);
    else ch = (byte) ((ch1 - '0') << 4);
    if (ch2 >= 'A') ch |= (byte) (ch2 - 0x37);
    else ch |= (byte) (ch2 - '0');
    return ch;
  }

  public static byte hexLowToAsc(byte xxc) {
    xxc &= 0x0f;
    if (xxc < 0x0a) xxc += '0';
    else xxc += 0x37;
    return (byte) xxc;
  }

  public static byte hexHighToAsc(int xxc) {
    xxc &= 0xf0;
    xxc = xxc >> 4;
    if (xxc < 0x0a) xxc += '0';
    else xxc += 0x37;
    return (byte) xxc;
  }

  /**
   * * length to asn1
   *
   * @param len
   * @return asn1Len
   */
  public static byte[] toAsn1Len(int len) {
    int ret = 0;
    byte[] buff = new byte[10];
    byte[] asn1Len = null;
    if (len > 65535) {
      return null;
    }
    if (len > 255) {
      buff[0] = (byte) 0x82;
      buff[1] = (byte) ((len & 0xFF00) >> 8);
      buff[2] = (byte) (len & 0x00FF);
      ret = 3;
    } else {
      if ((len & 0x80) != 0) {
        buff[0] = (byte) 0x81;
        buff[1] = (byte) len;
        ret = 2;
      } else {
        buff[0] = (byte) len;
        ret = 1;
      }
    }
    asn1Len = new byte[ret];
    System.arraycopy(buff, 0, asn1Len, 0, ret);
    return asn1Len;
  }

  /**
   * hex bcd to hex asc
   *
   * @param bcdhex
   * @return
   */
  public static String bcdhex_to_aschex(byte[] bcdhex) {
    byte[] aschex = {0, 0};
    String res = "";
    String tmp = "";
    for (int i = 0; i < bcdhex.length; i++) {
      aschex[1] = hexLowToAsc(bcdhex[i]);
      aschex[0] = hexHighToAsc(bcdhex[i]);
      tmp = new String(aschex);
      res += tmp;
    }
    return res;
  }

  /**
   * hex bcd to hex asc
   *
   * @param bcdhex
   * @param len
   * @return
   */
  public static byte[] bcdhex_to_aschex(byte[] bcdhex, int len) {
    byte[] aschex = new byte[len * 2];

    for (int i = 0; i < len; i++) {
      aschex[2 * i] = hexHighToAsc(bcdhex[i]);
      aschex[2 * i + 1] = hexLowToAsc(bcdhex[i]);
    }
    return aschex;
  }

  /**
   * hex bcd to hex asc
   *
   * @param aschex
   * @param len
   * @return
   */
  public static byte[] aschex_to_bcdhex(byte[] aschex, int len) {
    int i, j;
    if (len % 2 == 0) {
      j = len / 2;
    } else {
      j = len / 2 + 1;
    }
    byte[] bcdhex = new byte[j];
    for (i = 0; i < j; i++) {
      bcdhex[i] = ascToHex(aschex[2 * i], aschex[2 * i + 1]);
    }
    return bcdhex;
  }

  /**
   * xor
   *
   * @param a
   * @param b
   * @return
   */
  public static final String xor(String a, String b) {
    if (a.length() != b.length()) return null;

    byte[] aBuf = aschex_to_bcdhex(a);
    byte[] bBuf = aschex_to_bcdhex(b);

    byte[] outBuf = new byte[aBuf.length];
    for (int j = 0; j < aBuf.length; j++) outBuf[j] = (byte) (aBuf[j] ^ bBuf[j]);

    return bcdhex_to_aschex(outBuf);
  }

  /**
   * * sha1
   *
   * @param inStr
   * @return
   */
  public static String sha1String(String inStr) {
    MessageDigest md = null;
    String outStr = null;
    try {
      md = MessageDigest.getInstance("SHA-1"); // 选择SHA-1
      byte[] data = null;
      try {
        data = inStr.getBytes(DEFAUTL_CHARTSET);
      } catch (Exception e) {
        data = inStr.getBytes();
      }
      byte[] digest = md.digest(data); // 返回的是byet[]，要转化为String存储比较方便
      outStr = bcdhex_to_aschex(digest);
    } catch (NoSuchAlgorithmException nsae) {
      nsae.printStackTrace();
    }
    return outStr;
  }

  /**
   * md5
   *
   * @param inStr
   * @return
   */
  public static String md5String(String inStr) {
    MessageDigest md = null;
    String outStr = null;
    try {
      md = MessageDigest.getInstance("MD5"); // 选择MD5
      byte[] data = null;
      try {
        data = inStr.getBytes(DEFAUTL_CHARTSET);
      } catch (Exception e) {
        data = inStr.getBytes();
      }
      byte[] digest = md.digest(data); // 返回的是byet[]，要转化为String存储比较方便
      outStr = bcdhex_to_aschex(digest);
    } catch (NoSuchAlgorithmException nsae) {
      nsae.printStackTrace();
    }
    return outStr;
  }

  /**
   * byte to hex
   *
   * @param b
   * @return
   */
  public static String byte2hex(byte[] b) {
    String hs = "";
    String stmp = "";
    for (int n = 0; n < b.length; n++) {
      stmp = (Integer.toHexString(b[n] & 0XFF));
      if (stmp.length() == 1) {
        hs = hs + "0" + stmp;
      } else {
        hs = hs + stmp;
      }
      if (n < b.length - 1) {
        hs = hs + "";
      }
    }
    return hs.toUpperCase();
  }

  /**
   * * hex to byte[]
   *
   * @param str
   * @return
   */
  public static byte[] hex2byte(String str) {
    int len = str.length();
    String stmp = null;
    byte bt[] = new byte[len / 2];
    for (int n = 0; n < len / 2; n++) {
      stmp = str.substring(n * 2, n * 2 + 2);
      bt[n] = (byte) (Integer.parseInt(stmp, 16));
    }
    return bt;
  }

  /**
   * character padding
   *
   * @param str
   * @param padc padding character
   * @param length
   * @return
   * @author fisco-bcos
   */
  public static String paddingRightStr(String str, char padc, int length) {
    if (str.length() > length) {
      return str.substring(0, length);
    } else {
      int numc = length - str.length();
      StringBuffer rets = new StringBuffer(str);
      for (int i = 0; i < numc; i++) {
        rets.append(padc);
      }
      return rets.toString();
    }
  }

  /**
   * character padding
   *
   * @param str
   * @param padc padding character
   * @param length
   * @return
   * @author fisco-bcos
   */
  public static String paddingLeftStr(String str, char padc, int length) {
    if (str.length() > length) {
      return str.substring(0, length);
    } else {
      int numc = length - str.length();
      StringBuffer rets = new StringBuffer(str);
      for (int i = 0; i < numc; i++) {
        rets.insert(0, padc);
      }
      return rets.toString();
    }
  }

  /**
   * byte[] add
   *
   * @param bytes1
   * @param bytes2
   * @return
   * @author fisco-bcos
   */
  public static byte[] addByteArray(byte[] bytes1, byte[] bytes2) {
    byte[] temp;
    if (bytes1 == null) {
      temp = new byte[bytes2.length];
      System.arraycopy(bytes2, 0, temp, 0, bytes2.length);
    } else if (bytes2 == null) {
      temp = new byte[bytes1.length];
      System.arraycopy(bytes1, 0, temp, 0, bytes1.length);
    } else {
      temp = new byte[bytes1.length + bytes2.length];
      System.arraycopy(bytes1, 0, temp, 0, bytes1.length);
      System.arraycopy(bytes2, 0, temp, bytes1.length, bytes2.length);
    }
    return temp;
  }
}
