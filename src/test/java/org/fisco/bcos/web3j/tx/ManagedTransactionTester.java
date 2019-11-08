package org.fisco.bcos.web3j.tx;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigInteger;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.Request;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosTransactionReceipt;
import org.fisco.bcos.web3j.protocol.core.methods.response.BlockNumber;
import org.fisco.bcos.web3j.protocol.core.methods.response.SendTransaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.utils.TxHashVerifier;
import org.junit.Before;

public abstract class ManagedTransactionTester {

    public static final String ADDRESS = "0x3d6cb163f7c72d20b0fcd6baae5889329d138a4a";
    public static final String TRANSACTION_HASH = "0xHASH";
    protected Web3j web3j;
    protected TxHashVerifier txHashVerifier;

    @Before
    public void setUp() throws Exception {
        web3j = mock(Web3j.class);
        txHashVerifier = mock(TxHashVerifier.class);
        when(txHashVerifier.verify(any(), any())).thenReturn(true);
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
        BlockNumber ethBlockNumber = new BlockNumber();
        ethBlockNumber.setResult("0x1");

        Request<?, BlockNumber> ethBlockNumberRequest = mock(Request.class);
        when(ethBlockNumberRequest.send()).thenReturn(ethBlockNumber);
        when(web3j.getBlockNumber()).thenReturn((Request) ethBlockNumberRequest);
        when(web3j.getBlockNumberCache()).thenReturn(new BigInteger("1"));
    }

    @SuppressWarnings("unchecked")
    void prepareTransactionRequest() throws IOException {
        SendTransaction sendTransaction = new SendTransaction();
        sendTransaction.setResult(TRANSACTION_HASH);

        Request<?, SendTransaction> rawTransactionRequest = mock(Request.class);
        when(rawTransactionRequest.send()).thenReturn(sendTransaction);
        when(web3j.sendRawTransaction(any(String.class)))
                .thenReturn((Request) rawTransactionRequest);
    }

    @SuppressWarnings("unchecked")
    void prepareTransactionReceipt(TransactionReceipt transactionReceipt) throws IOException {
        BcosTransactionReceipt ethGetTransactionReceipt = new BcosTransactionReceipt();
        ethGetTransactionReceipt.setResult(transactionReceipt);

        Request<?, BcosTransactionReceipt> getTransactionReceiptRequest = mock(Request.class);
        when(getTransactionReceiptRequest.send()).thenReturn(ethGetTransactionReceipt);
        when(web3j.getTransactionReceipt(TRANSACTION_HASH))
                .thenReturn((Request) getTransactionReceiptRequest);
    }
}
