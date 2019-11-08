package org.fisco.bcos.web3j.tx;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.crypto.RawTransaction;
import org.fisco.bcos.web3j.crypto.TransactionEncoder;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.SendTransaction;
import org.fisco.bcos.web3j.tx.exceptions.TxHashMismatchException;
import org.fisco.bcos.web3j.utils.Numeric;
import org.fisco.bcos.web3j.utils.TxHashVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TransactionManager implementation for using an Ethereum node to transact.
 *
 * <p><b>Note</b>: accounts must be unlocked on the node for transactions to be successful.
 */
public class ClientTransactionManager extends TransactionManager {
    static Logger logger = LoggerFactory.getLogger(RawTransactionManager.class);
    private final Web3j web3j;
    protected TxHashVerifier txHashVerifier = new TxHashVerifier();

    public ClientTransactionManager(Web3j web3j, Credentials credentials) {
        super(credentials);
        this.web3j = web3j;
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
        logger.info("sendTransaction randomid: {} blockLimit:{}", randomid, blockLimit);
        RawTransaction rawTransaction =
                RawTransaction.createTransaction(
                        randomid, gasPrice, gasLimit, blockLimit, to, value, data);

        return signAndSend(rawTransaction);
    }

    //    public EthSendTransaction sendTransaction(BigInteger gasPrice, BigInteger gasLimit, String
    // to, String data, BigInteger value, TransactionSucCallback callback) throws IOException {
    //        Transaction transaction = new Transaction(
    //                getFromAddress(), null, gasPrice, gasLimit, to, value, data);
    //
    //        Request<?, EthSendTransaction> request = web3j.ethSendTransaction(transaction);
    //        request.setNeedTransCallback(true);
    //        request.setTransactionSucCallback(callback);
    //        return request.send();
    //    }

    public SendTransaction signAndSend(RawTransaction rawTransaction) throws IOException {

        byte[] signedMessage;

        signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);

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

    BigInteger getBlockLimit() throws IOException {
        return web3j.getBlockNumberCache();
    }
}
