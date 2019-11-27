package org.fisco.bcos.web3j.tx;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ExtendedRawTransaction;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.crypto.RawTransaction;
import org.fisco.bcos.web3j.crypto.TransactionEncoder;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.Request;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosBlock;
import org.fisco.bcos.web3j.protocol.core.methods.response.SendTransaction;
import org.fisco.bcos.web3j.tx.exceptions.ContractCallException;
import org.fisco.bcos.web3j.tx.exceptions.TxHashMismatchException;
import org.fisco.bcos.web3j.utils.Numeric;
import org.fisco.bcos.web3j.utils.TxHashVerifier;
import org.springframework.util.CollectionUtils;

/**
 * TransactionManager implementation using Ethereum wallet file to create and sign transactions
 * locally.
 *
 * <p>This transaction manager provides support for specifying the chain id for transactions as per
 * <a href="https://github.com/ethereum/EIPs/issues/155">EIP155</a>.
 */
public class RawTransactionManager extends TransactionManager {
    private final Web3j web3j;
    final Credentials credentials;

    private final byte chainId;

    protected TxHashVerifier txHashVerifier = new TxHashVerifier();

    public RawTransactionManager(Web3j web3j, Credentials credentials, byte chainId) {
        super(web3j, credentials);
        this.web3j = web3j;
        this.credentials = credentials;

        this.chainId = chainId;
    }

    public RawTransactionManager(
            Web3j web3j, Credentials credentials, byte chainId, int attempts, int sleepDuration) {
        super(web3j, attempts, sleepDuration, credentials);
        this.web3j = web3j;
        this.credentials = credentials;

        this.chainId = chainId;
    }

    public RawTransactionManager(Web3j web3j, Credentials credentials) {
        this(web3j, credentials, ChainId.NONE);
    }

    public RawTransactionManager(
            Web3j web3j, Credentials credentials, int attempts, int sleepDuration) {
        this(web3j, credentials, ChainId.NONE, attempts, sleepDuration);
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
    public SendTransaction sendTransaction(
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
        RawTransaction rawTransaction =
                RawTransaction.createTransaction(
                        randomid, gasPrice, gasLimit, blockLimit, to, value, data);

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
        Random r = new SecureRandom();
        BigInteger randomid = new BigInteger(250, r);
        BigInteger blockLimit = getBlockLimit();
        RawTransaction rawTransaction =
                RawTransaction.createTransaction(
                        randomid, gasPrice, gasLimit, blockLimit, to, value, data);

        return signAndSend(rawTransaction, callback);
    }

    public SendTransaction signAndSend(RawTransaction rawTransaction) throws IOException {

        byte[] signedMessage;

        if (chainId > ChainId.NONE) {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
        } else {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        }

        String hexValue = Numeric.toHexString(signedMessage);
        SendTransaction sendTransaction = web3j.sendRawTransaction(hexValue).send();
        if (sendTransaction != null && !sendTransaction.hasError()) {
            String txHashLocal = Hash.sha3(hexValue);
            String txHashRemote = sendTransaction.getTransactionHash();
            if (!txHashVerifier.verify(txHashLocal, txHashRemote)) {
                throw new TxHashMismatchException(txHashLocal, txHashRemote);
            }
        }
        return sendTransaction;
    }

    public SendTransaction signAndSend(
            RawTransaction rawTransaction, TransactionSucCallback callback) throws IOException {

        byte[] signedMessage;

        if (chainId > ChainId.NONE) {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
        } else {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        }

        String hexValue = Numeric.toHexString(signedMessage);
        Request<?, SendTransaction> request = web3j.sendRawTransaction(hexValue);
        request.setNeedTransCallback(true);
        request.setTransactionSucCallback(callback);
        request.sendOnly();

        return null;
    }

    @Override
    public BcosBlock submitTransactions(List<ExtendedRawTransaction> transactionList)
            throws IOException {
        if (CollectionUtils.isEmpty(transactionList)) {
            return null;
        }
        StringBuffer str = new StringBuffer();
        for (ExtendedRawTransaction tx : transactionList) {
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);
            BigInteger blockLimit = getBlockLimit();
            RawTransaction rawTransaction =
                    RawTransaction.createTransaction(
                            randomid,
                            tx.getGasPrice(),
                            tx.getGasLimit(),
                            blockLimit,
                            tx.getData(),
                            tx.getValue(),
                            tx.getExtraData());
            byte[] signedMessage;

            if (chainId > ChainId.NONE) {
                signedMessage =
                        TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
            } else {
                signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            }

            String signedTransaction = Numeric.toHexString(signedMessage);
            str.append(signedTransaction);
            str.append(",");
        }
        String txsStr = str.deleteCharAt(str.length() - 1).toString();
        BcosBlock block = web3j.submitTransactions(txsStr).send();
        if (block == null) {
            throw new ContractCallException("The return is null.");
        }
        if (block.hasError()) {
            throw new ContractCallException(block.getError().getMessage());
        }
        return block;
    }
}
