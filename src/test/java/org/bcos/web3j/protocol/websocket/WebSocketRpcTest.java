package org.bcos.web3j.protocol.websocket;

import org.bcos.web3j.protocol.core.Request;
import org.bcos.web3j.protocol.core.methods.response.EthBlockNumber;
import org.bcos.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class WebSocketRpcTest {

    private WebSocketClient webSocketClient  = new WebSocketClient(new URI("ws://10.107.105.59:8888/"));


    private WebSocketService service = new WebSocketService(webSocketClient, true);

    private Request<?, EthBlockNumber> request = new Request<>(
            "blockNumber",
            Arrays.asList("0"),
            service,
            EthBlockNumber.class);

    public WebSocketRpcTest() throws URISyntaxException {
    }


    @Test
    public void testConnectViaWebSocketClient() throws Exception {

        service.connect();
        service.sendAsync(request, Web3ClientVersion.class);

        //  verify(webSocketClient).connectBlocking();
    }
}
