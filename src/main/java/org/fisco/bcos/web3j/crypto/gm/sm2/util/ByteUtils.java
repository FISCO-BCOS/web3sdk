package org.fisco.bcos.web3j.crypto.gm.sm2.util;

public class ByteUtils {

  private ByteUtils() {};

  /**
   * @param bytes1
   * @param bytes2
   * @return
   * @author
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

  /**
   * @param bytes
   * @return
   * @author
   */
  public static byte[] copyBytes(byte[] bytes) {
    byte[] temp;
    temp = new byte[bytes.length];
    System.arraycopy(bytes, 0, temp, 0, bytes.length);
    return temp;
  }

  /**
   * @param bytes
   * @param pos
   * @param length
   * @return
   * @author fisco-bcos
   */
  public static byte[] subByteArray(byte[] bytes, int pos, int length) {
    byte[] retbs = new byte[length];
    System.arraycopy(bytes, pos, retbs, 0, length);
    return retbs;
  }

  /**
   * @param bytes
   * @return
   * @author
   */
  public static byte[] reverse(byte[] bytes) {
    byte[] ret = new byte[bytes.length];

    int index = ret.length - 1;
    for (int i = 0; i < bytes.length; i++) {
      ret[index] = bytes[i];
      index--;
    }

    return ret;
  }

  /**
   * @param bytes
   * @return
   * @author
   */
  public static byte[] littleToBigEndian(byte[] bytes) {
    return reverse(bytes);
  }
}
