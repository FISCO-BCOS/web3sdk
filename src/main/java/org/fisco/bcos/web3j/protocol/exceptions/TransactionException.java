package org.fisco.bcos.web3j.protocol.exceptions;

import java.math.BigInteger;
import java.util.Optional;

public class TransactionException extends Exception {
  private Optional<String> transactionHash = Optional.empty();
  private String status;
  private BigInteger gasUsed;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public BigInteger getGasUsed() {
    return gasUsed;
  }

  public void setGasUsed(BigInteger gasUsed) {
    this.gasUsed = gasUsed;
  }

  public void setTransactionHash(Optional<String> transactionHash) {
    this.transactionHash = transactionHash;
  }

  public TransactionException(String message) {
    super(message);
  }

  public TransactionException(String message, String transactionHash) {
    super(message);
    this.transactionHash = Optional.ofNullable(transactionHash);
  }

  public TransactionException(
      String message, String status, BigInteger gasUsed, String transactionHash) {
    super(message);
    this.status = status;
    this.gasUsed = gasUsed;
    this.transactionHash = Optional.ofNullable(transactionHash);
  }

  public TransactionException(Throwable cause) {
    super(cause);
  }

  /**
   * Obtain the transaction hash .
   *
   * @return optional transaction hash .
   */
  public Optional<String> getTransactionHash() {
    return transactionHash;
  }
}
