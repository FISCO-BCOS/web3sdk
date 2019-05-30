package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.math.BigInteger;
import org.fisco.bcos.web3j.protocol.core.Response;
import org.fisco.bcos.web3j.utils.Numeric;

/** getPendingTxSize */
public class PendingTxSize extends Response<String> {
  public BigInteger getPendingTxSize() {
    return Numeric.decodeQuantity(getResult());
  }
}
