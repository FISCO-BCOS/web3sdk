package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

/** getNodeVersion. */
public class NodeVersion extends Response<String> {

  public String getWeb3ClientVersion() {
    return getResult();
  }
}
