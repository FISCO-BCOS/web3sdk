package org.fisco.bcos.channel.dto;

import io.netty.channel.ChannelHandlerContext;
import org.fisco.bcos.channel.client.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChannelPush2 extends ChannelPush {
    static Logger logger = LoggerFactory.getLogger(ChannelPush2.class);
    private String topic;
    
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void sendResponse(ChannelResponse response) {
        logger.debug("send ChannelResponse seq:{}", response.getMessageID());

        response.setMessageID(getSeq());

        getService().sendResponseMessage2(response, getCtx(), getSeq(), topic);
    }
}
