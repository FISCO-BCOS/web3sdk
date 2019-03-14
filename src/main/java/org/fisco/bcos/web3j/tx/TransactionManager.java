package org.fisco.bcos.web3j.tx;

import java.io.IOException;
import java.math.BigInteger;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.SendTransaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.protocol.exceptions.TransactionException;
import org.fisco.bcos.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.fisco.bcos.web3j.tx.response.TransactionReceiptProcessor;
import org.fisco.bcos.web3j.utils.AttemptsConf;

/**
 * Transaction manager abstraction for executing transactions with Ethereum client via various
 * mechanisms.
 */
public abstract class TransactionManager {

  // configurable
  public static final int DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH = AttemptsConf.sleepDuration;
  public static final int DEFAULT_POLLING_FREQUENCY = AttemptsConf.attempts; // 15 * 100

  private final TransactionReceiptProcessor transactionReceiptProcessor;
  final Credentials credentials;

  protected TransactionManager(
      TransactionReceiptProcessor transactionReceiptProcessor, Credentials credentials) {
    this.transactionReceiptProcessor = transactionReceiptProcessor;
    this.credentials = credentials;
  }

  protected TransactionManager(Web3j web3j, Credentials credentials) {
    this(
        new PollingTransactionReceiptProcessor(
            web3j, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH ,DEFAULT_POLLING_FREQUENCY),
        credentials);
  }

  protected TransactionManager(
      Web3j web3j, int attempts, long sleepDuration, Credentials credentials) {
    this(new PollingTransactionReceiptProcessor(web3j, sleepDuration, attempts), credentials);
  }

  protected TransactionReceipt executeTransaction(
      BigInteger gasPrice, BigInteger gasLimit, String to, String data, BigInteger value)
      throws IOException, TransactionException {

    SendTransaction sendTransaction = sendTransaction(gasPrice, gasLimit, to, data, value);
    return processResponse(sendTransaction);
  }

  public abstract SendTransaction sendTransaction(
      BigInteger gasPrice, BigInteger gasLimit, String to, String data, BigInteger value)
      throws IOException;

  public SendTransaction sendTransaction(
      BigInteger gasPrice,
      BigInteger gasLimit,
      String to,
      String data,
      BigInteger value,
      TransactionSucCallback callback)
      throws IOException {
    return null;
  };

  public String getFromAddress() {
    return credentials.getAddress();
  }

  private TransactionReceipt processResponse(SendTransaction transactionResponse)
      throws IOException, TransactionException {
    if (transactionResponse.hasError()) {
      throw new RuntimeException(
          "Error processing transaction request: " + transactionResponse.getError().getMessage());
    }

    String transactionHash = transactionResponse.getTransactionHash();

    return transactionReceiptProcessor.waitForTransactionReceipt(transactionHash);
  }
}
