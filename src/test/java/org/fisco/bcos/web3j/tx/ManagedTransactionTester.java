package org.fisco.bcos.web3j.tx;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.SampleKeys;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameterName;
import org.fisco.bcos.web3j.protocol.core.Request;
import org.fisco.bcos.web3j.protocol.core.methods.response.*;
import org.fisco.bcos.web3j.utils.TxHashVerifier;
import org.junit.Before;


import java.io.IOException;
import java.math.BigInteger;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public abstract class ManagedTransactionTester {

    static final String ADDRESS = "0x3d6cb163f7c72d20b0fcd6baae5889329d138a4a";
    static final String TRANSACTION_HASH = "0xHASH";
    protected Web3j web3j;
    protected TxHashVerifier txHashVerifier;

    @Before
    public void setUp() throws Exception {
        web3j = mock(Web3j.class);
        txHashVerifier = mock(TxHashVerifier.class);
        when(txHashVerifier.verify(any(), any())).thenReturn(true);
    }

    public TransactionManager getVerifiedTransactionManager(Credentials credentials,
                                                            int attempts, int sleepDuration) {
        RawTransactionManager transactionManager =
                new RawTransactionManager(web3j, credentials, attempts, sleepDuration);
        transactionManager.setTxHashVerifier(txHashVerifier);
        return transactionManager;
    }

    public TransactionManager getVerifiedTransactionManager(Credentials credentials) {
        RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
        transactionManager.setTxHashVerifier(txHashVerifier);
        return transactionManager;
    }

    void prepareTransaction(TransactionReceipt transactionReceipt) throws IOException {
        prepareBlockNumberRequest();
        prepareTransactionRequest();
        prepareTransactionReceipt(transactionReceipt);
    }

    @SuppressWarnings("unchecked")
    void prepareBlockNumberRequest() throws IOException {
        EthBlockNumber ethBlockNumber = new EthBlockNumber();
        ethBlockNumber.setResult("0x1");

        Request<?, EthBlockNumber> ethBlockNumberRequest = mock(Request.class);
        when(ethBlockNumberRequest.send())
                .thenReturn(ethBlockNumber);
        when(web3j.ethBlockNumber())
                .thenReturn((Request) ethBlockNumberRequest);
        when(web3j.getBlockNumberCache()).thenReturn(new BigInteger("1"));
    }

    @SuppressWarnings("unchecked")
    void prepareTransactionRequest() throws IOException {
        EthSendTransaction ethSendTransaction = new EthSendTransaction();
        ethSendTransaction.setResult(TRANSACTION_HASH);

        Request<?, EthSendTransaction> rawTransactionRequest = mock(Request.class);
        when(rawTransactionRequest.send()).thenReturn(ethSendTransaction);
        when(web3j.ethSendRawTransaction(any(String.class)))
                .thenReturn((Request) rawTransactionRequest);
    }

    @SuppressWarnings("unchecked")
    void prepareTransactionReceipt(TransactionReceipt transactionReceipt) throws IOException {
        EthGetTransactionReceipt ethGetTransactionReceipt = new EthGetTransactionReceipt();
        ethGetTransactionReceipt.setResult(transactionReceipt);

        Request<?, EthGetTransactionReceipt> getTransactionReceiptRequest = mock(Request.class);
        when(getTransactionReceiptRequest.send())
                .thenReturn(ethGetTransactionReceipt);
        when(web3j.ethGetTransactionReceipt(TRANSACTION_HASH))
                .thenReturn((Request) getTransactionReceiptRequest);
    }

}
