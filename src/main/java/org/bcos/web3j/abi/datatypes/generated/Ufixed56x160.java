package org.bcos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import org.bcos.web3j.abi.datatypes.Ufixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link org.bcos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Ufixed56x160 extends Ufixed {
    public static final Ufixed56x160 DEFAULT = new Ufixed56x160(BigInteger.ZERO);

    public Ufixed56x160(BigInteger value) {
        super(56, 160, value);
    }

    public Ufixed56x160(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(56, 160, m, n);
    }
}
