package org.fisco.bcos.web3j.abi.datatypes;

import org.fisco.bcos.web3j.abi.datatypes.generated.Uint160;
import org.fisco.bcos.web3j.utils.Numeric;

import java.math.BigInteger;

/**
 * Address type, which is equivalent to uint160.
 */
public class Address implements Type<String> {

    public static final String TYPE_NAME = "address";
    public static final int LENGTH = 160;
    public static final int LENGTH_IN_HEX = LENGTH >> 2;
    public static final Address DEFAULT = new Address(BigInteger.ZERO);

    private final Uint160 value;

    public Address(Uint160 value) {
        this.value = value;
    }

    public Address(BigInteger value) {
        this(new Uint160(value));
    }

    public Address(String hexValue) {
        this(Numeric.toBigInt(hexValue));
    }

    public Uint160 toUint160() {
        return value;
    }

    @Override
    public String getTypeAsString() {
        return TYPE_NAME;
    }

    @Override
    public String toString() {
        return Numeric.toHexStringWithPrefixZeroPadded(
                value.getValue(), LENGTH_IN_HEX);
    }

    @Override
    public String getValue() {
        return toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Address address = (Address) o;

        return value != null ? value.equals(address.value) : address.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
