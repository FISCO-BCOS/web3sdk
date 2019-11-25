package org.fisco.bcos.web3j.tx;

import java.io.IOException;
import java.math.BigInteger;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.crypto.ExtendedRawTransaction;
import org.fisco.bcos.web3j.precompile.cns.CnsService;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.exceptions.TransactionException;

/** Generic transaction manager. */
public abstract class ManagedTransaction {

    public static final BigInteger GAS_PRICE = BigInteger.valueOf(22_000_000_000L);

    protected Web3j web3j;

    protected TransactionManager transactionManager;

    protected CnsService cnsService;

    protected ManagedTransaction(Web3j web3j, TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.web3j = web3j;
        this.cnsService = new CnsService(web3j, transactionManager.credentials);
    }

    public long getSyncThreshold() {
        return cnsService.getSyncThreshold();
    }

    public void setSyncThreshold(long syncThreshold) {
        cnsService.setSyncThreshold(syncThreshold);
    }

    protected void sendOnly(
            String to,
            String data,
            BigInteger value,
            BigInteger gasPrice,
            BigInteger gasLimit,
            TransactionSucCallback callback)
            throws IOException, TransactionException {
        transactionManager.sendTransaction(gasPrice, gasLimit, to, data, value, null, callback);
    }

    protected String createSeq(
            String to, String data, BigInteger value, BigInteger gasPrice, BigInteger gasLimit)
            throws IOException {
        ExtendedRawTransaction rawTransaction =
                transactionManager.createTransaction(gasPrice, gasLimit, to, data, value, null);
        return transactionManager.sign(rawTransaction);
    }
}
