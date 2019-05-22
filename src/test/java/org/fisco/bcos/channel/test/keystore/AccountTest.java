package org.fisco.bcos.channel.test.keystore;

import java.math.BigInteger;
import java.util.Arrays;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.fisco.bcos.channel.client.P12Manager;
import org.fisco.bcos.channel.client.PEMManager;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AccountTest {
    private static Logger logger = LoggerFactory.getLogger(AccountTest.class);

    // openssl pkcs12 -export -name client -in "$node_dir/data/node.crt" -inkey
    // "$node_dir/data/node.key" -out "$node_dir/keystore.p12"
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "classpath:applicationContext-keystore-sample.xml");

        try {
            System.out.println("Testing p12...");
            P12Manager p12 = context.getBean(P12Manager.class);
            ECKeyPair p12KeyPair = p12.getECKeyPair(p12.getPassword());

            System.out.println("p12 privateKey: " + p12KeyPair.getPrivateKey().toString(16));
            System.out.println("p12 publicKey: " + p12KeyPair.getPublicKey().toString(16));

            ECPublicKey publicKey = (ECPublicKey) p12.getPublicKey("123456");
            byte[] publicKeyBytes = publicKey.getQ().getEncoded(false);
            BigInteger publicKeyValue =
                    new BigInteger(1, Arrays.copyOfRange(publicKeyBytes, 1, publicKeyBytes.length));
            System.out.println("p12 publicKey from privateKey: " + publicKeyValue.toString(16));

            Credentials credentials = Credentials.create(p12KeyPair);
            System.out.println("p12 Address: " + credentials.getAddress());

            System.out.println("Testing pem...");
            PEMManager pem = context.getBean(PEMManager.class);
            ECKeyPair pemKeyPair = pem.getECKeyPair();

            System.out.println("PEM privateKey: " + pemKeyPair.getPrivateKey().toString(16));
            System.out.println("PEM publicKey: " + pemKeyPair.getPublicKey().toString(16));

            Credentials credentialsPEM = Credentials.create(pemKeyPair);
            System.out.println("PEM Address: " + credentialsPEM.getAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
