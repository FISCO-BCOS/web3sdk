package org.fisco.bcos.channel.client;

import io.netty.util.Timeout;
import org.fisco.bcos.channel.dto.BcosResponse;
import org.fisco.bcos.channel.protocol.ChannelMessageError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BcosResponseCallback {
    private static Logger logger = LoggerFactory.getLogger(BcosResponseCallback.class);

    private Timeout timeout;

    public abstract void onResponse(BcosResponse response);

    public void onTimeout() {
        // logger.error("Processing bcos message timeout:{}");

        BcosResponse response = new BcosResponse();
        response.setErrorCode(ChannelMessageError.MESSAGE_TIMEOUT.getError());
        response.setErrorMessage("Processing bcos message timeout");

        response.setContent("");

        onResponse(response);
    }

    public Timeout getTimeout() {
        return timeout;
    }

    public void setTimeout(Timeout timeout) {
        this.timeout = timeout;
    }
}
