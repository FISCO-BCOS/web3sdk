package org.fisco.bcos.channel.client;

import java.io.IOException;
import java.math.BigInteger;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.protocol.exceptions.TransactionException;
import org.fisco.bcos.web3j.tx.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecuteTransaction extends Contract {

    private static Logger logger = LoggerFactory.getLogger(ExecuteTransaction.class);

    public ExecuteTransaction(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super("", contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public TransactionReceipt send(Function function) {
        TransactionReceipt transactionReceipt = null;
        try {
            transactionReceipt = executeTransaction(function);
        } catch (IOException | TransactionException e) {
            logger.error("Execute transaction failed:", e);
        }
        return transactionReceipt;
    }
}
