package org.fisco.bcos.channel.client;

import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.tx.Contract;

import java.math.BigInteger;

public class SendTransaction extends Contract {

    public SendTransaction(String contractAddress,
                           Web3j web3j,
                           Credentials credentials,
                           BigInteger gasPrice,
                           BigInteger gasLimit) {
        super("", contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public void send(Function function, TransactionCallback callback) throws Exception {
        asyncExecuteTransaction(function, callback);
    }

}
