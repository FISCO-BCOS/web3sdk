package org.fisco.bcos.channel.test.amop;
import java.security.KeyPair;

import org.fisco.bcos.channel.client.RSAUtil;

public class GenerateRsaKey {
	
	public void generateKey() {
		try {
			// ===============生成公钥和私钥，公钥传给客户端，私钥服务端保留==================
			// 生成RSA公钥和私钥，并Base64编码
			KeyPair keyPair = RSAUtil.getKeyPair(1024);
			String publicKeyStr = RSAUtil.getPublicKey(keyPair);
			String privateKeyStr = RSAUtil.getPrivateKey(keyPair);
			System.out.println("RSA公钥Base64编码:" + publicKeyStr);
			System.out.println("RSA私钥Base64编码:" + privateKeyStr);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		GenerateRsaKey demo = new GenerateRsaKey();
		demo.generateKey();
	}

}
