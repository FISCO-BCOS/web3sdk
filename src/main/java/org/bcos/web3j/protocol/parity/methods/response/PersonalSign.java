package org.bcos.web3j.protocol.parity.methods.response;

import org.bcos.web3j.protocol.core.Response;

/**
 * personal_sign.
 */
public class PersonalSign extends Response<String> {
    public String getSignedMessage() {
        return getResult();
    }

}
