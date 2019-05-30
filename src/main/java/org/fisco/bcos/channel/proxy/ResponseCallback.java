package org.fisco.bcos.channel.proxy;

import org.fisco.bcos.channel.handler.Message;

public interface ResponseCallback {
  public void onResponse(Message response);
}
