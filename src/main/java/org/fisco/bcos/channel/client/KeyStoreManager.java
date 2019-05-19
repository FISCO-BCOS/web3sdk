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
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class KeyStoreManager {
    private String keyStoreFile;
    private String password;
    private KeyStore keyStore;
    PKCS12KeyStoreSpi p12KeyStore;
    
    public void load() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
	    NoSuchProviderException {
	Security.setProperty("crypto.policy", "unlimited");
	Security.addProvider(new BouncyCastleProvider());
	
	ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	keyStore = KeyStore.getInstance("PKCS12", "BC");
	Resource keyStoreResource = resolver.getResource(keyStoreFile);
	
	keyStore.load(keyStoreResource.getInputStream(), password.toCharArray());
    }

    public void load(InputStream in, String password)
	    throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
	//keyStore.load(in, password.toCharArray());
	
	p12KeyStore.engineLoad(in, password.toCharArray());
    }

    public PrivateKey getPrivateKey(String name, String password)
	    throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
	return (PrivateKey) keyStore.getKey(name, password.toCharArray());
    }
    
    public PublicKey getPublicKey(String name, String password) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException  {
	ECPrivateKey privateKey = (ECPrivateKey) getPrivateKey(name, password);

	ECParameterSpec params = privateKey.getParams();

	org.bouncycastle.jce.spec.ECParameterSpec bcSpec = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util
		.convertSpec(params, false);
	org.bouncycastle.math.ec.ECPoint q = bcSpec.getG().multiply(privateKey.getS());
	org.bouncycastle.math.ec.ECPoint bcW = bcSpec.getCurve().decodePoint(q.getEncoded(false));
	ECPoint w = new ECPoint(bcW.getAffineXCoord().toBigInteger(), bcW.getAffineYCoord().toBigInteger());
	ECPublicKeySpec keySpec = new ECPublicKeySpec(w, tryFindNamedCurveSpec(params));
	return (PublicKey) KeyFactory
		.getInstance("EC", org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME)
		.generatePublic(keySpec);
    }
    
	@SuppressWarnings("unchecked")
	public static ECParameterSpec tryFindNamedCurveSpec(ECParameterSpec params) {
	    org.bouncycastle.jce.spec.ECParameterSpec bcSpec
	        = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(params, false);
	    for (Object name : Collections.list(org.bouncycastle.jce.ECNamedCurveTable.getNames())) {
	        org.bouncycastle.jce.spec.ECNamedCurveParameterSpec bcNamedSpec
	            = org.bouncycastle.jce.ECNamedCurveTable.getParameterSpec((String) name);
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

    public Certificate getCertificate(String name) throws KeyStoreException {
	return keyStore.getCertificate(name);
    }

    public PublicKey getPublicKeyFromCertificate(String name) throws KeyStoreException {
	Certificate certificate = getCertificate(name);
	return certificate.getPublicKey();
    }

    public ECKeyPair getECKeyPair(String name, String password)
	    throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
	PrivateKey privateKey = getPrivateKey(name, password);
	PublicKey publicKey = getPublicKey(name, password);

	KeyPair keyPair = new KeyPair(publicKey, privateKey);

	return ECKeyPair.create(keyPair);
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getKeyStoreFile() {
	return keyStoreFile;
    }

    public void setKeyStoreFile(String keyStoreFile) {
	this.keyStoreFile = keyStoreFile;
    }
}
