package org.fisco.bcos.channel.client;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.Keys;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class KeyStoreManager {
	public void load() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		keyStore = KeyStore.getInstance("PKCS12");
		Resource keyStoreResource = resolver.getResource(keyStoreFile);

		keyStore.load(keyStoreResource.getInputStream(), password.toCharArray());
	}
	
	public void load(InputStream in, String password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		keyStore.load(in, password.toCharArray());
	}
	
	public PrivateKey getPrivateKey(String name, String password) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
		return (PrivateKey) keyStore.getKey(name, password.toCharArray());
	}
	
	public Certificate getCertificate(String name) throws KeyStoreException {
		return keyStore.getCertificate(name);
	}
	
	public PublicKey getPublicKey(String name) throws KeyStoreException {
		Certificate certificate = getCertificate(name);
		return certificate.getPublicKey();
	}
	
	public ECKeyPair getECKeyPair(String name, String password) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
		PrivateKey privateKey = getPrivateKey(name, password);
		
		return Keys.deserialize(privateKey.getEncoded());
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

	private String keyStoreFile;
	private String password;
	private KeyStore keyStore;
}
