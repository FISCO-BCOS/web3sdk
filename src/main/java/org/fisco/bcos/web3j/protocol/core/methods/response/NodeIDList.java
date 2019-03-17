package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.util.List;
import org.fisco.bcos.web3j.protocol.core.Response;

/** getNodeIDList */
public class NodeIDList extends Response<List<String>> {
  public List<String> getNodeIDList() {
    return getResult();
  }
}
