package org.bcos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import org.bcos.web3j.abi.datatypes.Ufixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link org.bcos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Ufixed96x40 extends Ufixed {
    public static final Ufixed96x40 DEFAULT = new Ufixed96x40(BigInteger.ZERO);

    public Ufixed96x40(BigInteger value) {
        super(96, 40, value);
    }

    public Ufixed96x40(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(96, 40, m, n);
    }
}
