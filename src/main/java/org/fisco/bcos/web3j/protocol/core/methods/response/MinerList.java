package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

import java.util.List;

public class MinerList extends Response<List<String>> {

    public List<String> getGroupList() {
        return getResult();
    }
}
