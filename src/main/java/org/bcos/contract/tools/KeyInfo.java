package org.bcos.contract.tools;

import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.bcos.web3j.protocol.ObjectMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyInfo implements KeyInfoInterface {
    private static String privateKey;
    private static String publicKey;
    private static String account;
    private static Logger logger = LoggerFactory.getLogger(KeyInfo.class);
    static int MAX_STR_LEN = 1024 * 1024;

    public final static String privJsonKey = "privateKey";
    public final static String pubJsonKey = "publicKey";
    public final static String accountJsonKey = "account";

    KeyInfo(String publicKey, String privateKey, String account) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.account = account;
    }

    KeyInfo() {
    }

    private static class Account {
        private String privateKey;
        private String publicKey;
        private String account;

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }
    }

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
            if (reader != null)
                bufReader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            int intC;
            while ((intC = bufReader.read()) != -1) {
                char c = (char) intC;
                if (c == '\n') {
                    break;
                }
                if (sb.length() >= MAX_STR_LEN) {
                    throw new Exception("input too long");
                }
                sb.append(c);
            }
            String content = sb.toString();
            System.out.println("read file " + keyFile + ", result:" + content);
            return content;
        } catch (Exception e) {
            logger.error("read file " + keyFile + " failed, error message:" + e.getMessage());
            return null;
        } finally {
            ReleaseInputStream(reader);
            ReleaseBufferedReader(bufReader);
        }
    }

    private static void ReleaseInputStream(InputStreamReader reader) {
        try {
            if (reader != null)
                reader.close();
        } catch (Exception e) {
            logger.error("close InputStreamReader failed, error message:" + e.getMessage());
        }
    }

    private static void ReleaseBufferedReader(BufferedReader bufReader) {
        try {
            if (bufReader != null)
                bufReader.close();
        } catch (Exception e) {
            logger.error("close BufferedReader failed, error message: " + e.getMessage());
        }
    }

    /**
     * @param keyFile: file that contains the key information
     * @author: fisco-dev
     * @function: load key information from specified file
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
            Account key =
                    ObjectMapperFactory.getObjectMapper().readValue(keyInfoJsonStr, Account.class);

            privateKey = key.getPrivateKey();
            publicKey = key.getPublicKey();
            account = key.getAccount();

            System.out.println("");
            System.out.println("====LOADED KEY INFO ===");
            System.out.println("* private key:" + privateKey);
            System.out.println("* public key :" + publicKey);
            System.out.println("* account: " + account);
            return RetCode.success;
        } catch (Exception e) {
            System.out.println("load private key from " + keyFile + " failed, error message:" + e.getMessage());
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
            logger.error("write " + content + " to " + keyFile + " failed, error message: " + e.getMessage());
        } finally {
            if (ps != null)
                ps.close();
        }
        return RetCode.storeKeyInfoFailed;
    }

    /**
     * @param keyFile: file used to store the key information
     * @author: fisco-dev
     * @function: store key information into specified file
     */
    @Override
    public int storeKeyInfo(String keyFile) {
    	try {
            Account key = new Account();
            key.setPrivateKey(getPrivateKey());
            key.setPublicKey(getPublicKey());
            key.setAccount(getAccount());

            String keyJsonInfo = ObjectMapperFactory.getObjectMapper().writeValueAsString(key);
            System.out.println("== SAVED KEY INFO: " + keyJsonInfo);
            return writeFile(keyFile, keyJsonInfo);
        } catch (Exception e) {
            System.out.println("store keyInfo to " + keyFile + " failed, error message: " + e.getMessage());
            logger.error("store keyInfo to " + keyFile + " failed, error message: " + e.getMessage());
            return RetCode.storeKeyInfoFailed;
        }
    }

}
