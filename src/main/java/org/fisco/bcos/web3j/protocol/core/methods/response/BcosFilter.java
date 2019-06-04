package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.math.BigInteger;
import org.fisco.bcos.web3j.protocol.core.Response;
import org.fisco.bcos.web3j.utils.Numeric;

/** newBlockFilter */
public class BcosFilter extends Response<String> {
  public BigInteger getFilterId() {
    return Numeric.decodeQuantity(getResult());
  }
}
