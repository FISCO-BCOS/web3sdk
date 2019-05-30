package org.fisco.bcos.web3j.crypto.gm;

import java.math.BigInteger;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.gm.sm3.SM3Digest;
import org.fisco.bcos.web3j.utils.Numeric;

public class GenGmAccount {
  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      Usage();
      return;
    }
    String opType = args[0];
    if (opType.equalsIgnoreCase("load")) {
      if (args.length < 2) {
        Usage();
        return;
      }
      LoadGuoMiKeyInfo(args[1]);
    } else if (opType.equalsIgnoreCase("genkey")) {
      String keyFile = "key.info";
      if (args.length > 1) keyFile = args[1];
      GenGuoMiKeyAndStore(keyFile);
    } else {
      System.out.println("UNKOWN OPTION");
      Usage();
    }
  }

  private static void Usage() {
    System.out.println("----Usage of GenGmAccount:");
    System.out.println(
        "-------------------------------------------------------------------------------");
    System.out.println("==== load key information from specified file ====");
    System.out.println(
        "java cp \'conf/:apps/*:lib/*\' org.bcos.contract.tools.GenGmAccount load ${keyFile}");
    System.out.println("");
    System.out.println(
        "==== generate guomi private key/public key/account, store into specified file, default is key.info=======");
    System.out.println(
        "java cp \'conf/:apps/*:lib/*\' org.bcos.contract.tools.GenGmAccount genkey ${keyFile}");
    System.out.println(
        "-------------------------------------------------------------------------------");
  }

  /**
   * @author: fisco-dev
   * @function: load private/public key from specified file
   * @param keyFile: file name that contains private/public/account info
   */
  private static void LoadGuoMiKeyInfo(String keyFile) {
    KeyInfo keyInfo = new KeyInfo();
    int ret = keyInfo.loadKeyInfo(keyFile);
    if (ret == RetCode.success)
      System.out.println("=== LOAD GUOMI KEY INFO FROM " + keyFile + " SUCCESS ===");
    else System.out.println("xxx LOAD GUOMI KEY INFO FROM " + keyFile + " FAILED xxx");
  }

  /**
   * @author: fisco-dev
   * @function: generate private/public key with GuoMi algorithm
   * @param keyFile: file name used to store private/public/account info
   */
  private static void GenGuoMiKeyAndStore(String keyFile) {

    System.out.println(
        "-------------------------------------------------------------------------------");
    System.out.println(
        "==========Generate (private key, public key, account) For Guomi randomly =======");
    ECKeyPair keyPair = GenCredential.createGuomiKeyPair();
    if (keyPair != null) {
      // deduce account according to public key
      String account = deduceAccountFromPublic(keyPair.getPublicKey());
      while (account == null || keyPair == null) {
        keyPair = GenCredential.createGuomiKeyPair();
        account = deduceAccountFromPublic(keyPair.getPublicKey());
      }
      System.out.println("===Generated Account:");
      System.out.println("* public key:" + (keyPair.getPublicKey().toString(16)));
      System.out.println("* private key :" + (keyPair.getPrivateKey().toString(16)));
      System.out.println("* account    :" + account);
      // save private/public/account result
      System.out.println("");
      System.out.println("==== SAVE PRIVATE/PUBLIC/ACCOUNT INFO ===");
      KeyInfo keyInfo =
          new KeyInfo(
              keyPair.getPublicKey().toString(16), keyPair.getPrivateKey().toString(16), account);
      int result = keyInfo.storeKeyInfo(keyFile);
      if (result != RetCode.success)
        System.out.println("xxx STORE PRIVATE/PUBLIC/ACCOUNT INFO FAILED xxx");
      else System.out.println("=== STORE PIVATE/PUBLIC/ACCOUNT SUCCEED ===");
      System.out.println(
          "-------------------------------------------------------------------------------");
    } else {
      System.out.println("==== generate private/public key with GuoMi algorithm failed ====");
    }
  }

  private static String deduceAccountFromPublic(BigInteger publicKey) {
    try {
      SM3Digest sm3Digest = new SM3Digest();
      System.out.println("===GEN COUNT :" + publicKey.toString(16));
      String publicKeyNoPrefix = Numeric.cleanHexPrefix(publicKey.toString(16));
      String hashSM3String = sm3Digest.hash(publicKeyNoPrefix);
      String account = hashSM3String.substring(24);

      return "0x" + account;
    } catch (Exception e) {
      System.out.println("DeduceAccountFromPublic failed, error message:" + e.getMessage());
      return null;
    }
  }
}
