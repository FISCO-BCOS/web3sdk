package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.util.List;

import org.fisco.bcos.web3j.protocol.core.Response;

/**
 * eth_getCode.
 */
public class NodeIDList extends Response<List<String>> {
    public List<String> getNodeIDList() {
        return getResult();
    }
}
