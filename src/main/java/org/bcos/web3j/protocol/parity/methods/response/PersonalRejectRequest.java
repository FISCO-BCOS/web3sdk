package org.bcos.web3j.protocol.parity.methods.response;

import org.bcos.web3j.protocol.core.Response;

/**
 * personal_rejectRequest.
 */
public class PersonalRejectRequest extends Response<Boolean> {
    public boolean isRejected() {
        return getResult();
    }
}
