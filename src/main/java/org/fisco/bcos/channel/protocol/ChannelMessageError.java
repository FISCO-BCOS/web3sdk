package org.fisco.bcos.channel.protocol;

public enum ChannelMessageError {
    NODES_UNREACHABLE(99), // nodes unreachable
    MESSAGE_SEND_EXCEPTION(100), // send failed after N times retry
    MESSAGE_TIMEOUT(102); // timeout

    private int error;

    private ChannelMessageError(int error) {
        this.setError(error);
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
