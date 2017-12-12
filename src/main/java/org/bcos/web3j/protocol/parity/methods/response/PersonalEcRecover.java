package org.bcos.web3j.protocol.parity.methods.response;

import org.bcos.web3j.protocol.core.Response;

/**
 * personal_ecRecover.
 */
public class PersonalEcRecover extends Response<String> {
    public String getRecoverAccountId() {
        return getResult();
    }
}
