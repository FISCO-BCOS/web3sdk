package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

/**
 * shh_addToGroup.
 */
public class ShhAddToGroup extends Response<Boolean> {

    public boolean addedToGroup() {
        return getResult();
    }
}
