package org.fisco.bcos.channel.event.filter;

import io.netty.channel.ChannelHandlerContext;
import java.math.BigInteger;
import java.util.List;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.tx.txdecode.LogResult;

public class EventLogFilter {
    // represents a request at the user level
    private String registerID;
    private String filterID;

    private EventLogUserParams params;
    private EventLogPushCallback callback;
    private EventLogFilterStatus status = EventLogFilterStatus.WAITING_RESPONSE;

    private ChannelHandlerContext ctx = null;
    private BigInteger lastBlockNumber = null;
    private long logCount = 0;

    public EventLogPushCallback getCallback() {
        return callback;
    }

    public void setCallback(EventLogPushCallback callback) {
        this.callback = callback;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public EventLogUserParams getParams() {
        return params;
    }

    public void setParams(EventLogUserParams params) {
        this.params = params;
    }

    public EventLogFilterStatus getStatus() {
        return status;
    }

    public void setStatus(EventLogFilterStatus status) {
        this.status = status;
    }

    public String getRegisterID() {
        return registerID;
    }

    public void setRegisterID(String registerID) {
        this.registerID = registerID;
    }

    public BigInteger getLastBlockNumber() {
        return lastBlockNumber;
    }

    public void setLastBlockNumber(BigInteger lastBlockNumber) {
        this.lastBlockNumber = lastBlockNumber;
    }

    public long getLogCount() {
        return logCount;
    }

    public void setLogCount(long logCount) {
        this.logCount = logCount;
    }

    public void addLogCount(int i) {
        logCount += i;
    }

    public String getFilterID() {
        return filterID;
    }

    public void setFilterID(String filterID) {
        this.filterID = filterID;
    }

    @Override
    public String toString() {
        return "EventLogFilter [registerID="
                + registerID
                + ", filterID="
                + filterID
                + ", params="
                + params
                + ", status="
                + status
                + ", ctx="
                + ctx
                + ", lastBlockNumber="
                + lastBlockNumber
                + ", logCount="
                + logCount
                + "]";
    }

    public EventLogUserParams generateNewParams() {
        EventLogUserParams params = new EventLogUserParams();
        params.setToBlock(getParams().getToBlock());
        params.setAddresses(getParams().getAddresses());
        params.setTopics(getParams().getTopics());

        if (lastBlockNumber == null) {
            params.setFromBlock(getParams().getFromBlock());
        } else {
            params.setFromBlock(lastBlockNumber.toString());
        }

        return params;
    }

    public void updateByLogResult(List<LogResult> logResults) {
        if (logResults.isEmpty()) {
            return;
        }

        Log lastLog = logResults.get(logResults.size() - 1).getLog();
        if (lastBlockNumber == null) {
            lastBlockNumber = lastLog.getBlockNumber();
            logCount += logResults.size();
        } else {
            if (lastLog.getBlockNumber().compareTo(lastBlockNumber) > 0) {
                lastBlockNumber = lastLog.getBlockNumber();
                logCount += logResults.size();
            }
        }
    }
}
