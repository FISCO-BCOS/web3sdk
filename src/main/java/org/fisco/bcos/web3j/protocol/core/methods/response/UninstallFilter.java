package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

/** getUninstallFilter */
@Deprecated
public class UninstallFilter extends Response<Boolean> {
    public boolean isUninstalled() {
        return getResult();
    }
}
