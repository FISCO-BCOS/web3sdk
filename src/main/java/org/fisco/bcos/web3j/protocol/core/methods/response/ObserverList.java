package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.util.List;
import org.fisco.bcos.web3j.protocol.core.Response;

public class ObserverList extends Response<List<String>> {

    public List<String> getObserverList() {
        return getResult();
    }
}
