package org.fisco.bcos.channel.event.filter;

import io.netty.util.Timeout;
import java.util.List;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.tx.txdecode.LogResult;

public abstract class EventLogPushCallback {

    // record event log push parameter
    private EventLogFilterParams params;
    // request timeout
    private Timeout timeout;

    public abstract LogResult transferLogToLogResult(Log log);

    public abstract void onPushEventLog(int status, List<LogResult> logs);

    public Timeout getTimeout() {
        return timeout;
    }

    public void setTimeout(Timeout timeout) {
        this.timeout = timeout;
    }

    public EventLogFilterParams getParams() {
        return params;
    }

    public void setParams(EventLogFilterParams params) {
        this.params = params;
    }
}
