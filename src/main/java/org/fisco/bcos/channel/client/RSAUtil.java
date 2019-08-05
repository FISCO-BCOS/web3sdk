package org.fisco.bcos.channel.client;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSAUtil {
	
	
	 public static String rsaStr="RSA";
	// 生成RSA
	public static KeyPair getKeyPair(int bit) throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(rsaStr);
		keyPairGenerator.initialize(bit);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		return keyPair;
	}

	// 获取公钥
	public static String getPublicKey(KeyPair keyPair) {
		PublicKey publicKey = keyPair.getPublic();
		byte[] bytes = publicKey.getEncoded();
		return byte2Base64(bytes);
	}

	// 获取私钥(Base64编码)
	public static String getPrivateKey(KeyPair keyPair) {
		PrivateKey privateKey = keyPair.getPrivate();
		byte[] bytes = privateKey.getEncoded();
		return byte2Base64(bytes);
	}

	// 公钥加密
	public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance(rsaStr);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] bytes = cipher.doFinal(content);
		return bytes;
	}

	// 公钥验签
	public static byte[] publicDecrypt(byte[] content, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance(rsaStr);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] bytes = cipher.doFinal(content);
		return bytes;
	}

	// 私钥签名
	public static byte[] privateEncrypt(byte[] content, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance(rsaStr);
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] bytes = cipher.doFinal(content);
		return bytes;
	}

	// 私钥解密
	public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance(rsaStr);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] bytes = cipher.doFinal(content);
		return bytes;
	}

	// 字节数组转Base64编码
	public static String byte2Base64(byte[] bytes) {
		Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(bytes);
	}

	// Base64编码转字节数组
	public static byte[] base642Byte(String base64Key) throws IOException {
		Base64.Decoder decoder = Base64.getDecoder();
		return decoder.decode(base64Key);
	}

	public static PublicKey getPublicKeyByStr(String key) {
		try {
			byte[] byteKey = Base64.getDecoder().decode(key);
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byteKey);
			KeyFactory keyFactory = KeyFactory.getInstance(rsaStr);
			return keyFactory.generatePublic(x509EncodedKeySpec);

		} catch (Exception e) {
			return null;
		}
	}
	
	public static PrivateKey  getPrivateKeyByStr(String key) {
	    try {
	        byte[] byteKey = Base64.getDecoder().decode(key);
	        PKCS8EncodedKeySpec x509EncodedKeySpec = new PKCS8EncodedKeySpec(byteKey);
	        KeyFactory keyFactory = KeyFactory.getInstance(rsaStr);
	        return keyFactory.generatePrivate(x509EncodedKeySpec);
	 
	    } catch (Exception e) {
	    	return null;
	    }
	}
}
