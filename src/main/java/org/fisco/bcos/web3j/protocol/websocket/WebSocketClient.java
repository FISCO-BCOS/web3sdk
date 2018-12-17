package org.fisco.bcos.web3j.protocol.websocket;

import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Map;

/**
 * Web socket client implementation that connects to a specify URI. Allows to provide a listener
 * that will be called when a new message is received by the client.
 */
public class WebSocketClient extends org.java_websocket.client.WebSocketClient {

    private static final Logger log = LoggerFactory.getLogger(WebSocketClient.class);

    private WebSocketListener listener;

    public WebSocketClient(URI serverUri) {
        super(serverUri);
    }

    public WebSocketClient(URI serverUri, Map<String,String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("Opened WebSocket connection to {}", uri);
    }

    @Override
    public void onMessage(String s) {
        try {
            log.debug("Received message {} from server {}", s, uri);
            listener.onMessage(s);
        } catch (Exception e) {
            log.error("Failed to process message '{}' from server {}", s, uri);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        log.info("Closed WebSocket connection to {}, because of reason: '{}'."
                + "Conection closed remotely: {}", uri, reason, remote);
        listener.onClose();
    }

    @Override
    public void onError(Exception e) {
        log.error(String.format("WebSocket connection to {} failed with error", uri), e);
        listener.onError(e);
    }

    /**
     * Set a listener that will be called when a new message is received by the client.
     *
     * @param listener WebSocket listener
     */
    public void setListener(WebSocketListener listener) {
        this.listener = listener;
    }
}
