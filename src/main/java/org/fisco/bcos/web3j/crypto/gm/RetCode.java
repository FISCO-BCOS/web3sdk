package org.fisco.bcos.web3j.crypto.gm;

public class RetCode {
  public static final int success = 0;
  public static final int openFileFailed = 10000;
  public static final int parseJsonFailed = 10001;
  public static final int loadKeyInfoFailed = 10002;
  public static final int storeKeyInfoFailed = 10003;

  public static void msg(int retCode) {
    if (retCode == success) System.out.println("===SUCCESS===");
    if (retCode == openFileFailed) System.out.println("===OPEN FILE FAILED===");
    if (retCode == parseJsonFailed) System.out.println("===PARSE JSON OBJECT FAILED ===");
    if (retCode == loadKeyInfoFailed) System.out.println("===LOAD KEY FROM FILE FAILED ===");
    if (retCode == storeKeyInfoFailed) System.out.println("====STORE KEY INFO FAILED====");
  }
}
