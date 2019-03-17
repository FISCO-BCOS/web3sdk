package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.util.List;
import org.fisco.bcos.web3j.protocol.core.Response;

/** getGroupList */
public class GroupList extends Response<List<String>> {

  public List<String> getGroupList() {
    return getResult();
  }
}
