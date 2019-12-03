package org.fisco.bcos.web3j.crypto;

import org.fisco.bcos.web3j.crypto.gm.sm2.SM2Sign;
import org.fisco.bcos.web3j.crypto.gm.sm3.SM3Digest;

public class EncryptType {

    public static final int ECDSA_TYPE = 0;
    public static final int SM2_TYPE = 1;

    public static int encryptType = ECDSA_TYPE; // 0:ECDSA 1:SM2

    public EncryptType(int encryptType) {
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

    public int getEncryptType() {
        return encryptType;
    }

    public void setEncryptType(int encryptType) {
        EncryptType.encryptType = encryptType;
    }
}
