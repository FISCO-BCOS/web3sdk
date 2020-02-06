package org.fisco.bcos.web3j.crypto;

import static org.fisco.bcos.web3j.crypto.Sign.CURVE;

import java.math.BigInteger;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.signers.HMacDSAKCalculator;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Hex;
import org.fisco.bcos.web3j.utils.Numeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by websterchen on 2018/4/25. */
public class ECDSASign implements SignInterface {

    private static final Logger logger = LoggerFactory.getLogger(ECDSASign.class);

    private static final BigInteger curveN =
            new BigInteger(
                    1,
                    Hex.decode("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141"));
    private static final BigInteger halfCurveN = curveN.shiftRight(1);

    @Override
    public Sign.SignatureData signMessage(byte[] message, ECKeyPair keyPair) {
        BigInteger privateKey = keyPair.getPrivateKey();
        BigInteger publicKey = keyPair.getPublicKey();

        byte[] messageHash = Hash.sha3(message);

        ECDSASignature sig = sign(messageHash, privateKey);

        // Now we have to work backwards to figure out the recId needed to recover the signature.
        ECPoint ecPoint = sig.p;
        BigInteger affineXCoordValue = ecPoint.normalize().getAffineXCoord().toBigInteger();
        BigInteger affineYCoordValue = ecPoint.normalize().getAffineYCoord().toBigInteger();

        int recId = affineYCoordValue.and(BigInteger.ONE).intValue();
        recId |= (affineXCoordValue.compareTo(sig.r) != 0 ? 2 : 0);
        if (sig.s.compareTo(halfCurveN) > 0) {
            sig.s = Sign.CURVE.getN().subtract(sig.s);
            recId = recId ^ 1;
        }

        /** code to test if recId value is correct */
        /*
        int recId0 = -1;
        for (int i = 0; i < 4; i++) {
            BigInteger k = Sign.recoverFromSignature(i, sig, messageHash);
            if (k != null && k.equals(publicKey)) {
                recId0 = i;
                break;
            }
        }

        if (recId0 == -1) {
            throw new RuntimeException(
                    "Could not construct a recoverable key. This should never happen.");
        }

        if (recId != recId0) {
            if (logger.isErrorEnabled()) {
                logger.error(
                        " invalid recId value111, recId={}, recIdOld={}, s={}, r={}, x={}, y={} ",
                        recId,
                        recIdOld,
                        sig.s,
                        sig.r,
                        affineXCoordValue,
                        affineYCoordValue);
            }
        }
        */

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
        Object[] components = signer.generateSignature2(transactionHash);
        /*
        return new ECDSASignature(
                        (BigInteger) components[0],
                        (BigInteger) components[1],
                        (ECPoint) components[2])
                .toCanonicalised();*/
        return new ECDSASignature(
                (BigInteger) components[0], (BigInteger) components[1], (ECPoint) components[2]);
    }
}
