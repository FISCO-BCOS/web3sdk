package org.fisco.bcos.channel.event.filter;

import java.util.List;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;

public class EventLogFilterPushResponse {
    private int result;
    private String filterID;
    private List<Log> logs;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getFilterID() {
        return filterID;
    }

    public void setFilterID(String filterID) {
        this.filterID = filterID;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    @Override
    public String toString() {
        return "EventLogFilterPushResponse [result="
                + result
                + ", filterID="
                + filterID
                + ", logs="
                + logs
                + "]";
    }
}
