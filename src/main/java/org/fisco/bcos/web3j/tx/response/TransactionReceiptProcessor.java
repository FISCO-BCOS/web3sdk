package org.fisco.bcos.web3j.tx.response;

import java.io.IOException;
import java.util.Optional;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosTransactionReceipt;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.protocol.exceptions.TransactionException;

/** Abstraction for managing how we wait for transaction receipts to be generated on the network. */
public abstract class TransactionReceiptProcessor {

  private final Web3j web3j;

  public TransactionReceiptProcessor(Web3j web3j) {
    this.web3j = web3j;
  }

  public abstract TransactionReceipt waitForTransactionReceipt(String transactionHash)
      throws IOException, TransactionException;

  Optional<TransactionReceipt> sendTransactionReceiptRequest(String transactionHash)
      throws IOException, TransactionException {
    BcosTransactionReceipt transactionReceipt = web3j.getTransactionReceipt(transactionHash).send();
    if (transactionReceipt.hasError()) {
      throw new TransactionException(
          "Error processing request: " + transactionReceipt.getError().getMessage());
    }

    return transactionReceipt.getTransactionReceipt();
  }
}
