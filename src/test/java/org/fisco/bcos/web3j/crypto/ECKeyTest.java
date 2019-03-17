package org.fisco.bcos.web3j.crypto;

import java.math.BigInteger;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

/** Created by mingzhenliu on 2017/7/12. */
public class ECKeyTest {

  @Test
  public void eckeyTest() throws Exception {

    ECKeyPair keyPair = Keys.createEcKeyPair();
    System.out.println("public key " + keyPair.getPublicKey());
    System.out.println("private key " + keyPair.getPrivateKey());
    System.out.println("serialize key " + Hex.toHexString(Keys.serialize(keyPair)));
    // public key
    // 6005884739482598907019672016029935954035758996027051146272921018865015941269698926222431345309233458526942087465818124661687956402067203118790805113144306
    // private key 11695290896330592173013668505941497555094145434653626165899956696676058923570
    // serialize key
    keyPair =
        Keys.deserialize(
            Hex.decode(
                "19db4cd14479981c3d7e785ec2412619885b5a7ffc438e6801474b962996023272ac27315da55056067973a3b58b27385bb9c919331df1751771016efcbe61a969458d6f7286b7a7107e4cd6e17b348c9df6c2b3fe9bf239555f90a78f8603f2"));
    System.out.println("public key " + keyPair.getPublicKey());
    System.out.println("private key " + Hex.toHexString(keyPair.getPrivateKey().toByteArray()));
    String str = "hello world";
    Sign.SignatureData sigData = Sign.getSignInterface().signMessage(str.getBytes(), keyPair);
    BigInteger publicKey = Sign.signedMessageToKey(str.getBytes(), sigData);
    System.out.println("publicKey " + publicKey);
  }
}
