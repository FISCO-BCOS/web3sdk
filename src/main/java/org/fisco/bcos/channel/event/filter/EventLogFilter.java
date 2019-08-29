package org.fisco.bcos.channel.event.filter;

import io.netty.channel.ChannelHandlerContext;
import java.math.BigInteger;

public class EventLogFilter {

    private EventLogPushCallback callback;
    private ExtendEventLogFilterParams params;
    private ChannelHandlerContext ctx = null;
    private EventLogFilterStatus status = EventLogFilterStatus.INITIALIZE;

    public EventLogPushCallback getCallback() {
        return callback;
    }

    public void setCallback(EventLogPushCallback callback) {
        this.callback = callback;
    }

    public void updateLastBlockNumber(BigInteger blockNumber) {
        this.getParams().setFromBlock(blockNumber.toString());
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public ExtendEventLogFilterParams getParams() {
        return params;
    }

    public void setParams(ExtendEventLogFilterParams params) {
        this.params = params;
    }

    public EventLogFilterStatus getStatus() {
        return status;
    }

    public void setStatus(EventLogFilterStatus status) {
        this.status = status;
    }
}
