package org.fisco.bcos.web3j.crypto;

import java.security.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.fisco.bcos.web3j.crypto.gm.sm2.SM2Sign;
import org.fisco.bcos.web3j.crypto.gm.sm3.SM3Digest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptType {

    private static final Logger logger = LoggerFactory.getLogger(EncryptType.class);

    public static final int ECDSA_TYPE = 0;
    public static final int SM2_TYPE = 1;

    public static int encryptType = ECDSA_TYPE; // 0:ECDSA 1:SM2

    public EncryptType(int encryptType) {
        resetEncryptType(encryptType);
    }

    public void resetEncryptType(int encryptType) {
        EncryptType.encryptType = encryptType;
        SignInterface signInterface;
        HashInterface hashInterface;
        if (encryptType == SM2_TYPE) {
            signInterface = new SM2Sign();
            hashInterface = new SM3Digest();
        } else {
            signInterface = new ECDSASign();
            hashInterface = new SHA3Digest();
        }
        Sign.setSignInterface(signInterface);
        Hash.setHashInterface(hashInterface);
    }

    public static void checkAccountPrivateKey(ECParameterSpec ecParameterSpec, int encryptType) {
        try {
            String name = ((ECNamedCurveSpec) ecParameterSpec).getName();
            boolean privateKeyMatch =
                    ((EncryptType.ECDSA_TYPE != EncryptType.encryptType) && name.contains("sm2"))
                            || ((EncryptType.ECDSA_TYPE == EncryptType.encryptType)
                                    && name.contains("secp256k1"));

            // logger.trace(" name: {}, encrypt: {}", name, encryptType);

            if (!privateKeyMatch) {
                throw new IllegalArgumentException(
                        " Not matched private key type, type name: "
                                + name
                                + ", encryptType: "
                                + EncryptType.encryptType);
            }
        } catch (Exception e) {
            logger.error(" exception: {}", e);
        }
    }

    public int getEncryptType() {
        return encryptType;
    }

    public void setEncryptType(int encryptType) {
        resetEncryptType(encryptType);
    }
}
