package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.math.BigInteger;
import org.fisco.bcos.web3j.protocol.core.Response;
import org.fisco.bcos.web3j.utils.Numeric;

/** getPbftView */
public class PbftView extends Response<String> {

  public BigInteger getPbftView() {
    return Numeric.decodeQuantity(getResult());
  }
}
