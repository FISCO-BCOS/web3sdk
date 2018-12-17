package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

/**
 * eth_sign.
 */
public class EthSign extends Response<String> {
    public String getSignature() {
        return getResult();
    }
}
