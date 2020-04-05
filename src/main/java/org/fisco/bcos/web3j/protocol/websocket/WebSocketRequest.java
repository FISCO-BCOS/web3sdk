package org.fisco.bcos.web3j.protocol.websocket;

import java.util.concurrent.CompletableFuture;

/**
 * Objects necessary to process a reply for a request sent via WebSocket protocol.
 *
 * @param <T> type of a data item that should be returned by the sent request
 */
@Deprecated
class WebSocketRequest<T> {
    private CompletableFuture<T> onReply;
    private Class<T> responseType;

    public WebSocketRequest(CompletableFuture<T> onReply, Class<T> responseType) {
        this.onReply = onReply;
        this.responseType = responseType;
    }

    public CompletableFuture<T> getOnReply() {
        return onReply;
    }

    public Class<T> getResponseType() {
        return responseType;
    }
}
