package org.fisco.bcos.channel.dto;

import io.netty.buffer.ByteBuf;
import org.fisco.bcos.channel.handler.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopicVerifyMessage extends Message {
    private static final Logger logger = LoggerFactory.getLogger(TopicVerifyMessage.class);

    private static final long serialVersionUID = -7276897518418560354L;

    public TopicVerifyMessage() {}

    public TopicVerifyMessage(Message msg) {
        length = msg.getLength();
        type = msg.getType();
        seq = msg.getSeq();
        result = msg.getResult();
    }

    @Override
    public void readExtra(ByteBuf in) {
        logger.debug("readExtra channel package: {}", result);
        if (result == 0) {
            data = new byte[length - Message.HEADER_LENGTH];
            in.readBytes(data, 0, length - Message.HEADER_LENGTH);
            logger.debug("data: {} {}", data.length, data);
        }
    }

    @Override
    public void writeHeader(ByteBuf out) {
        // 先计算总长度
        length = Message.HEADER_LENGTH + data.length;
        super.writeHeader(out);
    }
}
