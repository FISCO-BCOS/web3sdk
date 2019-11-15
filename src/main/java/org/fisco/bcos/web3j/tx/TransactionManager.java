package org.fisco.bcos.web3j.tx;

import java.io.IOException;
import java.math.BigInteger;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ExtendedRawTransaction;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.NodeVersion;
import org.fisco.bcos.web3j.protocol.core.methods.response.SendTransaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.protocol.exceptions.TransactionException;
import org.fisco.bcos.web3j.tx.exceptions.TxHashMismatchException;
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

    private NodeVersion.Version nodeVersion;

    public NodeVersion.Version getNodeVersion() {
        return nodeVersion;
    }

    public void setNodeVersion(NodeVersion.Version nodeVersion) {
        this.nodeVersion = nodeVersion;
    }

    protected TransactionManager(
            TransactionReceiptProcessor transactionReceiptProcessor, Credentials credentials) {
        this.transactionReceiptProcessor = transactionReceiptProcessor;
        this.credentials = credentials;
    }

    protected TransactionManager(Web3j web3j, Credentials credentials) {
        this(
                new PollingTransactionReceiptProcessor(
                        web3j, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH, DEFAULT_POLLING_FREQUENCY),
                credentials);
    }

    protected TransactionManager(
            Web3j web3j, int attempts, long sleepDuration, Credentials credentials) {
        this(new PollingTransactionReceiptProcessor(web3j, sleepDuration, attempts), credentials);
    }

    @Deprecated
    protected TransactionReceipt executeTransaction(
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            String data,
            BigInteger value,
            String extraData)
            throws IOException, TransactionException {

        SendTransaction sendTransaction =
                sendTransaction(gasPrice, gasLimit, to, data, value, extraData);
        return processResponse(sendTransaction);
    }

    public abstract SendTransaction sendTransaction(
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            String data,
            BigInteger value,
            String extraData)
            throws IOException;

    public SendTransaction sendTransaction(
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            String data,
            BigInteger value,
            String extraData,
            TransactionSucCallback callback)
            throws IOException {
        return null;
    };

    public SendTransaction sendTransaction(String signedTransaction)
            throws IOException, TxHashMismatchException {
        return null;
    }

    public SendTransaction sendTransaction(
            String signedTransaction, TransactionSucCallback callback)
            throws IOException, TxHashMismatchException {
        return null;
    }

    public ExtendedRawTransaction createTransaction(
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            String data,
            BigInteger value,
            String extraData)
            throws IOException {
        return null;
    }

    public String sign(ExtendedRawTransaction transaction) {
        return null;
    }

    public String getFromAddress() {
        return credentials.getAddress();
    }

    @Deprecated
    private TransactionReceipt processResponse(SendTransaction transactionResponse)
            throws IOException, TransactionException {
        if (transactionResponse.hasError()) {
            throw new RuntimeException(
                    "Error processing transaction request: "
                            + transactionResponse.getError().getMessage());
        }

        String transactionHash = transactionResponse.getTransactionHash();

        return transactionReceiptProcessor.waitForTransactionReceipt(transactionHash);
    }
}
