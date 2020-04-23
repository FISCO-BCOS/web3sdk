package org.fisco.bcos.web3j.crypto;

import static org.junit.Assert.assertEquals;

import org.fisco.bcos.channel.client.P12Manager;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.crypto.tool.ECCDecrypt;
import org.fisco.bcos.web3j.crypto.tool.ECCEncrypt;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ECKeyPairTest {

    @Test
    public void encryptECKeyPairTest() throws Exception {
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();

        ECCEncrypt encrypt = new ECCEncrypt(ecKeyPair.getPublicKey());
        ECCDecrypt decrypt = new ECCDecrypt(ecKeyPair.getPrivateKey());

        String message = "ecc encrypt test";
        byte[] encryptData = encrypt.encrypt(message.getBytes("utf-8"));
        byte[] decryptData = decrypt.decrypt(encryptData);

        String decrpptMessage = new String(decryptData, "utf-8");
        assertEquals(message, decrpptMessage);
    }

    @Test
    public void encryptECKeyPairTestEmptyString() throws Exception {
        Credentials credentials = GenCredential.create();
        ECKeyPair ecKeyPair = credentials.getEcKeyPair();

        ECCEncrypt encrypt = new ECCEncrypt(ecKeyPair.getPublicKey());
        ECCDecrypt decrypt = new ECCDecrypt(ecKeyPair.getPrivateKey());

        String message = "";
        byte[] encryptData = encrypt.encrypt(message.getBytes("utf-8"));
        byte[] decryptData = decrypt.decrypt(encryptData);

        assertEquals(message, new String(decryptData, "utf-8"));
    }

    @Test
    public void encryptECKeyPairTestWithPem() throws Exception {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "classpath:applicationContext-keystore-sample.xml");
        // test p12
        P12Manager p12 = context.getBean(P12Manager.class);

        ECKeyPair ecKeyPair = p12.getECKeyPair();

        ECCEncrypt encrypt = new ECCEncrypt(ecKeyPair.getPublicKey());
        ECCDecrypt decrypt = new ECCDecrypt(ecKeyPair.getPrivateKey());

        String message = "";
        byte[] encryptData = encrypt.encrypt(message.getBytes("utf-8"));
        byte[] decryptData = decrypt.decrypt(encryptData);

        assertEquals(message, new String(decryptData, "utf-8"));
    }

    @Test
    public void verifyECKeyPairTest() throws Exception {
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        String message = "verify test";
        ECDSASignature signature = ecKeyPair.sign(message.getBytes());
        boolean verify = ecKeyPair.verify(message.getBytes(), signature);

        assertEquals(verify, true);
    }

    @Test
    public void verifyECKeyPairTestEmptyString() throws Exception {
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        String message = "";
        ECDSASignature signature = ecKeyPair.sign(message.getBytes());
        boolean verify = ecKeyPair.verify(message.getBytes(), signature);

        assertEquals(verify, true);
    }

    @Test
    public void verifyECKeyPairTestWithPem() throws Exception {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "classpath:applicationContext-keystore-sample.xml");
        // test p12
        P12Manager p12 = context.getBean(P12Manager.class);

        ECKeyPair ecKeyPair = p12.getECKeyPair();

        String message = "ecc encrypt test";
        ECDSASignature signature = ecKeyPair.sign(message.getBytes());
        boolean verify = ecKeyPair.verify(message.getBytes(), signature);

        assertEquals(verify, true);
    }

    @Test
    public void verifyECDSASignTest() throws Exception {
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        ECDSASign ecdsaSign = new ECDSASign();
        String message = "message";
        Sign.SignatureData signatureData = ecdsaSign.signMessage(message.getBytes(), ecKeyPair);

        SHA3Digest sha3Digest = new SHA3Digest();
        byte[] hash = sha3Digest.hash(message.getBytes());

        boolean verify = ecdsaSign.verify(hash, ecKeyPair.getPublicKey(), signatureData);

        assertEquals(verify, true);
    }

    @Test
    public void verifySecp256ECDSASignTest() throws Exception {
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        ECDSASign ecdsaSign = new ECDSASign();
        String message = "message";
        Sign.SignatureData signatureData =
                ecdsaSign.secp256SignMessage(message.getBytes(), ecKeyPair);

        SHA3Digest sha3Digest = new SHA3Digest();
        byte[] hash = sha3Digest.hash(message.getBytes());

        boolean verify = ecdsaSign.secp256Verify(hash, ecKeyPair.getPublicKey(), signatureData);

        assertEquals(verify, true);
    }

    @Test
    public void verifyECDSASignTest0() throws Exception {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "classpath:applicationContext-keystore-sample.xml");
        // test p12
        P12Manager p12 = context.getBean(P12Manager.class);
        ECKeyPair ecKeyPair = p12.getECKeyPair();

        ECDSASign ecdsaSign = new ECDSASign();
        String message = "message";
        Sign.SignatureData signatureData = ecdsaSign.signMessage(message.getBytes(), ecKeyPair);

        SHA3Digest sha3Digest = new SHA3Digest();
        byte[] hash = sha3Digest.hash(message.getBytes());

        boolean verify = ecdsaSign.verify(hash, ecKeyPair.getPublicKey(), signatureData);

        assertEquals(verify, true);
    }

    @Test
    public void verifySecp256ECDSASignTest0() throws Exception {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "classpath:applicationContext-keystore-sample.xml");
        // test p12
        P12Manager p12 = context.getBean(P12Manager.class);
        ECKeyPair ecKeyPair = p12.getECKeyPair();

        ECDSASign ecdsaSign = new ECDSASign();
        String message = "message";
        Sign.SignatureData signatureData =
                ecdsaSign.secp256SignMessage(message.getBytes(), ecKeyPair);

        SHA3Digest sha3Digest = new SHA3Digest();
        byte[] hash = sha3Digest.hash(message.getBytes());

        boolean verify = ecdsaSign.secp256Verify(hash, ecKeyPair.getPublicKey(), signatureData);

        assertEquals(verify, true);
    }

    @Test
    public void verifyECDSASignTest1() throws Exception {

        ECKeyPair ecKeyPair = Keys.createEcKeyPair();

        ECDSASign ecdsaSign = new ECDSASign();
        String message = "message";
        Sign.SignatureData signatureData = ecdsaSign.signMessage(message.getBytes(), ecKeyPair);

        SHA3Digest sha3Digest = new SHA3Digest();
        byte[] hash = sha3Digest.hash(message.getBytes());

        boolean verify = ecdsaSign.verify(hash, ecKeyPair.getPublicKey(), signatureData);

        assertEquals(verify, true);
    }

    @Test
    public void verifySecp256ECDSASignTest1() throws Exception {

        ECKeyPair ecKeyPair = Keys.createEcKeyPair();

        ECDSASign ecdsaSign = new ECDSASign();
        String message = "message";
        Sign.SignatureData signatureData =
                ecdsaSign.secp256SignMessage(message.getBytes(), ecKeyPair);

        SHA3Digest sha3Digest = new SHA3Digest();
        byte[] hash = sha3Digest.hash(message.getBytes());

        boolean verify = ecdsaSign.secp256Verify(hash, ecKeyPair.getPublicKey(), signatureData);

        assertEquals(verify, true);
    }
}
