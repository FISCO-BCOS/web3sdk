package org.bcos.web3j.tx;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.core.methods.response.EthGasPrice;
import org.bcos.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.bcos.web3j.protocol.core.methods.response.EthSendTransaction;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.bcos.web3j.protocol.exceptions.TransactionTimeoutException;


/**
 * Generic transaction manager.
 */
public abstract class ManagedTransaction {

    // https://www.reddit.com/r/ethereum/comments/5g8ia6/attention_miners_we_recommend_raising_gas_limit/
    public static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);

    protected Web3j web3j;

    protected TransactionManager transactionManager;

    protected ManagedTransaction(Web3j web3j, TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.web3j = web3j;
    }

    public BigInteger getGasPrice() throws IOException {
        EthGasPrice ethGasPrice = web3j.ethGasPrice().send();

        return ethGasPrice.getGasPrice();
    }

    protected TransactionReceipt send(
            String to, String data, BigInteger value, BigInteger gasPrice, BigInteger gasLimit, BigInteger type, boolean isInitByName)
            throws InterruptedException, IOException, TransactionTimeoutException {
        return transactionManager.executeTransaction(gasPrice, gasLimit, to, data, value,type, isInitByName);
    }

    protected void send(
            String to, String data, BigInteger value, BigInteger gasPrice, BigInteger gasLimit, BigInteger type, boolean isInitByName, TransactionSucCallback callback)
            throws InterruptedException, IOException, TransactionTimeoutException {
        transactionManager.executeTransaction(gasPrice, gasLimit, to, data, value, type, isInitByName, callback);
    }
}
