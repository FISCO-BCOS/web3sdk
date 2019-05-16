package org.fisco.bcos.channel.test.keystore;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import org.fisco.bcos.channel.client.KeyStoreManager;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	// openssl pkcs12 -export -name client -in "$node_dir/data/node.crt" -inkey "$node_dir/data/node.key" -out "$node_dir/keystore.p12"
	public static void main(String[] args) {
		ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext-keystore-sample.xml");
        KeyStoreManager ks = context.getBean(KeyStoreManager.class);
        
        try {
			ECKeyPair ec = ks.getECKeyPair("demo", "123456");
			
			//System.out.println(ec.getPublicKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}

}
