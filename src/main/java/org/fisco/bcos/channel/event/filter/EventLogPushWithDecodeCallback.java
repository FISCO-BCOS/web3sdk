package org.fisco.bcos.channel.event.filter;

import java.util.List;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.tx.txdecode.BaseException;
import org.fisco.bcos.web3j.tx.txdecode.LogResult;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventLogPushWithDecodeCallback extends EventLogPushCallback {

    // Log parsing tool for the pushed log
    private TransactionDecoder decoder;

    private static final Logger logger =
            LoggerFactory.getLogger(EventLogPushWithDecodeCallback.class);

    public EventLogPushWithDecodeCallback() {}

    public EventLogPushWithDecodeCallback(TransactionDecoder decoder) {
        this.decoder = decoder;
    }

    public TransactionDecoder getDecoder() {
        return decoder;
    }

    public void setDecoder(TransactionDecoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public void onPushEventLog(int status, List<LogResult> logs) {
        logger.info(
                " onPushEventLog, params: {}, status: {}, logs: {}",
                getFilter().getParams(),
                status,
                logs);
    }

    @Override
    public LogResult transferLogToLogResult(Log log) {
        try {
            LogResult logResult = getDecoder().decodeEventLogReturnObject(log);
            return logResult;
        } catch (BaseException e) {
            logger.warn(" event log decode failed, log: {}", log);
            return null;
        }
    }
}
