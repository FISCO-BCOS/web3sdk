package org.bcos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import org.bcos.web3j.abi.datatypes.Ufixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link org.bcos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Ufixed8x216 extends Ufixed {
    public static final Ufixed8x216 DEFAULT = new Ufixed8x216(BigInteger.ZERO);

    public Ufixed8x216(BigInteger value) {
        super(8, 216, value);
    }

    public Ufixed8x216(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(8, 216, m, n);
    }
}
