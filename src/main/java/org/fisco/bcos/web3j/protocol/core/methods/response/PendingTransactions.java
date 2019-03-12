package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.util.List;
import org.fisco.bcos.web3j.protocol.core.Response;

/** getPendingTransactions */
public class PendingTransactions extends Response<List<Transaction>> {
  public List<Transaction> getPendingTransactions() {
    return getResult();
  }
}
