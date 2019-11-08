package org.fisco.bcos.web3j.protocol;

import org.fisco.bcos.web3j.protocol.core.Ethereum;
import org.fisco.bcos.web3j.protocol.core.JsonRpc2_0Web3j;

/** JSON-RPC Request object building factory. */
public interface Web3j extends Ethereum {

    static Web3j build(Web3jService web3jService, int groupId) {
        return new JsonRpc2_0Web3j(web3jService, groupId);
    }
}
