package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

public class BlockHash extends Response<String>{
	public String getBlockHashByNumber() {
        return getResult();
    }
}
