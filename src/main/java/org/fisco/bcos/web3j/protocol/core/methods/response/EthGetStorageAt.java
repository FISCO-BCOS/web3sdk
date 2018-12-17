package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

/**
 * eth_getStorageAt.
 */
public class EthGetStorageAt extends Response<String> {
    public String getData() {
        return getResult();
    }
}
