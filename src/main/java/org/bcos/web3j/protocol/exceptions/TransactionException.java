package org.bcos.web3j.protocol.exceptions;

import java.util.Optional;

public class TransactionException extends Exception {
    private Optional<String> transactionHash = Optional.empty();

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, String transactionHash) {
        super(message);
        this.transactionHash = Optional.ofNullable(transactionHash);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }

    /**
     * Obtain the transaction hash .
     * @return optional transaction hash .
     */
    public Optional<String> getTransactionHash() {
        return transactionHash;
    }
}