package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

/** getClientVersion. */
public class ClientVersion extends Response<String> {

  public String getWeb3ClientVersion() {
    return getResult();
  }
}
