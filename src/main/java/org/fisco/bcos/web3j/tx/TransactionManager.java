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

/**
 * Transaction manager abstraction for executing transactions with Ethereum client via various
 * mechanisms.
 */
public abstract class TransactionManager {

    final Credentials credentials;

    private NodeVersion.Version nodeVersion;

    public NodeVersion.Version getNodeVersion() {
        return nodeVersion;
    }

    public void setNodeVersion(NodeVersion.Version nodeVersion) {
        this.nodeVersion = nodeVersion;
    }

    protected TransactionManager(Credentials credentials) {
        this.credentials = credentials;
    }

    @Deprecated
    protected TransactionManager(
            Web3j web3j, int attempts, long sleepDuration, Credentials credentials) {
        this(credentials);
    }

    protected TransactionManager(Web3j web3j, Credentials credentials) {
        this(credentials);
    }

    protected abstract BigInteger getBlockLimit() throws IOException;

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

        return null;
    }
}
