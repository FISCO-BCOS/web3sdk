package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

import java.util.List;

public class EthPeers extends Response<List<String>> {

    public List<String> getPeerList() {
        return getResult();
    }
}