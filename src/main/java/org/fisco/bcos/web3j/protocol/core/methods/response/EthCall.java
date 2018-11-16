package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

/**
 * eth_call.
 */
public class EthCall extends Response<String> {
    public String getValue() {
        return getResult();
    }
}
