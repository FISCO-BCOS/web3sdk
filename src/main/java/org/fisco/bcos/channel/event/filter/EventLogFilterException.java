package org.fisco.bcos.channel.event.filter;

@SuppressWarnings("serial")
public class EventLogFilterException extends Exception {

    public EventLogFilterException(EventLogFilterPushStatus status, String message) {
        super(message);
        this.status = status;
    }

    private EventLogFilterPushStatus status;

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
