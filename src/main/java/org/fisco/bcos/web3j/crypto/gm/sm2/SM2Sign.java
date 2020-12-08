package org.fisco.bcos.web3j.crypto.gm.sm2;

import java.io.IOException;
import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithID;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.Sign;
import org.fisco.bcos.web3j.crypto.SignInterface;
import org.fisco.bcos.web3j.crypto.gm.sm2.crypto.asymmetric.SM2Algorithm;
import org.fisco.bcos.web3j.crypto.gm.sm2.util.BigIntegers;
import org.fisco.bcos.web3j.crypto.gm.sm2.util.encoders.Hex;
import org.fisco.bcos.web3j.crypto.gm.sm3.SM3Digest;
import org.fisco.bcos.web3j.utils.Numeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by websterchen on 2018/3/22. */
public class SM2Sign implements SignInterface {
    static Logger logger = LoggerFactory.getLogger(SM2Sign.class);

    private static final ECDomainParameters eCDomainParameters =
            new ECDomainParameters(SM2Algorithm.sm2Curve, SM2Algorithm.sm2Point, SM2Algorithm.n);
    private static final byte[] identValue =
            org.bouncycastle.util.encoders.Hex.decode("31323334353637383132333435363738");

    @Override
    public Sign.SignatureData signMessage(byte[] message, ECKeyPair keyPair) {
        return sign2(message, keyPair);
    }

    /**
     * The new sm2 signature algorithm with better performance
     *
     * @param message
     * @param ecKeyPair
     * @return
     */
    public static Sign.SignatureData sign2(byte[] message, ECKeyPair ecKeyPair) {

        SM2Signer sm2Signer = new SM2Signer();

        ECPrivateKeyParameters eCPrivateKeyParameters =
                new ECPrivateKeyParameters(ecKeyPair.getPrivateKey(), eCDomainParameters);

        sm2Signer.initWithCache(
                true,
                new ParametersWithID(new ParametersWithRandom(eCPrivateKeyParameters), identValue));

        org.bouncycastle.crypto.digests.SM3Digest sm3Digest =
                new org.bouncycastle.crypto.digests.SM3Digest();

        byte[] md = new byte[sm3Digest.getDigestSize()];
        sm3Digest.update(message, 0, message.length);
        sm3Digest.doFinal(md, 0);

        sm2Signer.update(md, 0, md.length);

        byte[] r = null;
        byte[] s = null;
        byte[] pub = null;

        try {
            BigInteger[] bigIntegers = sm2Signer.generateSignature2();

            pub = Numeric.toBytesPadded(ecKeyPair.getPublicKey(), 64);
            r = SM2Algorithm.getEncoded(bigIntegers[0]);
            s = SM2Algorithm.getEncoded(bigIntegers[1]);
        } catch (CryptoException e) {
            throw new RuntimeException(e);
        }

        return new Sign.SignatureData((byte) 0, r, s, pub);
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

    /**
     * @param hash SM3 hash
     * @param signatureData
     * @return
     * @throws IOException
     */
    public static boolean verify(byte[] hash, Sign.SignatureData signatureData) {

        byte[] pub = signatureData.getPub();

        String hexPbkX = org.bouncycastle.util.encoders.Hex.toHexString(pub, 0, 32);
        String hexPbkY = org.bouncycastle.util.encoders.Hex.toHexString(pub, 32, 32);

        BigInteger biR = BigIntegers.fromUnsignedByteArray(signatureData.getR());
        BigInteger biS = BigIntegers.fromUnsignedByteArray(signatureData.getS());

        return SM2Algorithm.verify(hash, biR, biS, hexPbkX, hexPbkY);
    }
}
