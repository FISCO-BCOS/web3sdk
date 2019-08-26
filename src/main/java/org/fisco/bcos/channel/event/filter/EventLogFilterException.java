package org.fisco.bcos.channel.event.filter;

@SuppressWarnings("serial")
public class EventLogFilterException extends Exception {

    private EventLogFilterPushStatus status;

    public EventLogFilterException(EventLogFilterPushStatus status, String message) {
        super(message);
        this.status = status;
    }

    public EventLogFilterPushStatus getStatus() {
        return status;
    }

    public void setStatus(EventLogFilterPushStatus status) {
        this.status = status;
    }

    public int getStatusAsIntValue() {
        return status.getStatus();
    }
}
