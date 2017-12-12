package org.bcos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import org.bcos.web3j.abi.datatypes.Int;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link org.bcos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Int256 extends Int {
    public static final Int256 DEFAULT = new Int256(BigInteger.ZERO);

    public Int256(BigInteger value) {
        super(256, value);
    }

    public Int256(long value) {
        this(BigInteger.valueOf(value));
    }
}
