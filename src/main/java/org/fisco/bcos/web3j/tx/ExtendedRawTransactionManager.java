package org.fisco.bcos.web3j.tx;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ExtendedRawTransaction;
import org.fisco.bcos.web3j.crypto.ExtendedTransactionEncoder;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.Request;
import org.fisco.bcos.web3j.protocol.core.methods.response.SendTransaction;
import org.fisco.bcos.web3j.tx.exceptions.TxHashMismatchException;
import org.fisco.bcos.web3j.utils.Numeric;
import org.fisco.bcos.web3j.utils.TxHashVerifier;

/**
 * TransactionManager implementation using Ethereum wallet file to create and sign transactions
 * locally.
 *
 * <p>This transaction manager provides support for specifying the chain id for transactions as per
 * <a href="https://github.com/ethereum/EIPs/issues/155">EIP155</a>.
 */
public class ExtendedRawTransactionManager extends TransactionManager {
    private final Web3j web3j;
    final Credentials credentials;

    private final byte chainId;

    private final BigInteger groupId;
    private final BigInteger fiscoChainId;

    protected TxHashVerifier txHashVerifier = new TxHashVerifier();

    public ExtendedRawTransactionManager(
            Web3j web3j,
            Credentials credentials,
            byte chainId,
            BigInteger groupId,
            BigInteger fiscoChainId) {
        super(credentials);
        this.web3j = web3j;
        this.credentials = credentials;
        this.chainId = chainId;
        this.groupId = groupId;
        this.fiscoChainId = fiscoChainId;
    }

    public ExtendedRawTransactionManager(
            Web3j web3j,
            Credentials credentials,
            byte chainId,
            int attempts,
            int sleepDuration,
            BigInteger groupId,
            BigInteger fiscoChainId) {
        super(credentials);
        this.web3j = web3j;
        this.credentials = credentials;
        this.chainId = chainId;
        this.groupId = groupId;
        this.fiscoChainId = fiscoChainId;
    }

    public ExtendedRawTransactionManager(
            Web3j web3j, Credentials credentials, BigInteger groupId, BigInteger fiscoChainId) {
        this(web3j, credentials, ChainId.NONE, groupId, fiscoChainId);
    }

    public ExtendedRawTransactionManager(
            Web3j web3j,
            Credentials credentials,
            int attempts,
            int sleepDuration,
            BigInteger groupId,
            BigInteger fiscoChainId) {
        this(web3j, credentials, ChainId.NONE, attempts, sleepDuration, groupId, fiscoChainId);
    }

    BigInteger getBlockLimit() throws IOException {
        return web3j.getBlockNumberCache();
    }

    public TxHashVerifier getTxHashVerifier() {
        return txHashVerifier;
    }

    public void setTxHashVerifier(TxHashVerifier txHashVerifier) {
        this.txHashVerifier = txHashVerifier;
    }

    @Override
    public ExtendedRawTransaction createTransaction(
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            String data,
            BigInteger value,
            String extraData)
            throws IOException {
        Random r = new SecureRandom();
        BigInteger randomid = new BigInteger(250, r);
        BigInteger blockLimit = getBlockLimit();
        ExtendedRawTransaction rawTransaction =
                ExtendedRawTransaction.createTransaction(
                        randomid,
                        gasPrice,
                        gasLimit,
                        blockLimit,
                        to,
                        value,
                        data,
                        fiscoChainId,
                        groupId,
                        extraData);
        return rawTransaction;
    }

    @Override
    public SendTransaction sendTransaction(
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            String data,
            BigInteger value,
            String extraData)
            throws IOException {

        ExtendedRawTransaction rawTransaction =
                createTransaction(gasPrice, gasLimit, to, data, value, extraData);

        return signAndSend(rawTransaction);
    }

    @Override
    public SendTransaction sendTransaction(
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            String data,
            BigInteger value,
            String extraData,
            TransactionSucCallback callback)
            throws IOException {

        ExtendedRawTransaction rawTransaction =
                createTransaction(gasPrice, gasLimit, to, data, value, extraData);
        return signAndSend(rawTransaction, callback);
    }

    @Override
    public SendTransaction sendTransaction(String signedTransaction)
            throws IOException, TxHashMismatchException {
        SendTransaction sendTransaction = web3j.sendRawTransaction(signedTransaction).send();
        if (sendTransaction != null && !sendTransaction.hasError()) {
            String txHashLocal = Hash.sha3(signedTransaction);
            String txHashRemote = sendTransaction.getTransactionHash();
            if (!txHashVerifier.verify(txHashLocal, txHashRemote)) {
                throw new TxHashMismatchException(txHashLocal, txHashRemote);
            }
        }

        return sendTransaction;
    }

    @Override
    public SendTransaction sendTransaction(
            String signedTransaction, TransactionSucCallback callback)
            throws IOException, TxHashMismatchException {
        Request<?, SendTransaction> request = web3j.sendRawTransaction(signedTransaction);
        request.setNeedTransCallback(true);
        request.setTransactionSucCallback(callback);

        request.sendOnly();

        return null;

        /*
        if (ethSendTransaction != null && !ethSendTransaction.hasError()) {
            String txHashLocal = Hash.sha3(signedTransaction);
            String txHashRemote = ethSendTransaction.getTransactionHash();
            if (!txHashVerifier.verify(txHashLocal, txHashRemote)) {
                throw new TxHashMismatchException(txHashLocal, txHashRemote);
            }
        }

        return ethSendTransaction;
        */
    }

    @Override
    public String sign(ExtendedRawTransaction rawTransaction) {
        byte[] signedMessage;

        if (chainId > ChainId.NONE) {
            signedMessage =
                    ExtendedTransactionEncoder.signMessage(rawTransaction, chainId, credentials);
        } else {
            signedMessage = ExtendedTransactionEncoder.signMessage(rawTransaction, credentials);
        }

        String signedData = Numeric.toHexString(signedMessage);

        return signedData;
    }

    public SendTransaction signAndSend(ExtendedRawTransaction rawTransaction) throws IOException {
        String signedTransaction = sign(rawTransaction);
        SendTransaction result = sendTransaction(signedTransaction);
        return result;
    }

    public SendTransaction signAndSend(
            ExtendedRawTransaction rawTransaction, TransactionSucCallback callback)
            throws IOException {
        String signedTransaction = sign(rawTransaction);
        SendTransaction result = sendTransaction(signedTransaction, callback);
        return result;
    }
}
