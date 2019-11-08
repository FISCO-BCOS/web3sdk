package org.fisco.bcos.web3j.tx;

import java.io.IOException;
import java.math.BigInteger;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ExtendedRawTransaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.SendTransaction;
import org.fisco.bcos.web3j.tx.exceptions.TxHashMismatchException;

/**
 * Transaction manager abstraction for executing transactions with Ethereum client via various
 * mechanisms.
 */
public abstract class TransactionManager {

    final Credentials credentials;

    protected TransactionManager(Credentials credentials) {
        this.credentials = credentials;
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
}
