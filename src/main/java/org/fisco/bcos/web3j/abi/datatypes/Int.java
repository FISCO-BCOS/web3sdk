package org.fisco.bcos.web3j.abi.datatypes;

import java.math.BigInteger;
import org.fisco.bcos.web3j.abi.Constant;

/** Integer type. */
public class Int extends IntType {

    public static final String TYPE_NAME = "int";
    public static final Int DEFAULT = new Int(BigInteger.ZERO);

    public Int(BigInteger value) {
        // "int" values should be declared as int256 in computing function selectors
        this(MAX_BIT_LENGTH, value);
    }

    /**
     * check if value between MIN_INT256 ~ MIN_INT256
     *
     * @param value
     * @return
     */
    public boolean validInt(BigInteger value) {
        return value.compareTo(Constant.MIN_INT256) >= 0
                && value.compareTo(Constant.MAX_INT256) <= 0;
    }

    @Override
    boolean valid(int bitSize, BigInteger value) {
        return super.valid(bitSize, value) && validInt(value);
    }

    protected Int(int bitSize, BigInteger value) {
        super(TYPE_NAME, bitSize, value);
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
