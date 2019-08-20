package org.fisco.bcos.channel.client;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class ECDSAUtil {
    public static String ecStr = "EC";

    public KeyPair getKeyPair(int bit) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ecStr);
        keyPairGenerator.initialize(bit);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    public String getPublicKey(KeyPair keyPair) {
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return byte2Base64(bytes);
    }

    public String getPrivateKey(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return byte2Base64(bytes);
    }

    public boolean publicDecrypt(byte[] encryptContent, byte[] srcContent, PublicKey publicKey)
            throws Exception {
        Signature signature = Signature.getInstance("SHA1withECDSA");
        signature.initVerify(publicKey);
        signature.update(srcContent);
        return signature.verify(encryptContent);
    }

    public byte[] privateEncrypt(byte[] content, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA1withECDSA");
        signature.initSign(privateKey);
        signature.update(content);
        return signature.sign();
    }

    public String byte2Base64(byte[] bytes) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(bytes);
    }

    public byte[] base642Byte(String base64Key) throws IOException {
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(base64Key);
    }
}
