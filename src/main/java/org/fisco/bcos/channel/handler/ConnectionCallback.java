package org.fisco.bcos.channel.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import org.fisco.bcos.channel.client.BcosResponseCallback;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.dto.BcosMessage;
import org.fisco.bcos.channel.dto.BcosResponse;
import org.fisco.bcos.channel.dto.ChannelMessage;
import org.fisco.bcos.channel.dto.ChannelMessage2;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.Request;
import org.fisco.bcos.web3j.protocol.core.methods.response.BlockNumber;
import org.fisco.bcos.web3j.protocol.exceptions.MessageDecodingException;
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

            // Exchange channel protocol with the underlying node
            channelService.sendSDKChannelHighestSupportedMessage(ctx);

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

            queryBlockNumberForSelectNodes(ctx);
        } catch (Exception e) {
            logger.error("error:", e);
        }
    }

    private void queryBlockNumberForSelectNodes(ChannelHandlerContext ctx)
            throws JsonProcessingException {
        BcosMessage bcosMessage = new BcosMessage();
        bcosMessage.setType((short) 0x12);
        String seq = UUID.randomUUID().toString().replaceAll("-", "");
        bcosMessage.setSeq(seq);
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(channelService);
        Request<Integer, BlockNumber> request =
                new Request<>(
                        "getBlockNumber",
                        Arrays.asList(channelService.getGroupId()),
                        channelEthereumService,
                        BlockNumber.class);
        ObjectMapper objectMapper = new ObjectMapper();
        bcosMessage.setData(objectMapper.writeValueAsBytes(request));
        ByteBuf byteBuf = ctx.alloc().buffer();
        bcosMessage.writeHeader(byteBuf);
        bcosMessage.writeExtra(byteBuf);
        ctx.writeAndFlush(byteBuf);

        channelService
                .getSeq2Callback()
                .put(
                        seq,
                        new BcosResponseCallback() {

                            @Override
                            public void onResponse(BcosResponse response) {
                                try {
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    BlockNumber blockNumber =
                                            objectMapper.readValue(
                                                    response.getContent(), BlockNumber.class);
                                    SocketChannel socketChannel = (SocketChannel) ctx.channel();
                                    InetSocketAddress socketAddress = socketChannel.remoteAddress();
                                    channelService
                                            .getNodeToBlockNumberMap()
                                            .put(
                                                    socketAddress.getAddress().getHostAddress()
                                                            + socketAddress.getPort(),
                                                    blockNumber.getBlockNumber());
                                } catch (Exception e) {
                                    throw new MessageDecodingException(response.getContent());
                                }
                            }
                        });
    }

    @Override
    public void onDisconnect(ChannelHandlerContext ctx) {}

    @Override
    public void onMessage(ChannelHandlerContext ctx, ByteBuf message) {
        try {
            Message msg = new Message();
            msg.readHeader(message);

            // Get the version of the channel protocol
            // NodeChlProVersion nodeChannelVersion = channelService.getNodeChlProVersion(ctx);

            if (msg.getType() == 0x20 || msg.getType() == 0x21) {
                ChannelMessage channelMessage = new ChannelMessage(msg);
                channelMessage.readExtra(message);
                channelService.onReceiveChannelMessage(ctx, channelMessage);
            } else if (msg.getType() == 0x30 || msg.getType() == 0x31 || msg.getType() == 0x35) {
                ChannelMessage2 channelMessage = new ChannelMessage2(msg);
                channelMessage.readExtra(message);
                channelService.onReceiveChannelMessage2(ctx, channelMessage);
            } else if (msg.getType() == 0x12) {
                BcosMessage fiscoMessage = new BcosMessage(msg);
                fiscoMessage.readExtra(message);
                channelService.onReceiveEthereumMessage(ctx, fiscoMessage);
            } else if (msg.getType() == 0x13) {
                msg.readExtra(message);
                channelService.onReceiveHeartbeat(ctx, msg);
            } else if (msg.getType() == 0x14) {
                BcosMessage fiscoMessage = new BcosMessage(msg);
                fiscoMessage.readExtra(message);
                channelService.onReceiveChannelProtocolVersion(ctx, fiscoMessage);
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

    @Override
    public void sendHeartbeat(ChannelHandlerContext ctx) {
        channelService.sendHeartbeatMessage(ctx);
    }
}
