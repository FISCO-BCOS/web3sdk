package org.fisco.bcos.web3j.protocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Flowable;
import java.util.concurrent.CompletableFuture;
import org.fisco.bcos.web3j.protocol.core.Request;
import org.fisco.bcos.web3j.protocol.core.Response;
import org.fisco.bcos.web3j.protocol.websocket.events.Notification;
import org.fisco.bcos.web3j.utils.Async;

/** Base service implementation. */
public abstract class Service implements Web3jService {

  protected final ObjectMapper objectMapper;

  public Service(boolean includeRawResponses) {
    objectMapper = ObjectMapperFactory.getObjectMapper(includeRawResponses);
  }

  @Override
  public <T extends Response> CompletableFuture<T> sendAsync(
      Request jsonRpc20Request, Class<T> responseType) {
    return Async.run(() -> send(jsonRpc20Request, responseType));
  }

  @Override
  public <T extends Notification<?>> Flowable<T> subscribe(
      Request request, String unsubscribeMethod, Class<T> responseType) {
    throw new UnsupportedOperationException(
        String.format(
            "Service %s does not support subscriptions", this.getClass().getSimpleName()));
  }
}
