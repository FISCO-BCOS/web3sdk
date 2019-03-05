package org.fisco.bcos.web3j.crypto.gm;

import java.math.BigInteger;
import java.security.KeyPair;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.crypto.gm.sm2.crypto.asymmetric.SM2KeyGenerator;
import org.fisco.bcos.web3j.crypto.gm.sm2.crypto.asymmetric.SM2PrivateKey;
import org.fisco.bcos.web3j.crypto.gm.sm2.crypto.asymmetric.SM2PublicKey;
import org.fisco.bcos.web3j.crypto.gm.sm2.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenCredential {
  private static Logger logger = LoggerFactory.getLogger(GenCredential.class);

  public static ECKeyPair createGuomiKeyPair() {
    System.out.println("=====INIT GUOMI KEYPAIR ====");
    final SM2KeyGenerator generator = new SM2KeyGenerator();
    final KeyPair keyPairData = generator.generateKeyPair();
    if (keyPairData != null) return genEcPairFromKeyPair(keyPairData);
    System.out.println("=====INIT GUOMI KEYPAIR FAILED ====");
    return null;
  }

  private static ECKeyPair genEcPairFromKeyPair(KeyPair keyPairData) {
    try {
      SM2PrivateKey vk = (SM2PrivateKey) keyPairData.getPrivate();
      SM2PublicKey pk = (SM2PublicKey) keyPairData.getPublic();
      final byte[] publicKey = pk.getEncoded();
      final byte[] privateKey = vk.getEncoded();

      // System.out.println("===public:" + Hex.toHexString(publicKey));
      // System.out.println("===private:" + Hex.toHexString(privateKey));
      BigInteger biPublic = new BigInteger(Hex.toHexString(publicKey), 16);
      BigInteger biPrivate = new BigInteger(Hex.toHexString(privateKey), 16);

      // System.out.println("---public:" + biPublic.toString(16));
      // System.out.println("---private:" + biPrivate.toString(16));

      ECKeyPair keyPair = new ECKeyPair(biPrivate, biPublic);
      return keyPair;
    } catch (Exception e) {
      logger.error("create ec_keypair of guomi failed, error msg:" + e.getMessage());
      return null;
    }
  }

  private static ECKeyPair createGuomiKeyPair(String privKey) {
    SM2KeyGenerator generator = new SM2KeyGenerator();
    final KeyPair keyPairData = generator.generateKeyPair(privKey);
    if (keyPairData != null) return genEcPairFromKeyPair(keyPairData);
    return null;
  }

  private static ECKeyPair createECDSAKeyPair(String privKey) {
    try {
      BigInteger bigPrivKey = new BigInteger(privKey, 16);
      ECKeyPair keyPair = ECKeyPair.create(bigPrivKey);
      return keyPair;
    } catch (Exception e) {
      logger.error("create keypair of ECDSA failed, error msg:" + e.getMessage());
      return null;
    }
  }

  private static ECKeyPair createECDSAKeyPair() {
    try {
      ECKeyPair keyPair = Keys.createEcKeyPair();
      return keyPair;
    } catch (Exception e) {
      logger.error("create keypair of ECDSA failed, error msg:" + e.getMessage());
      return null;
    }
  }

  private static ECKeyPair createKeyPair() {
    // use guomi
    if (EncryptType.encryptType == 1) return createGuomiKeyPair();
    return createECDSAKeyPair(); // default use ECDSA
  }

  private static ECKeyPair createKeyPair(String privKey) {
    if (EncryptType.encryptType == 1) return createGuomiKeyPair(privKey);
    return createECDSAKeyPair(privKey);
  }

  public static Credentials create() {
    try {
      ECKeyPair keyPair = createKeyPair();
      if (keyPair == null) return null;

      Credentials credentials = Credentials.create(keyPair);
      return credentials;
    } catch (Exception e) {
      System.out.println("init credential failed");
      logger.error("init credential failed, error msg:" + e.getMessage());
      return null;
    }
  }

  public static Credentials create(String privKey) {
    try {
      ECKeyPair keyPair = createKeyPair(privKey);
      if (keyPair == null) return null;
      Credentials credentials = Credentials.create(keyPair);
      return credentials;
    } catch (Exception e) {
      System.out.println("init credential from private key failed ");
      logger.error("init credential from private key failed, error msg:" + e.getMessage());
      return null;
    }
  }
}
