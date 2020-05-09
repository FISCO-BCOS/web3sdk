package org.fisco.bcos.web3j.tx;

import java.io.IOException;
import java.math.BigInteger;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.Request;
import org.fisco.bcos.web3j.protocol.core.methods.response.SendTransaction;
import org.fisco.bcos.web3j.tx.exceptions.TxHashMismatchException;

public class ExtendedRawTransactonAndGetProofManager extends ExtendedRawTransactionManager {

    public ExtendedRawTransactonAndGetProofManager(
            Web3j web3j, Credentials credentials, BigInteger groupId, BigInteger fiscoChainId) {
        super(web3j, credentials, groupId, fiscoChainId);
    }

    @Override
    public SendTransaction sendTransaction(
            String signedTransaction, TransactionSucCallback callback)
            throws IOException, TxHashMismatchException {
        Request<?, SendTransaction> request =
                getWeb3j().sendRawTransactionAndGetProof(signedTransaction);
        request.setNeedTransCallback(true);
        request.setTransactionSucCallback(callback);

        request.sendOnly();

        return null;
    }
}
