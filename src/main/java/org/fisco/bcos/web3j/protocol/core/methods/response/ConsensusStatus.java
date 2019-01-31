package org.fisco.bcos.web3j.protocol.core.methods.response;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.fisco.bcos.web3j.protocol.core.Response;

public class ConsensusStatus extends Response<String> {
    public String getConsensusStatus() {
        return getResult();
    }
}
