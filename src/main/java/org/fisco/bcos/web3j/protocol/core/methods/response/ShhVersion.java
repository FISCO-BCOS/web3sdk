package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

/**
 * shh_version.
 */
public class ShhVersion extends Response<String> {

    public String getVersion() {
        return getResult();
    }
}
