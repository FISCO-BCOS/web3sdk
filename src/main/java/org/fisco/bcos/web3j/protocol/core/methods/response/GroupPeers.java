package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.util.List;
import org.fisco.bcos.web3j.protocol.core.Response;

/** getGroupPeers */
public class GroupPeers extends Response<List<String>> {
  public List<String> getGroupPeers() {
    return getResult();
  }
}
