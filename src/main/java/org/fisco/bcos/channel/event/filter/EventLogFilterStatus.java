package org.fisco.bcos.channel.event.filter;

public enum EventLogFilterStatus {
    INITIALIZE(0),
    EVENT_LOG_PUSHING(1),
    WAITING_RESPONSE(2),
    WAITING_RESEND(3);

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
