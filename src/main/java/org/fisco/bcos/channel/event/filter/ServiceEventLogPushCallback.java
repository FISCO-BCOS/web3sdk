package org.fisco.bcos.channel.event.filter;

import java.util.List;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.tx.txdecode.LogResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceEventLogPushCallback extends EventLogPushCallback {

    private static final Logger logger = LoggerFactory.getLogger(ServiceEventLogPushCallback.class);

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
        LogResult logResult = new LogResult();
        logResult.setLog(log);
        return logResult;
    }
}
