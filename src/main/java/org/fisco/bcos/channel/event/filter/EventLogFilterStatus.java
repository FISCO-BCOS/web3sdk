package org.fisco.bcos.channel.event.filter;

public enum EventLogFilterStatus {
    // event log is pushing from node normally
    EVENT_LOG_PUSHING(0x1),
    // request already send, wait for response
    WAITING_RESPONSE(0x2),
    // response not ok, wait for resend
    WAITING_REQUEST(0x3);

    private int status;

    private EventLogFilterStatus(int status) {
        this.setStatus(status);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
