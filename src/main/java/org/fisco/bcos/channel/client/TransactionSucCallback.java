package org.fisco.bcos.channel.client;

import io.netty.util.Timeout;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by suyuhui on 17/8/17. */
public abstract class TransactionSucCallback {
    private static Logger logger = LoggerFactory.getLogger(TransactionSucCallback.class);

    public abstract void onResponse(TransactionReceipt response);

    public void onTimeout() {
        TransactionReceipt receipt = new TransactionReceipt();
        receipt.setStatus("Transaction receipt timeout");
        // For console can get error message when timeout
        receipt.setMessage("Transaction receipt timeout");
        onResponse(receipt);
    }

    public Timeout getTimeout() {
        return timeout;
    }

    public void setTimeout(Timeout timeout) {
        this.timeout = timeout;
    }

    private Timeout timeout;
}
