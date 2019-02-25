package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

/** getUninstallFilter */
public class UninstallFilter extends Response<Boolean> {
  public boolean isUninstalled() {
    return getResult();
  }
}
