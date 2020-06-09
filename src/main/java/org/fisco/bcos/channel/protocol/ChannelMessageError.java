package org.fisco.bcos.channel.protocol;

public enum ChannelMessageError {
    NODES_UNREACHABLE(99), // nodes unreachable
    MESSAGE_SEND_EXCEPTION(100), // send failed after N times retry
    MESSAGE_TIMEOUT(102), // timeout
    MESSAGE_DECODE_ERROR(105), // decode error
    // the AMOP_requests or the AMOP_multicast_requests have been rejected due to over bandwidth
    // limit
    REJECT_AMOP_REQ_FOR_OVER_BANDWIDTHLIMIT(103);

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
