package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

public class BcosSubscribe extends Response<String> {
  public String getSubscriptionId() {
    return getResult();
  }
}
