package org.bcos.web3j.tx;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.bcos.web3j.protocol.core.methods.response.EthSendTransaction;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.bcos.web3j.protocol.exceptions.TransactionTimeoutException;

/**
 * Transaction manager abstraction for executing transactions with Ethereum client via
 * various mechanisms.
 */
public abstract class TransactionManager {

    private static final int SLEEP_DURATION = 1500;
    private static final int ATTEMPTS = 10;

    private final int sleepDuration;
    private final int attempts;

    private final Web3j web3j;

    protected TransactionManager(Web3j web3j) {
        this.web3j = web3j;
        this.attempts = ATTEMPTS;
        this.sleepDuration = SLEEP_DURATION;
    }

    protected TransactionManager(Web3j web3j, int attempts, int sleepDuration) {
        this.web3j = web3j;
        this.attempts = attempts;
        this.sleepDuration = sleepDuration;
    }

    TransactionReceipt executeTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value, BigInteger type, boolean isInitByName)
            throws InterruptedException, IOException, TransactionTimeoutException {

        EthSendTransaction ethSendTransaction = sendTransaction(
                gasPrice, gasLimit, to, data, value, type, isInitByName);
        return processResponse(ethSendTransaction);
    }

    void executeTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value, BigInteger type, boolean isInitByName, TransactionSucCallback callback)
            throws InterruptedException, IOException, TransactionTimeoutException {

        sendTransaction(gasPrice, gasLimit, to, data, value,type,isInitByName, callback);
    }

    public abstract EthSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value, BigInteger type, boolean isInitByName)
            throws IOException;

    public abstract EthSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value, BigInteger type, boolean isInitByName,TransactionSucCallback callback)
            throws IOException;
            
    public abstract String getFromAddress();

    private TransactionReceipt processResponse(EthSendTransaction transactionResponse)
            throws InterruptedException, IOException, TransactionTimeoutException {
        if (transactionResponse.hasError()) {
            throw new RuntimeException("Error processing transaction request: "
                    + transactionResponse.getError().getMessage());
        }

        String transactionHash = transactionResponse.getTransactionHash();

        return waitForTransactionReceipt(transactionHash);
    }

    private TransactionReceipt waitForTransactionReceipt(
            String transactionHash)
            throws InterruptedException, IOException, TransactionTimeoutException {

        return getTransactionReceipt(transactionHash, sleepDuration, attempts);
    }

    private TransactionReceipt getTransactionReceipt(
            String transactionHash, int sleepDuration, int attempts)
            throws IOException, InterruptedException, TransactionTimeoutException {

        Optional<TransactionReceipt> receiptOptional =
                sendTransactionReceiptRequest(transactionHash);
        int sumTime = 0;
        for (int i = 0; i < attempts; i++) {

            if (!receiptOptional.isPresent()) {
                Thread.sleep(sleepDuration*(i*i)+sleepDuration);
                sumTime += (sleepDuration*(i*i)+sleepDuration);
                receiptOptional = sendTransactionReceiptRequest(transactionHash);
            } else {
                return receiptOptional.get();
            }
        }

        throw new TransactionTimeoutException("Transaction receipt was not generated after "
                + ((sumTime) / 1000
                + " seconds for transaction: " + transactionHash));
    }

    private Optional<TransactionReceipt> sendTransactionReceiptRequest(
            String transactionHash) throws IOException {
        EthGetTransactionReceipt transactionReceipt =
                web3j.ethGetTransactionReceipt(transactionHash).send();
        if (transactionReceipt.hasError()) {
            throw new RuntimeException("Error processing request: "
                    + transactionReceipt.getError().getMessage());
        }

        return transactionReceipt.getTransactionReceipt();
    }
}
