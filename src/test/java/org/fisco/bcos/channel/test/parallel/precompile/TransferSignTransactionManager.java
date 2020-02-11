package org.fisco.bcos.channel.test.parallel.precompile;

import java.io.IOException;
import java.math.BigInteger;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.tx.ExtendedRawTransactionManager;

public class TransferSignTransactionManager extends ExtendedRawTransactionManager {
    public TransferSignTransactionManager(
            Web3j web3j, Credentials credentials, BigInteger groupId, BigInteger fiscoChainId) {
        super(web3j, credentials, groupId, fiscoChainId);
    }

    @Override
    protected BigInteger getBlockLimit() throws IOException {
        return new BigInteger("1");
    }
}
