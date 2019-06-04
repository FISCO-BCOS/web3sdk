package org.fisco.bcos.web3j.crypto;

import static org.fisco.bcos.web3j.crypto.Sign.CURVE;

import java.math.BigInteger;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.crypto.signers.HMacDSAKCalculator;
import org.fisco.bcos.web3j.utils.Numeric;

/** Created by websterchen on 2018/4/25. */
public class ECDSASign implements SignInterface {
  @Override
  public Sign.SignatureData signMessage(byte[] message, ECKeyPair keyPair) {
    BigInteger privateKey = keyPair.getPrivateKey();
    BigInteger publicKey = keyPair.getPublicKey();

    byte[] messageHash = Hash.sha3(message);

    ECDSASignature sig = sign(messageHash, privateKey);
    // Now we have to work backwards to figure out the recId needed to recover the signature.
    int recId = -1;
    for (int i = 0; i < 4; i++) {
      BigInteger k = Sign.recoverFromSignature(i, sig, messageHash);
      if (k != null && k.equals(publicKey)) {
        recId = i;
        break;
      }
    }
    if (recId == -1) {
      throw new RuntimeException(
          "Could not construct a recoverable key. This should never happen.");
    }

    int headerByte = recId + 27;

    // 1 header + 32 bytes for R + 32 bytes for S
    byte v = (byte) headerByte;
    byte[] r = Numeric.toBytesPadded(sig.r, 32);
    byte[] s = Numeric.toBytesPadded(sig.s, 32);

    return new Sign.SignatureData(v, r, s);
  }

  public static ECDSASignature sign(byte[] transactionHash, BigInteger privateKey) {
    ECDSASigner signer = new ECDSASigner(new HMacDSAKCalculator(new SHA256Digest()));

    ECPrivateKeyParameters privKey = new ECPrivateKeyParameters(privateKey, CURVE);
    signer.init(true, privKey);
    BigInteger[] components = signer.generateSignature(transactionHash);

    return new ECDSASignature(components[0], components[1]).toCanonicalised();
  }
}
