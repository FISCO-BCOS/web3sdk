package org.fisco.bcos.web3j.crypto.gm;

import com.alibaba.fastjson.JSONObject;
import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyInfo implements KeyInfoInterface {
  private static String privateKey;
  private static String publicKey;
  private static String account;
  private static Logger logger = LoggerFactory.getLogger(KeyInfo.class);

  public static final String privJsonKey = "privateKey";
  public static final String pubJsonKey = "publicKey";
  public static final String accountJsonKey = "account";

  KeyInfo(String publicKey, String privateKey, String account) {
    this.privateKey = privateKey;
    this.publicKey = publicKey;
    this.account = account;
  }

  KeyInfo() {}

  public void setPrivateKey(String privKey) {
    this.privateKey = privKey;
  }

  public String getPrivateKey() {
    return this.privateKey;
  }

  public void setPublicKey(String pubKey) {
    this.publicKey = pubKey;
  }

  public String getPublicKey() {
    return this.publicKey;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getAccount() {
    return this.account;
  }

  private static String readFile(String keyFile) {
    InputStreamReader reader = null;
    BufferedReader bufReader = null;
    try {
      File file = new File(keyFile);
      reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
      if (reader != null) bufReader = new BufferedReader(reader);
      String line = null;
      String content = "";
      if (bufReader != null) {
        while ((line = bufReader.readLine()) != null) {
          content += line;
        }
        System.out.println("read file " + keyFile + ", result:" + content);
      }

      return content;
    } catch (Exception e) {
      logger.error("read file " + keyFile + " failed, error message:" + e.getMessage());
      return null;
    } finally {
      ReleaseBufferedReader(bufReader);
      ReleaseInputStream(reader);
    }
  }

  private static void ReleaseInputStream(InputStreamReader reader) {
    try {
      if (reader != null) reader.close();
    } catch (Exception e) {
      logger.error("close InputStreamReader failed, error message:" + e.getMessage());
    }
  }

  private static void ReleaseBufferedReader(BufferedReader bufReader) {
    try {
      if (bufReader != null) bufReader.close();
    } catch (Exception e) {
      logger.error("close BufferedReader failed, error message: " + e.getMessage());
    }
  }

  /**
   * @author: fisco-dev
   * @param keyFile: file that contains the key information
   */
  @Override
  public int loadKeyInfo(String keyFile) {
    String keyInfoJsonStr = readFile(keyFile);
    if (keyInfoJsonStr == null) {
      System.out.println("load key information failed");
      logger.error("load key information failed");
      return RetCode.openFileFailed;
    }
    System.out.println("");
    System.out.println("===key info:" + keyInfoJsonStr);
    try {
      JSONObject keyInfoJsonObj = JSONObject.parseObject(keyInfoJsonStr);
      if (keyInfoJsonObj == null) {
        System.out.println("load json str from key info failed");
        logger.error("load json str from key info failed");
        return RetCode.parseJsonFailed;
      }
      if (keyInfoJsonObj.containsKey(privJsonKey))
        privateKey = keyInfoJsonObj.getString(privJsonKey);
      if (keyInfoJsonObj.containsKey(pubJsonKey)) publicKey = keyInfoJsonObj.getString(pubJsonKey);
      if (keyInfoJsonObj.containsKey(accountJsonKey))
        account = keyInfoJsonObj.getString(accountJsonKey);
      System.out.println("");
      System.out.println("====LOADED KEY INFO ===");
      System.out.println("* private key:" + privateKey);
      System.out.println("* public key :" + publicKey);
      System.out.println("* account: " + account);
      return RetCode.success;
    } catch (Exception e) {
      System.out.println(
          "load private key from " + keyFile + " failed, error message:" + e.getMessage());
      return RetCode.loadKeyInfoFailed;
    }
  }

  private static int writeFile(String keyFile, String content) {
    File file = null;
    PrintStream ps = null;
    try {
      file = new File(keyFile);
      ps = new PrintStream(new FileOutputStream(file));
      ps.println(content);
      return RetCode.success;
    } catch (Exception e) {
      System.out.println("write " + content + " to " + keyFile + " failed");
      logger.error(
          "write " + content + " to " + keyFile + " failed, error message: " + e.getMessage());
    } finally {
      if (ps != null) ps.close();
    }
    return RetCode.storeKeyInfoFailed;
  }

  /** @author: fisco-dev */
  @Override
  public int storeKeyInfo(String keyFile) {
    try {
      // Map<String, String> keyMap = new HashMap<String, String>();
      JSONObject keyMapJson = new JSONObject();
      keyMapJson.put(privJsonKey, privateKey);
      keyMapJson.put(pubJsonKey, publicKey);
      keyMapJson.put(accountJsonKey, account);

      String keyJsonInfo = keyMapJson.toString();
      System.out.println("== SAVED KEY INFO: " + keyJsonInfo);
      return writeFile(keyFile, keyJsonInfo);
    } catch (Exception e) {
      System.out.println(
          "store keyInfo to " + keyFile + " failed, error message: " + e.getMessage());
      logger.error("store keyInfo to " + keyFile + " failed, error message: " + e.getMessage());
      return RetCode.storeKeyInfoFailed;
    }
  }
}
