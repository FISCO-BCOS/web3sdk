package org.fisco.bcos.web3j.rlp;

import java.math.BigInteger;
import java.util.Arrays;

import org.fisco.bcos.web3j.utils.Numeric;

/**
 * RLP string type.
 */
public class RlpString implements RlpType {
    private static final byte[] EMPTY = new byte[]{ };

    private final byte[] value;

    private RlpString(byte[] value) {
        this.value = value;
    }

    public byte[] getBytes() {
        return value;
    }

    public BigInteger asPositiveBigInteger() {
        if (value.length == 0) {
            return BigInteger.ZERO;
        }
        return new BigInteger(1, value);
    }

    public String asString() {
        return Numeric.toHexString(value);
    }

    public static RlpString create(byte[] value) {
        return new RlpString(value);
    }

    public static RlpString create(byte value) {
        return new RlpString(new byte[]{ value });
    }

    public static RlpString create(BigInteger value) {
        // RLP encoding only supports positive integer values
        if (value.signum() < 1) {
            return new RlpString(EMPTY);
        } else {
            byte[] bytes = value.toByteArray();
            if (bytes[0] == 0) {  // remove leading zero
                return new RlpString(Arrays.copyOfRange(bytes, 1, bytes.length));
            } else {
                return new RlpString(bytes);
            }
        }
    }

    public static RlpString create(long value) {
        return create(BigInteger.valueOf(value));
    }

    public static RlpString create(String value) {
        return new RlpString(value.getBytes());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RlpString rlpString = (RlpString) o;

        return Arrays.equals(value, rlpString.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }
}
