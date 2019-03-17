package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

/** sendTransaction. */
public class SendTransaction extends Response<String> {
  public String getTransactionHash() {
    return getResult();
  }
}
