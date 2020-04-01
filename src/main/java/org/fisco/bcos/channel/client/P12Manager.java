package org.fisco.bcos.channel.client;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;
import org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class P12Manager {

    private static final Logger logger = LoggerFactory.getLogger(P12Manager.class);

    private String p12File;
    private final String NAME = "key";
    private String password;
    private KeyStore keyStore;
    private PKCS12KeyStoreSpi p12KeyStore;

    public P12Manager() {
        Security.setProperty("crypto.policy", "unlimited");
        Security.addProvider(new BouncyCastleProvider());
    }

    public void load()
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
                    NoSuchProviderException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        keyStore = KeyStore.getInstance("PKCS12", "BC");
        Resource keyStoreResource = resolver.getResource(p12File);

        keyStore.load(keyStoreResource.getInputStream(), password.toCharArray());

        // logger.debug(" p12 load, keyStore: {}", keyStore);
    }

    public void load(InputStream in, String password)
            throws NoSuchAlgorithmException, CertificateException, IOException, KeyStoreException,
                    NoSuchProviderException {
        keyStore = KeyStore.getInstance("PKCS12", "BC");
        keyStore.load(in, password.toCharArray());

        // logger.debug(" p12 load, keyStore: {}", keyStore);
    }

    public PrivateKey getPrivateKey()
            throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        return (PrivateKey) keyStore.getKey(NAME, password.toCharArray());
    }

    public PublicKey getPublicKey()
            throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException,
                    InvalidKeySpecException, NoSuchProviderException {
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

    public Certificate getCertificate() throws KeyStoreException {
        return keyStore.getCertificate(NAME);
    }

    public PublicKey getPublicKeyFromCertificate() throws KeyStoreException {
        Certificate certificate = getCertificate();
        return certificate.getPublicKey();
    }

    public ECKeyPair getECKeyPair()
            throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException,
                    InvalidKeySpecException, NoSuchProviderException {
        PrivateKey privateKey = getPrivateKey();
        PublicKey publicKey = getPublicKey();

        KeyPair keyPair = new KeyPair(publicKey, privateKey);

        return ECKeyPair.create(keyPair);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getP12File() {
        return p12File;
    }

    public void setP12File(String p12File) {
        this.p12File = p12File;
    }
}
