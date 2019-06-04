package org.fisco.bcos.web3j.crypto.gm.sm2;

import java.io.IOException;
import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.Sign;
import org.fisco.bcos.web3j.crypto.SignInterface;
import org.fisco.bcos.web3j.crypto.gm.sm2.crypto.asymmetric.SM2Algorithm;
import org.fisco.bcos.web3j.crypto.gm.sm2.util.encoders.Hex;
import org.fisco.bcos.web3j.crypto.gm.sm3.SM3Digest;
import org.fisco.bcos.web3j.utils.Numeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by websterchen on 2018/3/22. */
public class SM2Sign implements SignInterface {
  static Logger logger = LoggerFactory.getLogger(SM2Sign.class);

  @Override
  public Sign.SignatureData signMessage(byte[] message, ECKeyPair keyPair) {
    return sign(message, keyPair);
  }

  public static Sign.SignatureData sign(byte[] message, ECKeyPair ecKeyPair) {
    SM3Digest sm3Digest = new SM3Digest();
    BigInteger[] rs = null;
    byte[] r = null;
    byte[] s = null;
    byte[] pub = null;
    byte v = 0;
    byte[] messageHash = sm3Digest.hash(message);
    try {
      byte[] signByte = SM2Algorithm.sign(messageHash, ecKeyPair.getPrivateKey());
      logger.debug("signData:{}", signByte);
      // System.out.println("signData:" + Hex.toHexString(signByte));
      ASN1Sequence as = (ASN1Sequence) ASN1Primitive.fromByteArray(signByte);
      rs =
          new BigInteger[] {
            ((ASN1Integer) as.getObjectAt(0)).getValue(),
            ((ASN1Integer) as.getObjectAt(1)).getValue()
          };
    } catch (IOException ex) {
      logger.error("SM2 Sign ERROR");
    }
    if (rs != null) {
      r = SM2Algorithm.getEncoded(rs[0]);
      s = SM2Algorithm.getEncoded(rs[1]);
      /*System.out.println("publicKey:" + Hex.toHexString(Numeric.toBytesPadded(ecKeyPair.getPublicKey(),64)));
      System.out.println("publicKeyLen:" + ecKeyPair.getPublicKey().bitLength());
      System.out.println("privateKey:" + Hex.toHexString(Numeric.toBytesPadded(ecKeyPair.getPrivateKey(),32)));
      System.out.println("privateKey:" + ecKeyPair.getPrivateKey().bitLength());*/
      pub = Numeric.toBytesPadded(ecKeyPair.getPublicKey(), 64);
      logger.debug("SM2 SignPublic:{},SM2SignPublicLen:{}", Hex.toHexString(pub), pub.length);
      logger.debug("SM2 SignR:{},SM2SignRLen{}", Hex.toHexString(r), r.length);
      logger.debug("SM2 SignS:{},SM2SignSLen{}", Hex.toHexString(s), s.length);
      // System.out.println("SM2 SignPublic:" + Hex.toHexString(pub));
    }
    return new Sign.SignatureData(v, r, s, pub);
  }
}
