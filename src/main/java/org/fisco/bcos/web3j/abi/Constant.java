package org.fisco.bcos.web3j.abi;

import java.math.BigInteger;

public class Constant {
    public static final BigInteger MAX_UINT256 =
            new BigInteger("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", 16);
    public static final BigInteger MAX_INT256 =
            new BigInteger("7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", 16);
    public static final BigInteger MIN_INT256 =
            new BigInteger("-7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", 16);
}
