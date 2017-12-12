package org.bcos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import org.bcos.web3j.abi.datatypes.Ufixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link org.bcos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Ufixed64x8 extends Ufixed {
    public static final Ufixed64x8 DEFAULT = new Ufixed64x8(BigInteger.ZERO);

    public Ufixed64x8(BigInteger value) {
        super(64, 8, value);
    }

    public Ufixed64x8(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(64, 8, m, n);
    }
}
