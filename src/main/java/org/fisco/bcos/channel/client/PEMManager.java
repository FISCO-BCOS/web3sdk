package org.fisco.bcos.channel.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Collections;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class PEMManager {

    private static final Logger logger = LoggerFactory.getLogger(PEMManager.class);

    private PemObject pem;
    private String pemFile;

    public PEMManager() {
        Security.setProperty("crypto.policy", "unlimited");
        Security.addProvider(new BouncyCastleProvider());
    }

    public void load()
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
                    NoSuchProviderException, InvalidKeySpecException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource pemResources = resolver.getResource(pemFile);

        load(pemResources.getInputStream());
    }

    public void load(InputStream in)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
                    InvalidKeySpecException, NoSuchProviderException {
        PemReader pemReader = new PemReader(new InputStreamReader(in));

        pem = pemReader.readPemObject();
        if (pem == null) {
            throw new IOException("The file does not represent a pem account.");
        }

        // logger.debug(" load pem, type: {}, header: {}", pem.getType(), pem.getHeaders());

        pemReader.close();
    }

    public PrivateKey getPrivateKey()
            throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException {
        PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(pem.getContent());
        KeyFactory keyFacotry = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);

        return keyFacotry.generatePrivate(encodedKeySpec);
    }

    public PublicKey getPublicKeyFromPublicPem()
            throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException {
        X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(pem.getContent());
        KeyFactory keyFacotry = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
        return keyFacotry.generatePublic(encodedKeySpec);
    }

    public PublicKey getPublicKey()
            throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException {
        ECPrivateKey privateKey = (ECPrivateKey) getPrivateKey();

        ECParameterSpec params = privateKey.getParams();

        org.bouncycastle.jce.spec.ECParameterSpec bcSpec =
                org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(params, false);
        org.bouncycastle.math.ec.ECPoint q = bcSpec.getG().multiply(privateKey.getS());
        org.bouncycastle.math.ec.ECPoint bcW = bcSpec.getCurve().decodePoint(q.getEncoded(false));
        ECPoint w =
                new ECPoint(
                        bcW.getAffineXCoord().toBigInteger(), bcW.getAffineYCoord().toBigInteger());
        ECPublicKeySpec keySpec = new ECPublicKeySpec(w, tryFindNamedCurveSpec(params));
        return (PublicKey)
                KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME)
                        .generatePublic(keySpec);
    }

    public ECKeyPair getECKeyPair()
            throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException,
                    InvalidKeySpecException, NoSuchProviderException {
        PrivateKey privateKey = getPrivateKey();
        PublicKey publicKey = getPublicKey();

        KeyPair keyPair = new KeyPair(publicKey, privateKey);

        return ECKeyPair.create(keyPair);
    }

    @SuppressWarnings("unchecked")
    public static ECParameterSpec tryFindNamedCurveSpec(ECParameterSpec params) {
        org.bouncycastle.jce.spec.ECParameterSpec bcSpec =
                org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(params, false);
        for (Object name : Collections.list(org.bouncycastle.jce.ECNamedCurveTable.getNames())) {
            org.bouncycastle.jce.spec.ECNamedCurveParameterSpec bcNamedSpec =
                    org.bouncycastle.jce.ECNamedCurveTable.getParameterSpec((String) name);
            if (bcNamedSpec.getN().equals(bcSpec.getN())
                    && bcNamedSpec.getH().equals(bcSpec.getH())
                    && bcNamedSpec.getCurve().equals(bcSpec.getCurve())
                    && bcNamedSpec.getG().equals(bcSpec.getG())) {
                return new org.bouncycastle.jce.spec.ECNamedCurveSpec(
                        bcNamedSpec.getName(),
                        bcNamedSpec.getCurve(),
                        bcNamedSpec.getG(),
                        bcNamedSpec.getN(),
                        bcNamedSpec.getH(),
                        bcNamedSpec.getSeed());
            }
        }
        return params;
    }

    public String getPemFile() {
        return pemFile;
    }

    public void setPemFile(String pemFile) {
        this.pemFile = pemFile;
    }
}
