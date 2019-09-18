package org.fisco.bcos.channel.event.filter;

import java.util.List;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.tx.txdecode.LogResult;

public abstract class EventLogPushCallback {

    private EventLogFilter filter;

    public abstract LogResult transferLogToLogResult(Log log);

    public abstract void onPushEventLog(int status, List<LogResult> logs);

    public EventLogFilter getFilter() {
        return filter;
    }

    public void setFilter(EventLogFilter filter) {
        this.filter = filter;
    }
}
