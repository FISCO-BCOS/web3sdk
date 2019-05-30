package org.fisco.bcos.web3j.tx.response;

import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;

/** Transaction receipt processor callback. */
public interface Callback {
  void accept(TransactionReceipt transactionReceipt);

  void exception(Exception exception);
}
