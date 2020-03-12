package org.fisco.bcos.web3j.crypto;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.ECFieldFp;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.EllipticCurve;
import java.util.Arrays;
import java.util.Objects;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.crypto.signers.HMacDSAKCalculator;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.jce.spec.IESParameterSpec;
import org.bouncycastle.math.ec.custom.sec.SecP256K1Curve;
import org.bouncycastle.math.ec.custom.sec.SecP256K1Point;
import org.fisco.bcos.web3j.utils.Numeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Elliptic Curve SECP-256k1 generated key pair. */
public class ECKeyPair {

    private static final Logger logger = LoggerFactory.getLogger(ECKeyPair.class);

    // ECDSA secp256k1 algorithm constants
    private final BigInteger POINTG_PRE =
            new BigInteger("79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798", 16);
    private final BigInteger POINTG_POST =
            new BigInteger("483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8", 16);
    private final BigInteger FACTOR_N =
            new BigInteger("fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364141", 16);
    private final BigInteger FIELD_P =
            new BigInteger("fffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f", 16);
    private EllipticCurve ellipticCurve =
            new EllipticCurve(new ECFieldFp(FIELD_P), new BigInteger("0"), new BigInteger("7"));
    private ECPoint pointG = new ECPoint(POINTG_PRE, POINTG_POST);
    private ECNamedCurveSpec ecNamedCurveSpec =
            new ECNamedCurveSpec("secp256k1", ellipticCurve, pointG, FACTOR_N);

    private final IESParameterSpec IES_PARAMS = new IESParameterSpec(null, null, 64);

    private final BigInteger privateKey;
    private final BigInteger publicKey;
    private KeyPair keyPair;

    public ECKeyPair(BigInteger privateKey, BigInteger publicKey) {
        this(privateKey, publicKey, null);
        /** Try to create KeyPair object from publicbKey, privateKey, no guarantee of success */
        keyPair = createKeyPairFromKey(privateKey, publicKey);
    }

    public ECKeyPair(BigInteger privateKey, BigInteger publicKey, KeyPair keyPair) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.keyPair = keyPair;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    /**
     * create KeyPair from publicKey and privateKey
     *
     * @param privateKey
     * @param publicKey
     * @return
     */
    private KeyPair createKeyPairFromKey(BigInteger privateKey, BigInteger publicKey) {
        try {
            BCECPublicKey bcecPublicKey = null;
            BCECPrivateKey bcecPrivateKey = null;
            if (!Objects.isNull(publicKey)) {
                // Handle public key.
                String publicKeyValue = publicKey.toString(16);
                String prePublicKeyStr = publicKeyValue.substring(0, 64);
                String postPublicKeyStr = publicKeyValue.substring(64);
                SecP256K1Curve secP256K1Curve = new SecP256K1Curve();
                SecP256K1Point secP256K1Point =
                        (SecP256K1Point)
                                secP256K1Curve.createPoint(
                                        new BigInteger(prePublicKeyStr, 16),
                                        new BigInteger(postPublicKeyStr, 16));
                SecP256K1Point secP256K1PointG =
                        (SecP256K1Point) secP256K1Curve.createPoint(POINTG_PRE, POINTG_POST);

                ECDomainParameters domainParameters =
                        new ECDomainParameters(secP256K1Curve, secP256K1PointG, FACTOR_N);
                ECPublicKeyParameters publicKeyParameters =
                        new ECPublicKeyParameters(secP256K1Point, domainParameters);
                bcecPublicKey =
                        new BCECPublicKey(
                                "ECDSA",
                                publicKeyParameters,
                                ecNamedCurveSpec,
                                BouncyCastleProvider.CONFIGURATION);
            }

            if (!Objects.isNull(privateKey)) {
                // Handle secret key
                ECPrivateKeySpec secretKeySpec = new ECPrivateKeySpec(privateKey, ecNamedCurveSpec);
                bcecPrivateKey =
                        new BCECPrivateKey(
                                "ECDSA", secretKeySpec, BouncyCastleProvider.CONFIGURATION);
            }

            return new KeyPair(bcecPublicKey, bcecPrivateKey);

        } catch (Exception e) {
            logger.debug(" create KeyPair failed, e: {}", e);
            return null;
        }
    }

