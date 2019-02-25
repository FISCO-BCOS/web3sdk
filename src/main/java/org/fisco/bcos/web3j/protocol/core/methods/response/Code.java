package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

/** getCode. */
public class Code extends Response<String> {
  public String getCode() {
    return getResult();
  }
}
