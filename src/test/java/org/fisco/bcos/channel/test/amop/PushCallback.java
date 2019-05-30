package org.fisco.bcos.channel.test.amop;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.fisco.bcos.channel.client.ChannelPushCallback;
import org.fisco.bcos.channel.dto.ChannelPush;
import org.fisco.bcos.channel.dto.ChannelResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PushCallback extends ChannelPushCallback {
  static Logger logger = LoggerFactory.getLogger(PushCallback.class);

  @Override
  public void onPush(ChannelPush push) {
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    logger.debug("push:" + push.getContent());

    System.out.println(df.format(LocalDateTime.now()) + "server:push:" + push.getContent());
    ChannelResponse response = new ChannelResponse();
    response.setContent("receive request seq:" + String.valueOf(push.getMessageID()));
    response.setErrorCode(0);

    push.sendResponse(response);
  }
}
