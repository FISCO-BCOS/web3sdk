package org.fisco.bcos.web3j.protocol.core.methods.response;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.fisco.bcos.web3j.protocol.core.Response;

public class EthConsensusStatus extends Response<String> {
    public String getEthConsensusStatus() {
        return getResult();
    }
}