    /**
     * create ECKeyPair from KeyPair
     *
     * @param keyPair
     * @return
     */
    public static ECKeyPair create(KeyPair keyPair) {
        BCECPrivateKey privateKey = (BCECPrivateKey) keyPair.getPrivate();
        BCECPublicKey publicKey = (BCECPublicKey) keyPair.getPublic();

        BigInteger privateKeyValue = privateKey.getD();

        // Ethereum does not use encoded public keys like bitcoin - see
        // https://en.bitcoin.it/wiki/Elliptic_Curve_Digital_Signature_Algorithm for details
        // Additionally, as the first bit is a constant prefix (0x04) we ignore this value
        byte[] publicKeyBytes = publicKey.getQ().getEncoded(false);
        BigInteger publicKeyValue =
                new BigInteger(1, Arrays.copyOfRange(publicKeyBytes, 1, publicKeyBytes.length));

        ECKeyPair ecKeyPair = new ECKeyPair(privateKeyValue, publicKeyValue, keyPair);
        return ecKeyPair;
    }

    /**
     * create ECKeyPair from privateKey
     *
     * @param privateKey
     * @return
     */
    public static ECKeyPair create(BigInteger privateKey) {
        return new ECKeyPair(privateKey, Sign.publicKeyFromPrivate(privateKey));
    }

    /**
     * create ECKeyPair from privateKey
     *
     * @param privateKey
     * @return
     */
    public static ECKeyPair create(byte[] privateKey) {
        return create(Numeric.toBigInt(privateKey));
    }

    /**
     * Sign a hash with the private key of this key pair.
     *
     * @param hash the hash to sign
     * @return An {@link ECDSASignature} of the hash
     */
    public ECDSASignature sign(byte[] hash) {

        ECDSASigner signer = new ECDSASigner(new HMacDSAKCalculator(new SHA256Digest()));

        ECPrivateKeyParameters privKey = new ECPrivateKeyParameters(privateKey, Sign.CURVE);
        signer.init(true, privKey);
        BigInteger[] components = signer.generateSignature(hash);

        return new ECDSASignature(components[0], components[1]).toCanonicalised();
    }

    /**
     * Verify a hash with the private key of this key pair.
     *
     * @param hash
     * @param signature
     * @return
     */
    public boolean verify(byte[] hash, ECDSASignature signature) {
        ECDSASigner signer = new ECDSASigner();
        // not for signing...
        signer.init(
                false,
                new ECPublicKeyParameters(
                        Sign.publicPointFromPrivate(getPrivateKey()), Sign.CURVE));
        return signer.verifySignature(hash, signature.r, signature.s);
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
        if (Objects.isNull(getKeyPair()) || Objects.isNull(getKeyPair().getPublic())) {
            throw new UnsupportedOperationException(" Not support operation. ");
        }
        // Encrypt data.
        Cipher cipher = Cipher.getInstance("ECIES", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, getKeyPair().getPublic(), IES_PARAMS);

        return cipher.doFinal(data);
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
        if (Objects.isNull(getKeyPair()) || Objects.isNull(getKeyPair().getPrivate())) {
            throw new UnsupportedOperationException(" Not support operation. ");
        }
        Cipher cipher = Cipher.getInstance("ECIES", "BC");
        cipher.init(Cipher.DECRYPT_MODE, getKeyPair().getPrivate(), IES_PARAMS);

        return cipher.doFinal(data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ECKeyPair ecKeyPair = (ECKeyPair) o;
        return Objects.equals(privateKey, ecKeyPair.privateKey)
                && Objects.equals(publicKey, ecKeyPair.publicKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(privateKey, publicKey);
    }
}
