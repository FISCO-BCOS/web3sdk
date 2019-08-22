package org.fisco.bcos.channel.protocol;

public enum ChannelMessageType {
    CHANNEL_RPC_REQUEST(0x12), // type for rpc request
    CLIENT_HEARTBEAT(0x13), // type for heart beat for sdk
    CLIENT_HANDSHAKE(0x14), // type for hand shake
    CLIENT_REGISTER_EVENT_LOG(0x15), // type for event log filter register request and response
    AMOP_REQUEST(0x30), // type for request from sdk
    AMOP_RESPONSE(0x31), // type for response to sdk
    AMOP_CLIENT_TOPICS(0x32), // type for topic request
    AMOP_MULBROADCAST(0x35), // type for mult broadcast
    REQUEST_TOPICCERT(0x37), // type request verify
    UPDATE_TOPIICSTATUS(0x38), // type for update status
    TRANSACTION_NOTIFY(0x1000), // type for transaction notify
    BLOCK_NOTIFY(0x1001), // type for block notify
    EVENT_LOG_PUSH(0x1002); // type for event log push

    private int type;

    private ChannelMessageType(int type) {
        this.setType(type);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
