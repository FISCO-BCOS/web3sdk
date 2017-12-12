package org.bcos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import org.bcos.web3j.abi.datatypes.Ufixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link org.bcos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Ufixed96x128 extends Ufixed {
    public static final Ufixed96x128 DEFAULT = new Ufixed96x128(BigInteger.ZERO);

    public Ufixed96x128(BigInteger value) {
        super(96, 128, value);
    }

    public Ufixed96x128(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(96, 128, m, n);
    }
}
