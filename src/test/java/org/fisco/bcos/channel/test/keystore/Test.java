package org.fisco.bcos.channel.test.keystore;

import java.math.BigInteger;
import java.security.PublicKey;
import java.security.Security;
import java.util.Arrays;

import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.fisco.bcos.channel.client.KeyStoreManager;
import org.fisco.bcos.channel.client.PEMLoader;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.utils.Numeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	private static Logger logger = LoggerFactory.getLogger(Test.class);
	
	// openssl pkcs12 -export -name client -in "$node_dir/data/node.crt" -inkey
	// "$node_dir/data/node.key" -out "$node_dir/keystore.p12"
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:applicationContext-keystore-sample.xml");

		try {
			System.out.println("Testing keystore...");
			KeyStoreManager ks = context.getBean(KeyStoreManager.class);
			ECKeyPair ec = ks.getECKeyPair("alice", "123456");

			System.out.println("KeyStore PrivateKey: " + Numeric.toHexStringWithPrefixZeroPadded(ec.getPrivateKey(), (32 << 1)));
			System.out.println("KeyStore PublicKey: " + Numeric.toHexStringWithPrefixZeroPadded(ec.getPublicKey(), (64 << 1)));

			ECPublicKey publicKey = (ECPublicKey) ks.getPublicKey("alice", "123456");
			byte[] publicKeyBytes = publicKey.getQ().getEncoded(false);
			BigInteger publicKeyValue = new BigInteger(1, Arrays.copyOfRange(publicKeyBytes, 1, publicKeyBytes.length));
			System.out.println(
					"KeyStore PublicKey from privatekey: " + Numeric.toHexStringWithPrefixZeroPadded(publicKeyValue, (64 << 1)));

			Credentials credentials = Credentials.create(ec);
			System.out.println("KeyStore Address: " + credentials.getAddress());
			
			
			logger.info("providers: {}", Security.getProviders());
			System.out.println("Testing pem...");
			PEMLoader pem = context.getBean(PEMLoader.class);
			ECKeyPair ecPEM = pem.getECKeyPair();
			
			System.out.println("PEM PrivateKey: " + Numeric.toHexStringWithPrefixZeroPadded(ecPEM.getPrivateKey(), (32 << 1)));
			System.out.println("PEM PublicKey: " + Numeric.toHexStringWithPrefixZeroPadded(ecPEM.getPublicKey(), (64 << 1)));
			
			Credentials credentialsPEM = Credentials.create(ecPEM);
			System.out.println("PEM Address: " + credentialsPEM.getAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
