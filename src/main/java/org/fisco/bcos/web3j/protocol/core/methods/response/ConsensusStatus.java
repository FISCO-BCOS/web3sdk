package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

/** getConsensusStatus */
public class ConsensusStatus extends Response<String> {
  public String getConsensusStatus() {
    return getResult();
  }
}
