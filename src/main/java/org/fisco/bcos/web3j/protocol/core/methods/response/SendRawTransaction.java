package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

/** sendRawTransaction. */
public class SendRawTransaction extends Response<String> {
  public String getTransactionHash() {
    return getResult();
  }
}
