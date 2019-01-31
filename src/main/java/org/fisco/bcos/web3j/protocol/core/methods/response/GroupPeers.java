package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

import java.util.List;

/**
 * getGroupPeers
 */
public class GroupPeers extends Response<List<String>> {
    public List<String> getGroupPeers() {
        return getResult();
    }
}
