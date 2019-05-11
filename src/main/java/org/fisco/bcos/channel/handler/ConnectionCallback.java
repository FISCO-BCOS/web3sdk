package org.fisco.bcos.channel.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Set;
import java.util.UUID;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.dto.BcosMessage;
import org.fisco.bcos.channel.dto.ChannelMessage;
import org.fisco.bcos.channel.dto.ChannelMessage2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionCallback implements ChannelConnections.Callback {
    private static Logger logger = LoggerFactory.getLogger(ConnectionCallback.class);

    private ObjectMapper objectMapper = new ObjectMapper();
    private Service channelService;
    private Set<String> topics;

    public Service getChannelService() {
        return channelService;
    }

    public void setChannelService(Service channelService) {
        this.channelService = channelService;
    }

    public ConnectionCallback(Set<String> topics) {
        this.topics = topics;
    }

    public void setTopics(Set<String> topics) {
        try {
            this.topics = topics;
        } catch (Exception e) {
            logger.error("system error", e);
        }
    }

    @Override
    public void onConnect(ChannelHandlerContext ctx) {
        try {
            channelService.setNumber(BigInteger.ONE);

            Message message = new Message();
            message.setResult(0);
            message.setType((short) 0x32);
            message.setSeq(UUID.randomUUID().toString().replaceAll("-", ""));

            logger.debug(
                    "connection established，send topic to the connection:{}", message.getSeq());

            topics.add("_block_notify_" + String.valueOf(channelService.getGroupId()));
            message.setData(objectMapper.writeValueAsBytes(topics.toArray()));

            logger.debug("topics: {}", new String(message.getData()));

            ByteBuf out = ctx.alloc().buffer();
            message.writeHeader(out);
            message.writeExtra(out);

            ctx.writeAndFlush(out);
        } catch (Exception e) {
            logger.error("error:", e);
        }
    }

    @Override
    public void onDisconnect(ChannelHandlerContext ctx) {}

    @Override
    public void onMessage(ChannelHandlerContext ctx, ByteBuf message) {
        try {
            Message msg = new Message();
            msg.readHeader(message);

            if (msg.getType() == 0x20 || msg.getType() == 0x21) {
                ChannelMessage channelMessage = new ChannelMessage(msg);
                channelMessage.readExtra(message);

                channelService.onReceiveChannelMessage(ctx, channelMessage);
            } else if (msg.getType() == 0x30 || msg.getType() == 0x31) {
                ChannelMessage2 channelMessage = new ChannelMessage2(msg);
                channelMessage.readExtra(message);

                channelService.onReceiveChannelMessage2(ctx, channelMessage);
            } else if (msg.getType() == 0x12) {
                BcosMessage fiscoMessage = new BcosMessage(msg);
                fiscoMessage.readExtra(message);

                channelService.onReceiveEthereumMessage(ctx, fiscoMessage);
            } else if (msg.getType() == 0x13) {
                msg.readExtra(message);

                String content = "1";
                try {
                    content = new String(msg.getData(), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    logger.error("heartbeat packet cannot be parsed");
                } catch (Exception e) {
                    logger.error("heartbeat packet Exception");
                }

                if ("0".equals(content)) {
                    logger.trace("heartbeat packet，send heartbeat packet back");
                    Message response = new Message();

                    response.setSeq(msg.getSeq());
                    response.setResult(0);
                    response.setType((short) 0x13);
                    response.setData("1".getBytes());

                    ByteBuf out = ctx.alloc().buffer();
                    response.writeHeader(out);
                    response.writeExtra(out);

                    ctx.writeAndFlush(out);
                } else if ("1".equals(content)) {
                    logger.trace("heartbeat response");
                }
            } else if (msg.getType() == 0x1000) {
                BcosMessage fiscoMessage = new BcosMessage(msg);
                fiscoMessage.readExtra(message);
                channelService.onReceiveTransactionMessage(ctx, fiscoMessage);
            } else if (msg.getType() == 0x1001) {
                // new block notify
                ChannelMessage2 channelMessage = new ChannelMessage2(msg);
                channelMessage.readExtra(message);

                channelService.onReceiveBlockNotify(ctx, channelMessage);
            } else {
                logger.error("unknown message type:{}", msg.getType());
            }
        } finally {
            message.release();
        }
    }
}
