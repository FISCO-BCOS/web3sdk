package org.fisco.bcos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import org.fisco.bcos.web3j.abi.datatypes.Uint;

/**
 * Auto generated code.
 *
 * <p><strong>Do not modifiy!</strong>
 *
 * <p>Please use AbiTypesGenerator in the <a
 * href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 */
public class Uint208 extends Uint {
    public static final Uint208 DEFAULT = new Uint208(BigInteger.ZERO);

    public Uint208(BigInteger value) {
        super(208, value);
    }

    public Uint208(long value) {
        this(BigInteger.valueOf(value));
    }
}
