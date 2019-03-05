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
public class Int48 extends Int {
  public static final Int48 DEFAULT = new Int48(BigInteger.ZERO);

  public Int48(BigInteger value) {
    super(48, value);
  }

  public Int48(long value) {
    this(BigInteger.valueOf(value));
  }
}
