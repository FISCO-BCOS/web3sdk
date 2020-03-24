package org.fisco.bcos.channel.test.message;

import static org.junit.Assert.assertEquals;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.UUID;
import org.fisco.bcos.channel.dto.ChannelMessage2;
import org.fisco.bcos.channel.protocol.ChannelMessageType;
import org.junit.Test;

public class ChannelMessage2Test {
    @Test
    public void channelMessage2CodecTest0() {
        ChannelMessage2 channelMessage2 = new ChannelMessage2();

        String seq = UUID.randomUUID().toString().replaceAll("-", "");
        Integer result = Integer.valueOf(0);
        String content = "test content";
        String topic = "test_topic";
        channelMessage2.setSeq(seq);
        channelMessage2.setResult(result);
        channelMessage2.setType((short) ChannelMessageType.AMOP_REQUEST.getType());

        channelMessage2.setData(content.getBytes());
        channelMessage2.setTopic(topic);

        ByteBuf buffer = Unpooled.buffer();
        channelMessage2.writeHeader(buffer);
        channelMessage2.writeExtra(buffer);

        ChannelMessage2 channelMessage21 = new ChannelMessage2();
        channelMessage21.readHeader(buffer);
        channelMessage21.readExtra(buffer);

        assertEquals(channelMessage21.getTopic(), topic);
        assertEquals(new String(channelMessage21.getData()), content);
        assertEquals(channelMessage21.getResult(), result);
        assertEquals(channelMessage21.getSeq(), seq);
        assertEquals(
                channelMessage21.getType().intValue(), ChannelMessageType.AMOP_REQUEST.getType());
    }

    @Test
    public void channelMessage2CodecTest2() {
        ChannelMessage2 channelMessage2 = new ChannelMessage2();

        String seq = UUID.randomUUID().toString().replaceAll("-", "");
        Integer result = Integer.valueOf(0);
        String content = "中文测试";
        String topic = "中文topic测试";
        channelMessage2.setSeq(seq);
        channelMessage2.setResult(result);
        channelMessage2.setType((short) ChannelMessageType.AMOP_REQUEST.getType());

        channelMessage2.setData(content.getBytes());
        channelMessage2.setTopic(topic);

        ByteBuf buffer = Unpooled.buffer();
        channelMessage2.writeHeader(buffer);
        channelMessage2.writeExtra(buffer);

        ChannelMessage2 channelMessage21 = new ChannelMessage2();
        channelMessage21.readHeader(buffer);
        channelMessage21.readExtra(buffer);

        assertEquals(channelMessage21.getTopic(), topic);
        assertEquals(new String(channelMessage21.getData()), content);
        assertEquals(channelMessage21.getResult(), result);
        assertEquals(channelMessage21.getSeq(), seq);
        assertEquals(
                channelMessage21.getType().intValue(), ChannelMessageType.AMOP_REQUEST.getType());
    }
}
