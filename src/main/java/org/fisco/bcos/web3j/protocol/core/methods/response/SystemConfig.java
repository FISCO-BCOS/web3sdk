package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

/** getSystemConfigByKey */
public class SystemConfig extends Response<String> {
  public String getSystemConfigByKey() {
    return getResult();
  }
}
