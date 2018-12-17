package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

/**
 * eth_submitWork.
 */
public class EthSubmitWork extends Response<Boolean> {

    public boolean solutionValid() {
        return getResult();
    }
}
