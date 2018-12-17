package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

/**
 * eth_mining.
 */
public class EthMining extends Response<Boolean> {
    public boolean isMining() {
        return getResult();
    }
}
