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
public class Uint80 extends Uint {
  public static final Uint80 DEFAULT = new Uint80(BigInteger.ZERO);

  public Uint80(BigInteger value) {
    super(80, value);
  }

  public Uint80(long value) {
    this(BigInteger.valueOf(value));
  }
}
