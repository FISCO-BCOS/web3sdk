package org.fisco.bcos.channel.test.account;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.fisco.bcos.channel.client.P12Manager;
import org.fisco.bcos.channel.client.PEMManager;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AccountTest {
    private static Logger logger = LoggerFactory.getLogger(AccountTest.class);

    // openssl pkcs12 -export -name client -in "$node_dir/data/node.crt" -inkey
    // "$node_dir/data/node.key" -out "$node_dir/keystore.p12"

    public final String PRIVATE_KEY =
            "bc516b2600eec3a216f457dc14cf83a01ed22d0fc2149fc911dc2ec486fe57a3";
    public final String PUBLIC_KEY =
            "dbbfee4f76f5a3bc3dbc2e6127c4a1f50b7614bff4138a44a79aed3d42f67f9c7aa70570205f9b60a5888c6415b6a830012677b4415a79ccd1533fe5637861df";
    public final String ADDRESS = "0x0fc3c4bb89bd90299db4c62be0174c4966286c00";

    @Test
    public void accountTest()
            throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException,
                    InvalidKeySpecException, NoSuchProviderException {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "classpath:applicationContext-keystore-sample.xml");
        // test p12
        P12Manager p12 = context.getBean(P12Manager.class);
        ECKeyPair p12KeyPair = p12.getECKeyPair();
        assertEquals(p12KeyPair.getPrivateKey().toString(16), PRIVATE_KEY);
        assertEquals(p12KeyPair.getPublicKey().toString(16), PUBLIC_KEY);

        ECPublicKey publicKey = (ECPublicKey) p12.getPublicKey();
        byte[] publicKeyBytes = publicKey.getQ().getEncoded(false);
        BigInteger publicKeyValue =
                new BigInteger(1, Arrays.copyOfRange(publicKeyBytes, 1, publicKeyBytes.length));
        assertEquals(publicKeyValue.toString(16), PUBLIC_KEY);

        Credentials credentials = Credentials.create(p12KeyPair);
        assertEquals(credentials.getAddress(), ADDRESS);

        // test pem
        PEMManager pem = context.getBean(PEMManager.class);
        ECKeyPair pemKeyPair = pem.getECKeyPair();
        assertEquals(pemKeyPair.getPrivateKey().toString(16), PRIVATE_KEY);
        assertEquals(pemKeyPair.getPublicKey().toString(16), PUBLIC_KEY);

        Credentials credentialsPEM = Credentials.create(pemKeyPair);
        assertEquals(credentialsPEM.getAddress(), ADDRESS);
    }
}
