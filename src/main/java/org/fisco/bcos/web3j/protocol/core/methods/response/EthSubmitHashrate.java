package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

/**
 * eth_submitHashrate.
 */
public class EthSubmitHashrate extends Response<Boolean> {

    public boolean submissionSuccessful() {
        return getResult();
    }
}
