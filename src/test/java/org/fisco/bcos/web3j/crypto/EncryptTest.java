package org.fisco.bcos.web3j.crypto;

import static org.junit.Assert.assertTrue;

import org.fisco.bcos.web3j.crypto.gm.sm2.SM2Sign;
import org.fisco.bcos.web3j.crypto.gm.sm3.SM3Digest;
import org.junit.Test;

public class EncryptTest {

    @Test
    public void testEncryptTest() {
        HashInterface hashInterface = Hash.getHashInterface();
        SignInterface signInterface = Sign.getSignInterface();
        assertTrue(hashInterface instanceof SHA3Digest);
        assertTrue(signInterface instanceof ECDSASign);
    }

    @Test
    public void testSetECDSAEncryptTest() {
        EncryptType.setEncryptType(EncryptType.ECDSA_TYPE);
        HashInterface hashInterface = Hash.getHashInterface();
        SignInterface signInterface = Sign.getSignInterface();
        assertTrue(hashInterface instanceof SHA3Digest);
        assertTrue(signInterface instanceof ECDSASign);
    }

    @Test
    public void testSetSMEncryptTest() {
        EncryptType.setEncryptType(EncryptType.SM2_TYPE);
        HashInterface hashInterface = Hash.getHashInterface();
        SignInterface signInterface = Sign.getSignInterface();
        assertTrue(hashInterface instanceof SM3Digest);
        assertTrue(signInterface instanceof SM2Sign);
    }
}
