package org.fisco.bcos.web3j.crypto.tool;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.custom.sec.SecP256K1Curve;
import org.bouncycastle.math.ec.custom.sec.SecP256K1Point;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.utils.Numeric;

/** ECC encrpt utils */
public class ECCEncrypt {

    private final BigInteger pubKey;
    private final BCECPublicKey bCECPublicKey;

    public ECCEncrypt(BigInteger pubKey) {
        this.pubKey = pubKey;
        this.bCECPublicKey = createBCECPublicKey(pubKey);
    }

    public BigInteger getPubKey() {
        return pubKey;
    }

    public BCECPublicKey getbCECPublicKey() {
        return bCECPublicKey;
    }

    /**
     * create BCECPublicKey from publicKey and privateKey
     *
     * @param publicKey
     * @return
     */
    private BCECPublicKey createBCECPublicKey(BigInteger publicKey) {
        // Handle public key.
        String publicKeyValue =
                Numeric.toHexStringNoPrefixZeroPadded(publicKey, Keys.PUBLIC_KEY_LENGTH_IN_HEX);
        String prePublicKeyStr = publicKeyValue.substring(0, 64);
        String postPublicKeyStr = publicKeyValue.substring(64);
        SecP256K1Curve secP256K1Curve = new SecP256K1Curve();
        SecP256K1Point secP256K1Point =
                (SecP256K1Point)
                        secP256K1Curve.createPoint(
                                new BigInteger(prePublicKeyStr, 16),
                                new BigInteger(postPublicKeyStr, 16));
        SecP256K1Point secP256K1PointG =
                (SecP256K1Point)
                        secP256K1Curve.createPoint(ECCParams.POINTG_PRE, ECCParams.POINTG_POST);

        ECDomainParameters domainParameters =
                new ECDomainParameters(secP256K1Curve, secP256K1PointG, ECCParams.FACTOR_N);
        ECPublicKeyParameters publicKeyParameters =
                new ECPublicKeyParameters(secP256K1Point, domainParameters);

        BCECPublicKey bcecPublicKey =
                new BCECPublicKey(
                        "ECDSA",
                        publicKeyParameters,
                        ECCParams.ecNamedCurveSpec,
                        BouncyCastleProvider.CONFIGURATION);

        return bcecPublicKey;
    }

    /**
     * Encrypt the data with ECC algorithm
     *
     * @param data
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     */
    public byte[] encrypt(byte[] data)
            throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException,
                    BadPaddingException, IllegalBlockSizeException,
                    InvalidAlgorithmParameterException, InvalidKeyException {

        // Encrypt data.
        Cipher cipher = Cipher.getInstance(" ECIES", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, getbCECPublicKey(), ECCParams.IES_PARAMS);

        return cipher.doFinal(data);
    }
}
