package org.bcos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import org.bcos.web3j.abi.datatypes.Int;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link org.bcos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Int96 extends Int {
    public static final Int96 DEFAULT = new Int96(BigInteger.ZERO);

    public Int96(BigInteger value) {
        super(96, value);
    }

    public Int96(long value) {
        this(BigInteger.valueOf(value));
    }
}
