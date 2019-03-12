package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.math.BigInteger;
import org.fisco.bcos.web3j.protocol.core.Response;
import org.fisco.bcos.web3j.utils.Numeric;

/** getblockNumber. */
public class BlockNumber extends Response<String> {
  public BigInteger getBlockNumber() {
    return Numeric.decodeQuantity(getResult());
  }
}
