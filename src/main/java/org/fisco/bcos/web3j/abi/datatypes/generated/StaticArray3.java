package org.fisco.bcos.web3j.abi.datatypes.generated;

import java.util.List;
import org.fisco.bcos.web3j.abi.datatypes.StaticArray;
import org.fisco.bcos.web3j.abi.datatypes.Type;

/**
 * Auto generated code.
 *
 * <p><strong>Do not modifiy!</strong>
 *
 * <p>Please use AbiTypesGenerator in the <a
 * href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 */
public class StaticArray3<T extends Type> extends StaticArray<T> {
  public StaticArray3(List<T> values) {
    super(3, values);
  }

  @SafeVarargs
  public StaticArray3(T... values) {
    super(3, values);
  }
}
