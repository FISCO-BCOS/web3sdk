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
public class Int96 extends Int {
  public static final Int96 DEFAULT = new Int96(BigInteger.ZERO);

  public Int96(BigInteger value) {
    super(96, value);
  }

  public Int96(long value) {
    this(BigInteger.valueOf(value));
  }
}
