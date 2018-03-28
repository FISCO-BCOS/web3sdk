package org.bcos.web3j.tx;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.web3j.protocol.core.Request;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.crypto.TransactionEncoder;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.core.DefaultBlockParameterName;
import org.bcos.web3j.protocol.core.methods.request.RawTransaction;
import org.bcos.web3j.protocol.core.methods.response.EthBlockNumber;
import org.bcos.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.bcos.web3j.protocol.core.methods.response.EthSendTransaction;
import org.bcos.web3j.utils.Numeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TransactionManager implementation using Ethereum wallet file to create and sign transactions
 * locally.
 *
 * <p>This transaction manager provides support for specifying the chain id for transactions as per
 * <a href="https://github.com/ethereum/EIPs/issues/155">EIP155</a>.
 */
public class RawTransactionManager extends TransactionManager {
    static Logger logger = LoggerFactory.getLogger(RawTransactionManager.class);
    private final Web3j web3j;
    final Credentials credentials;

    private final byte chainId;

    public RawTransactionManager(Web3j web3j, Credentials credentials, byte chainId) {
        super(web3j);
        this.web3j = web3j;
        this.credentials = credentials;

        this.chainId = chainId;
    }

    public RawTransactionManager(
            Web3j web3j, Credentials credentials, byte chainId, int attempts, int sleepDuration) {
        super(web3j, attempts, sleepDuration);
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

    BigInteger getNonce() throws IOException {
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                credentials.getAddress(), DefaultBlockParameterName.PENDING).send();

        return ethGetTransactionCount.getTransactionCount();
    }

    BigInteger getBlockLimit() throws IOException {
        return  web3j.getBlockNumberCache();
    }

    @Override
    public EthSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value, BigInteger type, boolean isInitByName) throws IOException {

			Random r = new Random();
			BigInteger randomid = new BigInteger(250,r);
            BigInteger blockLimit = getBlockLimit();
            logger.info("sendTransaction randomid: {} blockLimit:{}", randomid,blockLimit);
            RawTransaction rawTransaction = RawTransaction.createTransaction(
                    randomid,
                    gasPrice,
                    gasLimit,
                    blockLimit,
                    to,
                    value,
                    data,
                    type,
                    isInitByName);

        return signAndSend(rawTransaction);
    }

    @Override
    public EthSendTransaction sendTransaction(BigInteger gasPrice, BigInteger gasLimit, String to, String data, BigInteger value, BigInteger type, boolean isInitByName, TransactionSucCallback callback) throws IOException {
        Random r = new Random();
        BigInteger randomid = new BigInteger(250,r);
        BigInteger blockLimit = getBlockLimit();
        RawTransaction rawTransaction = RawTransaction.createTransaction(

                randomid,
                gasPrice,
                gasLimit,
                blockLimit,
                to,
                value,
                data,
                type,
                isInitByName);


        return signAndSend(rawTransaction, callback);
    }

    public EthSendTransaction signAndSend(RawTransaction rawTransaction)
            throws IOException {

        byte[] signedMessage;

        if (chainId > ChainId.NONE) {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
        } else {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        }

        String hexValue = Numeric.toHexString(signedMessage);

        return web3j.ethSendRawTransaction(hexValue).send();
    }

    public EthSendTransaction signAndSend(RawTransaction rawTransaction, TransactionSucCallback callback)
            throws IOException {

        byte[] signedMessage;

        if (chainId > ChainId.NONE) {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
        } else {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        }

        String hexValue = Numeric.toHexString(signedMessage);
        Request<?, EthSendTransaction> request = web3j.ethSendRawTransaction(hexValue);
        request.setNeedTransCallback(true);
        request.setTransactionSucCallback(callback);
        return request.send();
    }
    
    @Override
    public String getFromAddress() {
        return credentials.getAddress();
    }
}
