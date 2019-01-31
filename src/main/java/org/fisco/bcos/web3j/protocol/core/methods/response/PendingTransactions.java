package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

import java.util.List;

public class PendingTransactions  extends Response<List<Transaction>> {
    public List<Transaction> getPendingTransactions() {
        return getResult();
    }
}
