package org.fisco.bcos.channel.client;

import io.netty.util.Timeout;
import org.fisco.bcos.channel.dto.FiscoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FiscoResponseCallback {
  private static Logger logger = LoggerFactory.getLogger(FiscoResponseCallback.class);

  private Timeout timeout;

  public abstract void onResponse(FiscoResponse response);

  public void onTimeout() {
    logger.error("Processing fisco message timeout:{}");

    FiscoResponse response = new FiscoResponse();
    response.setErrorCode(102);
    response.setErrorMessage("Processing fisco message timeout");

    response.setContent("");

    onResponse(response);
  }

  public Timeout getTimeout() {
    return timeout;
  }

  public void setTimeout(Timeout timeout) {
    this.timeout = timeout;
  }
}
