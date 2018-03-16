package org.bcos.channel.client;

import org.bcos.channel.dto.EthereumResponse;
import io.netty.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by suyuhui on 17/8/17.
 */
public abstract class TransactionSucCallback {
    private static Logger logger = LoggerFactory.getLogger(TransactionSucCallback.class);

    public abstract void onResponse(EthereumResponse response);

    public void onTimeout() {
        logger.error("transactionSuc timeout");
        EthereumResponse response = new EthereumResponse();
        response.setErrorCode(102);
        response.setErrorMessage("transactionSuc timeout");
        response.setContent("");
        onResponse(response);
    }

    public Timeout getTimeout() {
        return timeout;
    }

    public void setTimeout(Timeout timeout) {
        this.timeout = timeout;
    }

    private Timeout timeout;
}
