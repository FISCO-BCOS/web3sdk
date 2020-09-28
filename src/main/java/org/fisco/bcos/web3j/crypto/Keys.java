package org.fisco.bcos.web3j.crypto;

import static org.fisco.bcos.web3j.crypto.SecureRandomUtils.secureRandom;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.fisco.bcos.web3j.utils.Numeric;
import org.fisco.bcos.web3j.utils.Strings;

/** Crypto key utilities. */
public class Keys {

    static final int PRIVATE_KEY_SIZE = 32;
    static final int PUBLIC_KEY_SIZE = 64;

    public static final int ADDRESS_SIZE = 160;
    public static final int ADDRESS_LENGTH_IN_HEX = ADDRESS_SIZE >> 2;

    public static final int PUBLIC_KEY_LENGTH_IN_HEX = PUBLIC_KEY_SIZE << 1;
    public static final int PUBLIC_KEY_LENGTH_IN_HEX_WITH_COMPRESS_FLAG_1 =
            (PUBLIC_KEY_LENGTH_IN_HEX + 1);
    public static final int PUBLIC_KEY_LENGTH_IN_HEX_WITH_COMPRESS_FLAG_2 =
            (PUBLIC_KEY_LENGTH_IN_HEX + 2);
    public static final int PRIVATE_KEY_LENGTH_IN_HEX = PRIVATE_KEY_SIZE << 1;

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    private Keys() {}

    /**
     * Create a keypair using SECP-256k1 curve.
     *
     * <p>Private keypairs are encoded using PKCS8
     *
     * <p>Private keys are encoded using X.509
     */
    /**
     * Create a keypair using SECP-256k1 curve.
     *
     * <p>Private keypairs are encoded using PKCS8
     *
     * <p>Private keys are encoded using X.509
     */
    static KeyPair createSecp256k1KeyPair()
            throws NoSuchProviderException, NoSuchAlgorithmException,
                    InvalidAlgorithmParameterException {
        return createSecp256k1KeyPair(secureRandom());
    }

    static KeyPair createSecp256k1KeyPair(SecureRandom random)
            throws NoSuchProviderException, NoSuchAlgorithmException,
                    InvalidAlgorithmParameterException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
        ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("secp256k1");
        if (random != null) {
            keyPairGenerator.initialize(ecGenParameterSpec, random);
        } else {
            keyPairGenerator.initialize(ecGenParameterSpec);
        }
        return keyPairGenerator.generateKeyPair();
    }

    public static ECKeyPair createEcKeyPair()
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
                    NoSuchProviderException {
        return createEcKeyPair(secureRandom());
    }

    public static ECKeyPair createEcKeyPair(SecureRandom random)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
                    NoSuchProviderException {
        KeyPair keyPair = createSecp256k1KeyPair(random);
        return ECKeyPair.create(keyPair);
    }

    public static String getAddress(ECKeyPair ecKeyPair) {
        return getAddress(ecKeyPair.getPublicKey());
    }

    public static String getAddress(BigInteger publicKey) {
        return getAddress(Numeric.toHexStringNoPrefix(publicKey));
    }

    public static String getAddress(String publicKey) {
        String publicKeyNoPrefix = Numeric.cleanHexPrefix(publicKey);

        if (publicKeyNoPrefix.length() < PUBLIC_KEY_LENGTH_IN_HEX) {
            publicKeyNoPrefix =
                    Strings.zeros(PUBLIC_KEY_LENGTH_IN_HEX - publicKeyNoPrefix.length())
                            + publicKeyNoPrefix;
        } else if (publicKeyNoPrefix.length() == PUBLIC_KEY_LENGTH_IN_HEX) {
            // do nothing
        } else if (publicKeyNoPrefix.length() == PUBLIC_KEY_LENGTH_IN_HEX_WITH_COMPRESS_FLAG_2) {
            // 130 length,should start with 03 or 04
            if (!(publicKeyNoPrefix.startsWith("03") || publicKeyNoPrefix.startsWith("04"))) {
                throw new IllegalArgumentException(
                        " publicKey not start with \"03\" or \"04\", publicKey: " + publicKey);
            }
            publicKeyNoPrefix = publicKeyNoPrefix.substring(2);
        } else if (publicKeyNoPrefix.length() == PUBLIC_KEY_LENGTH_IN_HEX_WITH_COMPRESS_FLAG_1) {
            // 129 length,should start with 3 or 4
            if (!(publicKeyNoPrefix.startsWith("3") || publicKeyNoPrefix.startsWith("4"))) {
                throw new IllegalArgumentException(
                        " publicKey not start with \"3\" or \"4\", publicKey: " + publicKey);
            }
            publicKeyNoPrefix = publicKeyNoPrefix.substring(1);
        } else {
            // invalid public key
            throw new IllegalArgumentException(" invalid publicKey: " + publicKey);
        }

        String hash = Hash.sha3(publicKeyNoPrefix);
        return hash.substring(hash.length() - ADDRESS_LENGTH_IN_HEX); // right most 160 bits
    }

    public static byte[] getAddress(byte[] publicKey) {
        byte[] hash = Hash.sha3(publicKey);
        return Arrays.copyOfRange(hash, hash.length - 20, hash.length); // right most 160 bits
    }

    /**
     * Checksum address encoding as per <a
     * href="https://github.com/ethereum/EIPs/blob/master/EIPS/eip-55.md">EIP-55</a>.
     *
     * @param address a valid hex encoded address
     * @return hex encoded checksum address
     */
    public static String toChecksumAddress(String address) {
        String lowercaseAddress = Numeric.cleanHexPrefix(address).toLowerCase();
        String addressHash = Numeric.cleanHexPrefix(Hash.sha3String(lowercaseAddress));

        StringBuilder result = new StringBuilder(lowercaseAddress.length() + 2);

        result.append("0x");

        for (int i = 0; i < lowercaseAddress.length(); i++) {
            if (Integer.parseInt(String.valueOf(addressHash.charAt(i)), 16) >= 8) {
                result.append(String.valueOf(lowercaseAddress.charAt(i)).toUpperCase());
            } else {
                result.append(lowercaseAddress.charAt(i));
            }
        }

        return result.toString();
    }

    public static byte[] serialize(ECKeyPair ecKeyPair) {
        byte[] privateKey = Numeric.toBytesPadded(ecKeyPair.getPrivateKey(), PRIVATE_KEY_SIZE);
        byte[] publicKey = Numeric.toBytesPadded(ecKeyPair.getPublicKey(), PUBLIC_KEY_SIZE);

        byte[] result = Arrays.copyOf(privateKey, PRIVATE_KEY_SIZE + PUBLIC_KEY_SIZE);
        System.arraycopy(publicKey, 0, result, PRIVATE_KEY_SIZE, PUBLIC_KEY_SIZE);
        return result;
    }

    public static ECKeyPair deserialize(byte[] input) {
        if (input.length != PRIVATE_KEY_SIZE + PUBLIC_KEY_SIZE) {
            throw new RuntimeException("Invalid input key size");
        }

        BigInteger privateKey = Numeric.toBigInt(input, 0, PRIVATE_KEY_SIZE);
        BigInteger publicKey = Numeric.toBigInt(input, PRIVATE_KEY_SIZE, PUBLIC_KEY_SIZE);

        return new ECKeyPair(privateKey, publicKey);
    }
}
