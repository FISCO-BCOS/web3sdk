package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.util.List;

import org.fisco.bcos.web3j.protocol.core.Response;

/**
 * eth_accounts.
 */
public class EthAccounts extends Response<List<String>> {
    public List<String> getAccounts() {
        return getResult();
    }
}
