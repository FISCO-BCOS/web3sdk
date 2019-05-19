package org.fisco.bcos.channel.test.keystore;

import java.math.BigInteger;
import java.security.PublicKey;
import java.util.Arrays;

import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.fisco.bcos.channel.client.KeyStoreManager;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.utils.Numeric;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    // openssl pkcs12 -export -name client -in "$node_dir/data/node.crt" -inkey
    // "$node_dir/data/node.key" -out "$node_dir/keystore.p12"
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "classpath:applicationContext-keystore-sample.xml");
        KeyStoreManager ks = context.getBean(KeyStoreManager.class);

        try {
	    ECKeyPair ec = ks.getECKeyPair("alice", "123456");
	    
	    System.out.println("PrivateKey: " + Numeric.toHexStringWithPrefixZeroPadded(ec.getPrivateKey(), (32 << 1)));
	    System.out.println("PublicKey: " + Numeric.toHexStringWithPrefixZeroPadded(ec.getPublicKey(), (64 << 1)));
	    
	    ECPublicKey publicKey = (ECPublicKey) ks.getPublicKeyFromPrivateKey("alice", "123456");
	    byte[] publicKeyBytes = publicKey.getQ().getEncoded(false);
	    BigInteger publicKeyValue = new BigInteger(1, Arrays.copyOfRange(publicKeyBytes, 1, publicKeyBytes.length));
	    System.out.println("PublicKey from privatekey: " + Numeric.toHexStringWithPrefixZeroPadded(publicKeyValue, (64 << 1)));
	    
	    Credentials credentials = Credentials.create(ec);
	    System.out.println("Address: " + credentials.getAddress());
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
