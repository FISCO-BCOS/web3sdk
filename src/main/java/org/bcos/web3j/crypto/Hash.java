package org.bcos.web3j.crypto;

import org.bcos.web3j.crypto.sm3.SM3Digest;
import org.bouncycastle.jcajce.provider.digest.Keccak;

import org.bcos.web3j.utils.Numeric;

/**
 * Crypto related functions.
 */
public class Hash {
    private static HashInterface hashInterface = new SHA3Digest();
    public static HashInterface getHashInterface() {
        return hashInterface;
    }

    public static void setHashInterface(HashInterface hashInterface) {
        Hash.hashInterface = hashInterface;
    }


    /**
     * Keccak-256 hash function.
     *
     * @param hexInput hex encoded input data with optional 0x prefix
     * @return hash value as hex encoded string
     */
    public static String sha3(String hexInput) {
        return hashInterface.hash(hexInput);
    }

    /**
     * Keccak-256 hash function.
     *
     * @param input binary encoded input data
     * @param offset of start of data
     * @param length of data
     * @return hash value
     */
    public static byte[] sha3(byte[] input, int offset, int length) {
        return hashInterface.hash(input,offset,length);
    }

    public static byte[] sha3(byte[] input) {
        return hashInterface.hash(input,0,input.length);
    }
}
