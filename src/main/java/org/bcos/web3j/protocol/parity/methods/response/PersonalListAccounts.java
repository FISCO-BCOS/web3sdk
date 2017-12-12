package org.bcos.web3j.protocol.parity.methods.response;

import java.util.List;

import org.bcos.web3j.protocol.core.Response;

/**
 * personal_listAccounts.
 */
public class PersonalListAccounts extends Response<List<String>> {
    public List<String> getAccountIds() {
        return getResult();
    }
}
