package org.fisco.bcos.channel.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.fisco.bcos.channel.client.BcosResponseCallback;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.dto.BcosMessage;
import org.fisco.bcos.channel.dto.BcosResponse;
import org.fisco.bcos.channel.dto.ChannelMessage2;
import org.fisco.bcos.channel.dto.TopicVerifyMessage;
import org.fisco.bcos.channel.protocol.ChannelHandshake;
import org.fisco.bcos.channel.protocol.ChannelMessageError;
import org.fisco.bcos.channel.protocol.ChannelMessageType;
import org.fisco.bcos.channel.protocol.ChannelPrococolExceiption;
import org.fisco.bcos.channel.protocol.ChannelProtocol;
import org.fisco.bcos.channel.protocol.EnumChannelProtocolVersion;
import org.fisco.bcos.channel.protocol.EnumSocketChannelAttributeKey;
import org.fisco.bcos.fisco.EnumNodeVersion;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.Request;
import org.fisco.bcos.web3j.protocol.core.Response;
import org.fisco.bcos.web3j.protocol.core.methods.response.BlockNumber;
import org.fisco.bcos.web3j.protocol.core.methods.response.NodeVersion;
import org.fisco.bcos.web3j.protocol.exceptions.MessageDecodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionCallback implements ChannelConnections.Callback {
    private static Logger logger = LoggerFactory.getLogger(ConnectionCallback.class);

    private Integer connectTimeoutMS = 10000;
    // getClientVersion interface timeout, unit millisecond
    private Integer queryNodeVersionTimeoutMS = 5000;

    public Integer getQueryNodeVersionTimeoutMS() {
        return queryNodeVersionTimeoutMS;
    }

    public void setQueryNodeVersionTimeoutMS(Integer queryNodeVersionTimeoutMS) {
        this.queryNodeVersionTimeoutMS = queryNodeVersionTimeoutMS;
    }

    public Integer getConnectTimeoutMS() {
        return connectTimeoutMS;
    }

    public void setConnectTimeoutMS(Integer connectTimeoutMS) {
        this.connectTimeoutMS = connectTimeoutMS;
    }

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
        String host = ChannelHandlerContextHelper.getPeerHost(ctx);
        logger.info(" connect {} success, ctx: {}", host, System.identityHashCode(ctx));
        try {
            // must set to BigInteger.ONE
            // since sdk only get and initialize blockNumber when the init blockNumber is
            // BigInteger.ONE
            channelService.setNumber(BigInteger.ONE);

            // query connected node version for deciding if send channel protocol handshake packet
            queryNodeVersion(ctx);
        } catch (JsonProcessingException e) {
            logger.error(
                    " query node version exception, ctx: {}, message: {} ", ctx, e.getMessage());

            ctx.writeAndFlush("").addListener(ChannelFutureListener.CLOSE);
        }
    }

    private void queryChannelProtocolVersion(ChannelHandlerContext ctx)
            throws ChannelPrococolExceiption, IOException {

        final String host = ChannelHandlerContextHelper.getPeerHost(ctx);

        ChannelHandshake channelHandshake = new ChannelHandshake();
        String seq = UUID.randomUUID().toString().replaceAll("-", "");

        byte[] payload = ObjectMapperFactory.getObjectMapper().writeValueAsBytes(channelHandshake);
        String content = new String(payload);

        logger.debug(
                " channel protocol handshake, host: {}, seq: {}, content: {}", host, seq, content);

        BcosMessage bcosMessage = new BcosMessage();
        bcosMessage.setType((short) ChannelMessageType.CLIENT_HANDSHAKE.getType());
        bcosMessage.setSeq(seq);
        bcosMessage.setResult(0);
        bcosMessage.setData(payload);

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
                                    if (response.getErrorCode() != 0) {

                                        logger.error(
                                                " channel protocol handshake request failed, code: {}, message: {}",
                                                response.getErrorCode(),
                                                response.getErrorMessage());

                                        throw new ChannelPrococolExceiption(
                                                " channel protocol handshake request failed, code: "
                                                        + response.getErrorCode()
                                                        + ", message: "
                                                        + response.getErrorMessage());
                                    }

                                    ChannelProtocol channelProtocol =
                                            ObjectMapperFactory.getObjectMapper()
                                                    .readValue(
                                                            response.getContent(),
                                                            ChannelProtocol.class);

                                    EnumChannelProtocolVersion enumChannelProtocolVersion =
                                            EnumChannelProtocolVersion.toEnum(
                                                    channelProtocol.getProtocol());
                                    channelProtocol.setEnumProtocol(enumChannelProtocolVersion);

                                    logger.info(
                                            " channel protocol handshake success, set socket channel protocol, host: {}, channel protocol: {}",
                                            host,
                                            channelProtocol);

                                    ctx.channel()
                                            .attr(
                                                    AttributeKey.valueOf(
                                                            EnumSocketChannelAttributeKey
                                                                    .CHANNEL_PROTOCOL_KEY.getKey()))
                                            .set(channelProtocol);

                                    //
                                    sendUpdateTopicMessage(ctx);
                                    queryBlockNumber(ctx);
                                    // channelService.getEventLogFilterManager().sendFilter();

                                } catch (Exception e) {
                                    logger.error(
                                            " channel protocol handshake failed, exception: {}",
                                            e.getMessage());

                                    ctx.writeAndFlush("").addListener(ChannelFutureListener.CLOSE);
                                }
                            }
                        });
    }

    private void queryNodeVersion(ChannelHandlerContext ctx) throws JsonProcessingException {

        final String host = ChannelHandlerContextHelper.getPeerHost(ctx);

        String seq = UUID.randomUUID().toString().replaceAll("-", "");

        Request<?, NodeVersion> request =
                new Request<>("getClientVersion", Arrays.asList(), null, NodeVersion.class);

        byte[] payload = ObjectMapperFactory.getObjectMapper().writeValueAsBytes(request);
        String content = new String(payload);

        logger.info(" query node version host: {}, seq: {}, content: {}", host, seq, content);

        BcosMessage bcosMessage = new BcosMessage();
        bcosMessage.setType((short) ChannelMessageType.CHANNEL_RPC_REQUEST.getType());
        bcosMessage.setSeq(seq);
        bcosMessage.setResult(0);
        bcosMessage.setData(payload);

        ByteBuf byteBuf = ctx.alloc().buffer();
        bcosMessage.writeHeader(byteBuf);
        bcosMessage.writeExtra(byteBuf);
        ctx.writeAndFlush(byteBuf);

        BcosResponseCallback callback =
                new BcosResponseCallback() {

                    @Override
                    public void onResponse(BcosResponse response) {
                        try {
                            if (response.getErrorCode()
                                    == ChannelMessageError.MESSAGE_TIMEOUT.getError()) {
                                // The fisco node version number is below 2.1.0 when request timeout
                                ChannelHandlerContextHelper.setProtocolVersion(
                                        ctx,
                                        EnumChannelProtocolVersion.VERSION_1,
                                        "below-2.1.0-timeout");

                                logger.info(
                                        " query node version timeout, content: {}",
                                        response.getContent());
                                sendUpdateTopicMessage(ctx);
                                queryBlockNumber(ctx);
                                return;
                            } else if (response.getErrorCode() != 0) {

                                logger.error(
                                        " fisco node version response, code: {}, message: {}",
                                        response.getErrorCode(),
                                        response.getErrorMessage());

                                throw new ChannelPrococolExceiption(
                                        " query node version failed, code: "
                                                + response.getErrorCode()
                                                + ", message: "
                                                + response.getErrorMessage());
                            }

                            Response<NodeVersion.Version> nodeVersion =
                                    ObjectMapperFactory.getObjectMapper()
                                            .readValue(response.getContent(), NodeVersion.class);

                            logger.info(
                                    " node: {}, content: {}",
                                    nodeVersion.getResult(),
                                    response.getContent());

                            if (EnumNodeVersion.channelProtocolHandleShakeSupport(
                                    nodeVersion.getResult().getSupportedVersion())) {
                                // fisco node support channel protocol handshake, start it

                                logger.info(
                                        " support channel handshake node: {}, content: {}",
                                        nodeVersion.getResult(),
                                        response.getContent());

                                queryChannelProtocolVersion(ctx);
                            } else { // default channel protocol

                                ChannelHandlerContextHelper.setProtocolVersion(
                                        ctx,
                                        EnumChannelProtocolVersion.VERSION_1,
                                        nodeVersion.getResult().getSupportedVersion());

                                logger.info(
                                        " not support channel handshake set default ,node: {}, content: {}",
                                        nodeVersion.getResult(),
                                        response.getContent());

                                sendUpdateTopicMessage(ctx);
                                queryBlockNumber(ctx);
                                // channelService.getEventLogFilterManager().sendFilter();
                            }

                        } catch (Exception e) {
                            logger.error(" query node version failed, message: {}", e.getMessage());

                            ctx.writeAndFlush("").addListener(ChannelFutureListener.CLOSE);
                        }
                    }
                };

        final BcosResponseCallback callbackInner = callback;

        callback.setTimeout(
                channelService
                        .getTimeoutHandler()
                        .newTimeout(
                                new TimerTask() {
                                    BcosResponseCallback _callback = callbackInner;

                                    @Override
                                    public void run(Timeout timeout) throws Exception {
                                        // handle timer
                                        _callback.onTimeout();
                                    }
                                },
                                queryNodeVersionTimeoutMS,
                                TimeUnit.MILLISECONDS));

        channelService.getSeq2Callback().put(seq, callback);
    }

    public void sendUpdateTopicMessage(ChannelHandlerContext ctx) throws JsonProcessingException {

        Message message = new Message();
        message.setResult(0);
        message.setType((short) ChannelMessageType.AMOP_CLIENT_TOPICS.getType());
        message.setSeq(UUID.randomUUID().toString().replaceAll("-", ""));

        topics.add("_block_notify_" + channelService.getGroupId());

        message.setData(ObjectMapperFactory.getObjectMapper().writeValueAsBytes(topics.toArray()));

        String content = new String(message.getData());

        ByteBuf out = ctx.alloc().buffer();
        message.writeHeader(out);
        message.writeExtra(out);

        ctx.writeAndFlush(out);

        logger.info(
                " send update topic message request, seq: {}, content: {}",
                message.getSeq(),
                content);
    }

    private void queryBlockNumber(ChannelHandlerContext ctx) throws JsonProcessingException {

        final String host = ChannelHandlerContextHelper.getPeerHost(ctx);

        String seq = channelService.newSeq();

        BcosMessage bcosMessage = new BcosMessage();
        bcosMessage.setType((short) ChannelMessageType.CHANNEL_RPC_REQUEST.getType());
        bcosMessage.setSeq(seq);
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(channelService);

        Request<Integer, BlockNumber> request =
                new Request<>(
                        "getBlockNumber",
                        Arrays.asList(channelService.getGroupId()),
                        channelEthereumService,
                        BlockNumber.class);

        bcosMessage.setData(ObjectMapperFactory.getObjectMapper().writeValueAsBytes(request));
        ByteBuf byteBuf = ctx.alloc().buffer();
        bcosMessage.writeHeader(byteBuf);
        bcosMessage.writeExtra(byteBuf);
        ctx.writeAndFlush(byteBuf);

        String content = new String(bcosMessage.getData());
        logger.info(" query block number host: {}, seq: {}, content: {}", host, seq, content);

        channelService
                .getSeq2Callback()
                .put(
                        seq,
                        new BcosResponseCallback() {
                            @Override
                            public void onResponse(BcosResponse response) {
                                try {
                                    BlockNumber blockNumber =
                                            ObjectMapperFactory.getObjectMapper()
                                                    .readValue(
                                                            response.getContent(),
                                                            BlockNumber.class);

                                    SocketChannel socketChannel = (SocketChannel) ctx.channel();
                                    InetSocketAddress socketAddress = socketChannel.remoteAddress();
                                    channelService
                                            .getNodeToBlockNumberMap()
                                            .put(
                                                    socketAddress.getAddress().getHostAddress()
                                                            + socketAddress.getPort(),
                                                    blockNumber.getBlockNumber());

                                    logger.info(
                                            " query blocknumer, host:{}, blockNumber: {} ",
                                            host,
                                            blockNumber.getBlockNumber());
                                } catch (Exception e) {
                                    logger.error(
                                            " query blocknumer failed, host: {}, message: {} ",
                                            host,
                                            e.getMessage());

                                    throw new MessageDecodingException(response.getContent());
                                }
                            }
                        });
    }

    @Override
    public void onDisconnect(ChannelHandlerContext ctx) {
        final String host = ChannelHandlerContextHelper.getPeerHost(ctx);
        channelService.getEventLogFilterManager().updateEventLogFilterStatus(ctx);
        logger.debug(" disconnect, host: {}, ctx: {}", host, System.identityHashCode(ctx));
    }

    @Override
    public void onMessage(ChannelHandlerContext ctx, ByteBuf message) {
        try {
            Message msg = new Message();

            try {
                msg.readHeader(message);
            } catch (Exception e) {
                String host =
                        ((SocketChannel) ctx.channel())
                                .remoteAddress()
                                .getAddress()
                                .getHostAddress();
                Integer port = ((SocketChannel) ctx.channel()).remoteAddress().getPort();

                logger.error(
                        " Maybe p2p port is used to channel connection, please check the configuration, peer {}:{}",
                        host,
                        port);
                throw new RuntimeException(e.getCause());
            }

            logger.trace(
                    "onMessage, seq:{}, type: {}, result: {}",
                    msg.getSeq(),
                    msg.getType(),
                    msg.getResult());

            if (msg.getType() == ChannelMessageType.AMOP_REQUEST.getType()
                    || msg.getType() == ChannelMessageType.AMOP_RESPONSE.getType()
                    || msg.getType() == ChannelMessageType.AMOP_MULBROADCAST.getType()) {
                ChannelMessage2 channelMessage = new ChannelMessage2(msg);
                channelMessage.readExtra(message);
                channelService.onReceiveChannelMessage2(ctx, channelMessage);
            } else if (msg.getType() == ChannelMessageType.CHANNEL_RPC_REQUEST.getType()) {
                BcosMessage fiscoMessage = new BcosMessage(msg);
                fiscoMessage.readExtra(message);
                channelService.onReceiveEthereumMessage(ctx, fiscoMessage);
            } else if (msg.getType() == ChannelMessageType.CLIENT_HEARTBEAT.getType()) {
                msg.readExtra(message);
                channelService.onReceiveHeartbeat(ctx, msg);
            } else if (msg.getType() == ChannelMessageType.CLIENT_HANDSHAKE.getType()) {
                BcosMessage fiscoMessage = new BcosMessage(msg);
                fiscoMessage.readExtra(message);
                channelService.onReceiveEthereumMessage(ctx, fiscoMessage);
            } else if (msg.getType() == ChannelMessageType.CLIENT_REGISTER_EVENT_LOG.getType()) {
                ChannelMessage2 channelMessage = new ChannelMessage2(msg);
                channelMessage.readExtra(message);
                channelService.onReceiveRegisterEventResponse(ctx, channelMessage);
            } else if (msg.getType() == ChannelMessageType.TRANSACTION_NOTIFY.getType()) {
                BcosMessage fiscoMessage = new BcosMessage(msg);
                fiscoMessage.readExtra(message);
                channelService.onReceiveTransactionMessage(ctx, fiscoMessage);
            } else if (msg.getType() == ChannelMessageType.BLOCK_NOTIFY.getType()) {
                // new block notify
                ChannelMessage2 channelMessage = new ChannelMessage2(msg);
                channelMessage.readExtra(message);
                channelService.onReceiveBlockNotify(ctx, channelMessage);
            } else if (msg.getType() == ChannelMessageType.EVENT_LOG_PUSH.getType()) {
                BcosMessage bcosMessage = new BcosMessage(msg);
                bcosMessage.readExtra(message);
                channelService.onReceiveEventLogPush(ctx, bcosMessage);
            } else if (msg.getType() == ChannelMessageType.REQUEST_TOPICCERT.getType()) {
                logger.info("get generate rand value request data");
                TopicVerifyMessage channelMessage = new TopicVerifyMessage(msg);
                channelMessage.readExtra(message);
                try {
                    channelService.checkTopicVerify(ctx, channelMessage);
                } catch (IOException e) {
                    logger.error("on receive channel failed");
                }
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
