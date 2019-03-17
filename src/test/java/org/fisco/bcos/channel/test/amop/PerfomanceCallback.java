package org.fisco.bcos.channel.test.amop;

import org.fisco.bcos.channel.client.ChannelResponseCallback;
import org.fisco.bcos.channel.dto.ChannelResponse;

public class PerfomanceCallback extends ChannelResponseCallback {
  @Override
  public void onResponseMessage(ChannelResponse response) {
    collector.onMessage(response);
  }

  public PerfomanceCollector collector;
}
