package org.fisco.bcos.web3j.abi.datatypes;

import java.math.BigInteger;
import org.fisco.bcos.web3j.abi.Constant;

/** Unsigned integer type. */
public class Uint extends IntType {

    public static final String TYPE_NAME = "uint";
    public static final Uint DEFAULT = new Uint(BigInteger.ZERO);
    /** This constructor is required by the {@link Address} type. */
    Uint(String typePrefix, int bitSize, BigInteger value) {
        super(typePrefix, bitSize, value);
    }

    protected Uint(int bitSize, BigInteger value) {
        this(TYPE_NAME, bitSize, value);
    }

    public Uint(BigInteger value) {
        // "int" values should be declared as int256 in computing function selectors
        this(MAX_BIT_LENGTH, value);
    }

    /**
     * check if value between 0 ~ MAX_UINT256
     *
     * @param value
     * @return
     */
    public boolean validUint(BigInteger value) {
        return value.compareTo(BigInteger.ZERO) >= 0 && value.compareTo(Constant.MAX_UINT256) <= 0;
    }

    @Override
    boolean valid(int bitSize, BigInteger value) {
        return super.valid(bitSize, value) && value.signum() != -1 && validUint(value);
    }

    @Override
    public boolean dynamicType() {
        return false;
    }

    @Override
    public int offset() {
        return 1;
    }
}
