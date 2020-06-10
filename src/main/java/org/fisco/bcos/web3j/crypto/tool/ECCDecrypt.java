package org.fisco.bcos.web3j.crypto.tool;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.ECPrivateKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/** ECC decrypt utils */
public class ECCDecrypt {

    private final BigInteger priKey;
    private final BCECPrivateKey bCECPrivateKey;

    public ECCDecrypt(BigInteger privateKey) {
        this.priKey = privateKey;
        this.bCECPrivateKey = createBCECPrivateKey(privateKey);
    }

    public BigInteger getPriKey() {
        return priKey;
    }

    public BCECPrivateKey getbCECPrivateKey() {
        return bCECPrivateKey;
    }

    /**
     * create BCECPrivateKey from privateKey
     *
     * @param privateKey
     * @return
     */
    private BCECPrivateKey createBCECPrivateKey(BigInteger privateKey) {
        // Handle secret key
        ECPrivateKeySpec secretKeySpec =
                new ECPrivateKeySpec(privateKey, ECCParams.ecNamedCurveSpec);
        BCECPrivateKey bcecPrivateKey =
                new BCECPrivateKey("ECDSA", secretKeySpec, BouncyCastleProvider.CONFIGURATION);
        return bcecPrivateKey;
    }

    /**
     * Decrypt the data which encryt by ECC
     *
     * @param data
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public byte[] decrypt(byte[] data)
            throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException,
                    InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException,
                    IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("ECIES", "BC");
        cipher.init(Cipher.DECRYPT_MODE, getbCECPrivateKey(), ECCParams.IES_PARAMS);

        return cipher.doFinal(data);
    }
}
