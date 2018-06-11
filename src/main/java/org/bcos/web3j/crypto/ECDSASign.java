package org.bcos.web3j.crypto;

import org.bcos.web3j.utils.Numeric;

import java.math.BigInteger;

/**
 * Created by websterchen on 2018/4/25.
 */
public class ECDSASign implements SignInterface{
    @Override
    public Sign.SignatureData signMessage(byte[] message, ECKeyPair keyPair){
        BigInteger privateKey = keyPair.getPrivateKey();
        BigInteger publicKey = keyPair.getPublicKey();

        byte[] messageHash = Hash.sha3(message);

        Sign.ECDSASignature sig = Sign.sign(messageHash, privateKey);
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
}
