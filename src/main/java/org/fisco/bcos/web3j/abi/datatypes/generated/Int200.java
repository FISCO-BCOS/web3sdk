package org.fisco.bcos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import org.fisco.bcos.web3j.abi.datatypes.Int;

/**
 * Auto generated code.
 *
 * <p><strong>Do not modifiy!</strong>
 *
 * <p>Please use AbiTypesGenerator in the <a
 * href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 */
public class Int200 extends Int {
    public static final Int200 DEFAULT = new Int200(BigInteger.ZERO);

    public Int200(BigInteger value) {
        super(200, value);
    }

    public Int200(long value) {
        this(BigInteger.valueOf(value));
    }
}
