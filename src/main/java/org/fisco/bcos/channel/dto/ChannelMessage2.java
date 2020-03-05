package org.fisco.bcos.channel.dto;

import io.netty.buffer.ByteBuf;
import org.fisco.bcos.channel.handler.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChannelMessage2 extends Message {
    private static Logger logger = LoggerFactory.getLogger(ChannelMessage2.class);

    private static final long serialVersionUID = -7276897518418560354L;

    public ChannelMessage2() {}

    public ChannelMessage2(Message msg) {
        length = msg.getLength();
        type = msg.getType();
        seq = msg.getSeq();
        result = msg.getResult();
    }

    @Override
    public void readExtra(ByteBuf in) {
        if (result == 0) {
            Short topicLength = in.readUnsignedByte();

            byte[] topicBytes = new byte[topicLength - 1];
            in.readBytes(topicBytes, 0, topicLength - 1);
            topic = new String(topicBytes);

            data = new byte[length - Message.HEADER_LENGTH - topicLength];
            in.readBytes(data, 0, length - Message.HEADER_LENGTH - topicLength);
        }
    }

    @Override
    public void writeHeader(ByteBuf out) {
        // total length
        length = Message.HEADER_LENGTH + 1 + topic.getBytes().length + data.length;

        super.writeHeader(out);
    }

    @Override
    public void writeExtra(ByteBuf out) {
        out.writeByte(1 + topic.getBytes().length);
        out.writeBytes(topic.getBytes());

        out.writeBytes(data);
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String toTopic) {
        this.topic = toTopic;
    }

    private String topic;
}
