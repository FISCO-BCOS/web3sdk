package org.fisco.bcos.channel.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.fisco.bcos.channel.dto.BcosBlkNotify;
import org.fisco.bcos.channel.dto.BcosMessage;
import org.fisco.bcos.channel.dto.BcosRequest;
import org.fisco.bcos.channel.dto.BcosResponse;
import org.fisco.bcos.channel.dto.ChannelMessage;
import org.fisco.bcos.channel.dto.ChannelMessage2;
import org.fisco.bcos.channel.dto.ChannelPush;
import org.fisco.bcos.channel.dto.ChannelPush2;
import org.fisco.bcos.channel.dto.ChannelRequest;
import org.fisco.bcos.channel.dto.ChannelResponse;
import org.fisco.bcos.channel.handler.ChannelConnections;
import org.fisco.bcos.channel.handler.ConnectionCallback;
import org.fisco.bcos.channel.handler.ConnectionInfo;
import org.fisco.bcos.channel.handler.GroupChannelConnectionsConfig;
import org.fisco.bcos.channel.handler.Message;
import org.fisco.bcos.channel.protocol.NodeVersion;
import org.fisco.bcos.channel.protocol.SDKVersion;
import org.fisco.bcos.channel.protocol.parser.BlkNotifyParser;
import org.fisco.bcos.channel.protocol.parser.HeartBeatParser;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.protocol.exceptions.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class Service {
    private static Logger logger = LoggerFactory.getLogger(Service.class);
    private Integer connectMillis = 30000;

    public Integer getConnectMillis() {
        return connectMillis;
    }

    public void setConnectMillis(Integer connectMillis) {
        this.connectMillis = connectMillis;
    }

    private Integer connectSleepPerMillis = 1;
    private String orgID;
    private String agencyName;
    private GroupChannelConnectionsConfig allChannelConnections;
    private ChannelPushCallback pushCallback;
    private Map<String, Object> seq2Callback = new ConcurrentHashMap<String, Object>();
    private int groupId;
    private static ObjectMapper objectMapper = new ObjectMapper();
    private BigInteger number = BigInteger.valueOf(0);
    private ConcurrentHashMap<String, BigInteger> nodeToBlockNumberMap = new ConcurrentHashMap<>();
    /** add transaction seq callback */
    private Map<String, Object> seq2TransactionCallback = new ConcurrentHashMap<String, Object>();

    private Timer timeoutHandler = new HashedWheelTimer();
    private ThreadPoolTaskExecutor threadPool;
    private BlockNotifyCallBack blockNotifyCallBack = new DefaultBlockNotifyCallBack();

    private Set<String> topics = new HashSet<String>();

    public BlockNotifyCallBack getBlockNotifyCallBack() {
        return blockNotifyCallBack;
    }

    public void setBlockNotifyCallBack(BlockNotifyCallBack blockNotifyCallBack) {
        this.blockNotifyCallBack = blockNotifyCallBack;
    }

    public void setTopics(Set<String> topics) {
        try {
            this.topics = topics;
        } catch (Exception e) {
            logger.error("system error", e);
        }
    }

    public ConcurrentHashMap<String, BigInteger> getNodeToBlockNumberMap() {
        return nodeToBlockNumberMap;
    }

    public void setNodeToBlockNumberMap(
            ConcurrentHashMap<String, BigInteger> nodeToBlockNumberMap) {
        this.nodeToBlockNumberMap = nodeToBlockNumberMap;
    }

    public boolean flushTopics(List<String> topics) {

        try {
            Message message = new Message();
            message.setResult(0);
            message.setType((short) 0x32); // topic设置topic消息0x32
            message.setSeq(UUID.randomUUID().toString().replaceAll("-", ""));

            message.setData(objectMapper.writeValueAsBytes(topics.toArray()));

            ChannelConnections fromChannelConnections =
                    allChannelConnections
                            .getAllChannelConnections()
                            .stream()
                            .filter(x -> x.getGroupId() == groupId)
                            .findFirst()
                            .get();

            if (fromChannelConnections == null) {
                logger.error("not found and connections which orgId is:{}", orgID);
                return false;
            }

            for (String key : fromChannelConnections.getNetworkConnections().keySet()) {
                ChannelHandlerContext ctx = fromChannelConnections.getNetworkConnections().get(key);

                if (ctx != null && ctx.channel().isActive()) {
                    ByteBuf out = ctx.alloc().buffer();
                    message.writeHeader(out);
                    message.writeExtra(out);
                    ctx.writeAndFlush(out);

                    String host =
                            ((SocketChannel) ctx.channel())
                                    .remoteAddress()
                                    .getAddress()
                                    .getHostAddress();
                    Integer port = ((SocketChannel) ctx.channel()).remoteAddress().getPort();

                    logger.info(
                            "try to flush seq:{} topics:{} to host:{} port :{}",
                            message.getSeq(),
                            topics,
                            host,
                            port);
                }
            }

            return true;
        } catch (Exception e) {
            logger.error("flushTopics exception", e);
        }

        return false;
    }

    public Set<String> getTopics() {
        return this.topics;
    }

    public Integer getConnectSleepPerMillis() {
        return connectSleepPerMillis;
    }

    public void setConnectSleepPerMillis(Integer connectSleepPerMillis) {
        this.connectSleepPerMillis = connectSleepPerMillis;
    }

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public ChannelPushCallback getPushCallback() {
        return pushCallback;
    }

    public void setPushCallback(ChannelPushCallback pushCallback) {
        this.pushCallback = pushCallback;
    }

    public GroupChannelConnectionsConfig getAllChannelConnections() {
        return allChannelConnections;
    }

    public void setAllChannelConnections(GroupChannelConnectionsConfig allChannelConnections) {
        this.allChannelConnections = allChannelConnections;
    }

    public void run() throws Exception {
        logger.debug("init ChannelService");
        int flag = 0;
        for (ChannelConnections channelConnections :
                allChannelConnections.getAllChannelConnections()) {

            if (channelConnections.getGroupId() == groupId) {
                flag = 1;
                try {
                    ConnectionCallback connectionCallback = new ConnectionCallback(topics);
                    connectionCallback.setChannelService(this);

                    channelConnections.setCallback(connectionCallback);
                    channelConnections.init();
                    channelConnections.setThreadPool(threadPool);
                    channelConnections.startConnect();

                    int sleepTime = 0;
                    boolean running = false;
                    while (true) {
                        Map<String, ChannelHandlerContext> networkConnection =
                                channelConnections.getNetworkConnections();
                        for (String key : networkConnection.keySet()) {
                            if (networkConnection.get(key) != null
                                    && networkConnection.get(key).channel().isActive()) {
                                running = true;
                                break;
                            }
                        }

                        if (running || sleepTime > connectMillis) {
                            break;
                        } else {
                            Thread.sleep(connectSleepPerMillis);
                            sleepTime += connectSleepPerMillis;
                        }
                    }

                    if (!running) {
                        logger.error("connectMillis = " + connectMillis);
                        logger.error(
                                "can not connect to nodes success, please checkout the node status and the sdk config!");
                        throw new Exception(
                                "Can not connect to nodes success, please checkout the node status and the sdk config!");
                    }
                } catch (InterruptedException e) {
                    logger.error("system error ", e);
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    logger.error("system error ", e);
                    throw e;
                }
            }
        }
        if (flag == 0) {
            throw new Exception("Please set the right groupId");
        }
    }

    public BcosResponse sendEthereumMessage(BcosRequest request) {
        class Callback extends BcosResponseCallback {
            public BcosResponse bcosResponse;
            public Semaphore semaphore = new Semaphore(1, true);

            Callback() {
                try {
                    semaphore.acquire(1);

                } catch (InterruptedException e) {
                    logger.error("error :", e);
                    Thread.currentThread().interrupt();
                }
            }

            @Override
            public void onResponse(BcosResponse response) {
                bcosResponse = response;

                if (bcosResponse != null && bcosResponse.getContent() != null) {
                    logger.debug("response: {}", response.getContent());
                } else {
                    logger.error("response is null");
                }

                semaphore.release();
            }
        };

        Callback callback = new Callback();

        asyncSendEthereumMessage(request, callback);
        try {
            callback.semaphore.acquire(1);
        } catch (InterruptedException e) {
            logger.error("system error:", e);
            Thread.currentThread().interrupt();
        }

        return callback.bcosResponse;
    }

    public BcosResponse sendEthereumMessage(
            BcosRequest request, TransactionSucCallback transactionSucCallback) {
        class Callback extends BcosResponseCallback {
            public BcosResponse ethereumResponse;
            public Semaphore semaphore = new Semaphore(1, true);

            Callback() {
                try {
                    semaphore.acquire(1);
                } catch (InterruptedException e) {
                    logger.error("error:", e);
                    Thread.currentThread().interrupt();
                }
            }

            @Override
            public void onResponse(BcosResponse response) {
                ethereumResponse = response;
                semaphore.release();
            }
        }

        Callback callback = new Callback();
        asyncSendEthereumMessage(request, callback, transactionSucCallback);
        try {
            callback.semaphore.acquire(1);
        } catch (InterruptedException e) {
            logger.error("system error:", e);
            Thread.currentThread().interrupt();
        }

        return callback.ethereumResponse;
    }

    public void asyncSendEthereumMessage(
            BcosRequest request,
            BcosResponseCallback fiscoResponseCallback,
            TransactionSucCallback transactionSucCallback) {
        this.asyncSendEthereumMessage(request, fiscoResponseCallback);
        if (request.getTimeout() > 0) {
            final TransactionSucCallback callbackInner = transactionSucCallback;
            callbackInner.setTimeout(
                    timeoutHandler.newTimeout(
                            new TimerTask() {
                                @Override
                                public void run(Timeout timeout) throws Exception {
                                    // 处理超时逻辑
                                    callbackInner.onTimeout();
                                    // timeout时清除map的数据,所以尽管后面有回包数据，也会找不到seq->callback的关系
                                    seq2TransactionCallback.remove(request.getMessageID());
                                }
                            },
                            request.getTimeout(),
                            TimeUnit.MILLISECONDS));
            this.seq2TransactionCallback.put(request.getMessageID(), callbackInner);
        } else {
            this.seq2TransactionCallback.put(request.getMessageID(), transactionSucCallback);
        }
    }

    public ChannelResponse sendChannelMessage2(ChannelRequest request) {
        class Callback extends ChannelResponseCallback2 {
            Callback() {
                try {
                    semaphore.acquire(1);

                } catch (InterruptedException e) {
                    logger.error("error:", e);
                    Thread.currentThread().interrupt();
                }
            }

            @Override
            public void onResponseMessage(ChannelResponse response) {
                channelResponse = response;

                logger.debug("response: {}", response.getContent());

                semaphore.release();
            }

            public ChannelResponse channelResponse;
            public Semaphore semaphore = new Semaphore(1, true);
        };

        Callback callback = new Callback();

        asyncSendChannelMessage2(request, callback);
        try {
            callback.semaphore.acquire(1);
        } catch (InterruptedException e) {
            logger.error("system error:", e);
            Thread.currentThread().interrupt();
        }

        return callback.channelResponse;
    }

    public void asyncSendEthereumMessage(BcosRequest request, BcosResponseCallback callback) {
        Boolean sended = false;

        BcosMessage bcosMessage = new BcosMessage();

        bcosMessage.setSeq(request.getMessageID());
        bcosMessage.setResult(0);
        bcosMessage.setType((short) 0x12);
        bcosMessage.setData(request.getContent().getBytes());
        // select node
        try {
            ChannelConnections channelConnections =
                    allChannelConnections
                            .getAllChannelConnections()
                            .stream()
                            .filter(x -> x.getGroupId() == groupId)
                            .findFirst()
                            .get();

            if (channelConnections == null) {
                if (orgID != null) {
                    logger.error("not found:{}", orgID);
                    throw new TransactionException("not found orgID");
                } else {
                    logger.error("not found:{}", agencyName);
                    throw new TransactionException("not found agencyName");
                }
            }
            ChannelHandlerContext ctx =
                    channelConnections.randomNetworkConnection(nodeToBlockNumberMap);

            ByteBuf out = ctx.alloc().buffer();
            bcosMessage.writeHeader(out);
            bcosMessage.writeExtra(out);

            seq2Callback.put(request.getMessageID(), callback);

            if (request.getTimeout() > 0) {
                final BcosResponseCallback callbackInner = callback;
                callback.setTimeout(
                        timeoutHandler.newTimeout(
                                new TimerTask() {
                                    BcosResponseCallback _callback = callbackInner;

                                    @Override
                                    public void run(Timeout timeout) throws Exception {
                                        // handle timer
                                        _callback.onTimeout();
                                    }
                                },
                                request.getTimeout(),
                                TimeUnit.MILLISECONDS));
            }

            ctx.writeAndFlush(out);
            sended = true;
            SocketChannel socketChannel = (SocketChannel) ctx.channel();
            InetSocketAddress socketAddress = socketChannel.remoteAddress();
            logger.debug(
                    "selected node {}:{} bcos request, seq:{}",
                    socketAddress.getAddress().getHostAddress(),
                    socketAddress.getPort(),
                    bcosMessage.getSeq());

        } catch (Exception e) {
            logger.error("system error: " + e);

            BcosResponse response = new BcosResponse();
            response.setErrorCode(-1);
            response.setErrorMessage(
                    e.getMessage()
                            + " Requset send failed! Can not connect to nodes success, please checkout the node status and the sdk config!");
            response.setContent("");
            response.setMessageID(request.getMessageID());

            if (callback.getTimeout() != null) {
                callback.getTimeout().cancel();
            }
            callback.onResponse(response);
        }
    }

    public void asyncSendChannelMessage2(
            ChannelRequest request, ChannelResponseCallback2 callback) {
        try {

            if (request.getContentByteArray().length >= 32 * 1024 * 1024) {
                logger.error(
                        "send byte length should not greater than 32M now length:{}",
                        request.getContentByteArray().length);
                System.out.println(
                        "send byte length should not greater than 32M now length:"
                                + request.getContentByteArray().length);
                throw new AmopException("send byte length should not greater than 32M");
            }

            logger.debug("ChannelRequest: " + request.getMessageID());
            callback.setService(this);

            ChannelMessage2 channelMessage = new ChannelMessage2();

            channelMessage.setSeq(request.getMessageID());
            channelMessage.setResult(0);
            channelMessage.setType((short) 0x30); // 链上链下请求0x30
            channelMessage.setData(request.getContentByteArray());

            System.out.println(
                    "value length:"
                            + request.getContentByteArray().length
                            + ":"
                            + request.getContent().getBytes().length);
            channelMessage.setTopic(request.getToTopic());

            try {
                List<ConnectionInfo> fromConnectionInfos = new ArrayList<ConnectionInfo>();

                // 设置发送节点
                ChannelConnections fromChannelConnections =
                        allChannelConnections
                                .getAllChannelConnections()
                                .stream()
                                .filter(x -> x.getGroupId() == groupId)
                                .findFirst()
                                .get();
                if (fromChannelConnections == null) {
                    // 没有找到对应的链
                    // 返回错误
                    if (orgID != null) {
                        logger.error("not found:{}", orgID);
                        throw new Exception("not found orgID");
                    } else {
                        logger.error("not found:{}", agencyName);
                        throw new Exception("not found agencyName");
                    }
                }
                fromConnectionInfos.addAll(fromChannelConnections.getConnections());
                logger.debug(
                        "FromOrg:{} nodes:{}",
                        request.getFromOrg(),
                        fromChannelConnections.getConnections().size());

                callback.setFromChannelConnections(fromChannelConnections);
                callback.setFromConnectionInfos(fromConnectionInfos);

                // 设置消息内容
                callback.setRequest(channelMessage);

                seq2Callback.put(request.getMessageID(), callback);

                if (request.getTimeout() > 0) {
                    final ChannelResponseCallback2 callbackInner = callback;
                    callback.setTimeout(
                            timeoutHandler.newTimeout(
                                    new TimerTask() {
                                        ChannelResponseCallback2 _callback = callbackInner;

                                        @Override
                                        public void run(Timeout timeout) throws Exception {
                                            // 处理超时逻辑
                                            _callback.onTimeout();
                                        }
                                    },
                                    request.getTimeout(),
                                    TimeUnit.MILLISECONDS));
                }

                callback.retrySendMessage();
            } catch (Exception e) {
                logger.error("send message fail:", e);

                ChannelResponse response = new ChannelResponse();
                response.setErrorCode(100);
                response.setMessageID(request.getMessageID());
                response.setErrorMessage(e.getMessage());
                response.setContent("");

                callback.onResponse(response);
                return;
            }
        } catch (Exception e) {
            logger.error("system error", e);
        }
    }

    public void asyncMulticastChannelMessage2(ChannelRequest request) {
        try {
            logger.debug("ChannelRequest: " + request.getMessageID());

            ChannelMessage2 channelMessage = new ChannelMessage2();

            channelMessage.setSeq(request.getMessageID());
            channelMessage.setResult(0);
            channelMessage.setType((short) 0x35); // 链上链下多播请求0x35
            channelMessage.setData(request.getContentByteArray());
            channelMessage.setTopic(request.getToTopic());

            try {
                // 设置发送节点
                ChannelConnections fromChannelConnections =
                        allChannelConnections
                                .getAllChannelConnections()
                                .stream()
                                .filter(x -> x.getGroupId() == groupId)
                                .findFirst()
                                .get();
                if (fromChannelConnections == null) {
                    // 没有找到对应的链
                    // 返回错误
                    if (orgID != null) {
                        logger.error("not found:{}", orgID);
                        throw new Exception("not found orgID");
                    } else {
                        logger.error("not found:{}", agencyName);
                        throw new Exception("not found agencyName");
                    }
                }

                logger.debug(
                        "FromOrg:{} nodes:{}",
                        request.getFromOrg(),
                        fromChannelConnections.getConnections().size());

                for (ConnectionInfo connectionInfo : fromChannelConnections.getConnections()) {
                    ChannelHandlerContext ctx =
                            fromChannelConnections.getNetworkConnectionByHost(
                                    connectionInfo.getHost(), connectionInfo.getPort());

                    if (ctx != null && ctx.channel().isActive()) {
                        ByteBuf out = ctx.alloc().buffer();
                        channelMessage.writeHeader(out);
                        channelMessage.writeExtra(out);

                        ctx.writeAndFlush(out);

                        logger.debug(
                                "send message to  "
                                        + connectionInfo.getHost()
                                        + ":"
                                        + String.valueOf(connectionInfo.getPort())
                                        + " 成功");
                    } else {
                        logger.error(
                                "sending node unavailable, {}",
                                connectionInfo.getHost()
                                        + ":"
                                        + String.valueOf(connectionInfo.getPort()));
                    }
                }
            } catch (Exception e) {
                logger.error("send message fail:", e);

                ChannelResponse response = new ChannelResponse();
                response.setErrorCode(100);
                response.setMessageID(request.getMessageID());
                response.setErrorMessage(e.getMessage());
                response.setContent("");

                return;
            }
        } catch (Exception e) {
            logger.error("system error", e);
        }
    }

    public void sendResponseMessage(
            ChannelResponse response,
            ConnectionInfo info,
            ChannelHandlerContext ctx,
            String fromNode,
            String toNode,
            String seq) {
        try {
            ChannelMessage responseMessage = new ChannelMessage();

            responseMessage.setData(response.getContent().getBytes());
            responseMessage.setResult(response.getErrorCode());
            responseMessage.setSeq(seq);
            responseMessage.setType((short) 0x21); // 链上链下回包0x21
            responseMessage.setToNode(fromNode);
            responseMessage.setFromNode(toNode);

            ByteBuf out = ctx.alloc().buffer();
            responseMessage.writeHeader(out);
            responseMessage.writeExtra(out);

            ctx.writeAndFlush(out);

            logger.debug("response seq:{} length:{}", response.getMessageID(), out.readableBytes());
        } catch (Exception e) {
            logger.error("system error", e);
        }
    }

    // 链上链下二期回包
    public void sendResponseMessage2(
            ChannelResponse response, ChannelHandlerContext ctx, String seq, String topic) {
        try {
            ChannelMessage2 responseMessage = new ChannelMessage2();

            responseMessage.setData(response.getContent().getBytes());
            responseMessage.setResult(response.getErrorCode());
            responseMessage.setSeq(seq);
            responseMessage.setType((short) 0x31); // 链上链下二期回包0x31
            responseMessage.setTopic(topic);

            ByteBuf out = ctx.alloc().buffer();
            responseMessage.writeHeader(out);
            responseMessage.writeExtra(out);

            ctx.writeAndFlush(out);

            logger.debug("response seq:{} length:{}", response.getMessageID(), out.readableBytes());
        } catch (Exception e) {
            logger.error("system error:", e);
        }
    }

    public void onReceiveChannelMessage(ChannelHandlerContext ctx, ChannelMessage message) {
        ChannelResponseCallback callback =
                (ChannelResponseCallback) seq2Callback.get(message.getSeq());
        logger.debug("onReceiveChannelMessage seq:{}", message.getSeq());

        if (message.getType() == 0x20) { // 链上链下请求
            logger.debug("channel Message PUSH");
            if (callback != null) {
                // 清空callback再处理
                logger.debug("seq already existed，clean:{}", message.getSeq());
                seq2Callback.remove(message.getSeq());
            }

            try {
                ChannelPush push = new ChannelPush();

                if (pushCallback != null) {
                    push.setService(this);
                    push.setCtx(ctx);
                    push.setMessageID(message.getSeq());
                    push.setFromNode(message.getFromNode());
                    push.setToNode(message.getToNode());
                    push.setSeq(message.getSeq());
                    push.setMessageID(message.getSeq());

                    push.setContent(new String(message.getData(), 0, message.getData().length));

                    pushCallback.onPush(push);
                } else {
                    logger.error("can not push，unset push callback");
                }
            } catch (Exception e) {
                logger.error("pushCallback error:", e);
            }
        } else if (message.getType() == 0x21) { // 链上链下回包
            logger.debug("channel response:{}", message.getSeq());
            if (callback != null) {
                logger.debug("found callback response");

                ChannelResponse response = new ChannelResponse();
                if (message.getResult() != 0) {
                    response.setErrorCode(message.getResult());
                    response.setErrorMessage("response error");
                }

                response.setErrorCode(message.getResult());
                response.setMessageID(message.getSeq());
                if (message.getData() != null) {
                    response.setContent(new String(message.getData()));
                }

                callback.onResponse(response);
            } else {
                logger.error("can not found response callback，timeout:{}", message.getData());
                return;
            }
        }
    }

    public void onReceiveEthereumMessage(ChannelHandlerContext ctx, BcosMessage message) {
        BcosResponseCallback callback = (BcosResponseCallback) seq2Callback.get(message.getSeq());

        if (callback != null) {

            if (callback.getTimeout() != null) {
                callback.getTimeout().cancel();
            }

            BcosResponse response = new BcosResponse();
            if (message.getResult() != 0) {
                response.setErrorMessage("BcosResponse error");
            }

            response.setErrorCode(message.getResult());
            response.setMessageID(message.getSeq());
            response.setContent(new String(message.getData()));

            callback.onResponse(response);

            seq2Callback.remove(message.getSeq());
        } else {
            logger.debug("no callback push message");
        }
    }

    public void onReceiveChannelMessage2(ChannelHandlerContext ctx, ChannelMessage2 message) {
        ChannelResponseCallback2 callback =
                (ChannelResponseCallback2) seq2Callback.get(message.getSeq());
        logger.debug("ChannelResponse seq:{}", message.getSeq());

        if (message.getType() == 0x30 || message.getType() == 0x35) { // 链上链下请求
            logger.debug("channel PUSH");
            if (callback != null) {
                // 清空callback再处理
                logger.debug("seq already existed，clear:{}", message.getSeq());
                seq2Callback.remove(message.getSeq());
            }

            try {
                ChannelPush2 push = new ChannelPush2();

                if (pushCallback != null) {
                    // pushCallback.setInfo(info);
                    push.setSeq(message.getSeq());
                    push.setService(this);
                    push.setCtx(ctx);
                    push.setTopic(message.getTopic());

                    push.setSeq(message.getSeq());
                    push.setMessageID(message.getSeq());
                    System.out.println("data length:" + message.getData().length);
                    logger.info("msg:" + Arrays.toString(message.getData()));
                    push.setContent(message.getData());
                    pushCallback.onPush(push);
                } else {
                    logger.error("can not push，unset push callback");
                }
            } catch (Exception e) {
                logger.error("push error:", e);
            }
        } else if (message.getType() == 0x31) { // 链上链下回包
            logger.debug("channel message:{}", message.getSeq());
            if (callback != null) {
                logger.debug("found callback response");

                ChannelResponse response = new ChannelResponse();
                if (message.getResult() != 0) {
                    response.setErrorCode(message.getResult());
                    response.setErrorMessage("response errors");
                }

                response.setErrorCode(message.getResult());
                response.setMessageID(message.getSeq());
                if (message.getData() != null) {
                    response.setContent(new String(message.getData()));
                }

                callback.onResponse(response);
            } else {
                logger.error("can not found response callback，timeout:{}", message.getData());
                return;
            }
        }
    }

    public void onReceiveBlockNotify(ChannelHandlerContext ctx, ChannelMessage2 message) {
        try {

            BlkNotifyParser blkNotifyParser = new BlkNotifyParser(getUsageChlVersion(ctx));

            String data = new String(message.getData());
            logger.info("Receive block notify: {}", data);
            BcosBlkNotify bcosBlkNotify = null;

            try {
                bcosBlkNotify = blkNotifyParser.decode(message.getData());
            } catch (Exception e) {
                logger.error(" Decode block notify message failed, message is {}", e.getMessage());
                return;
            }

            Integer groupID = Integer.parseInt(bcosBlkNotify.getGroupID());
            BigInteger blkNumber = bcosBlkNotify.getBlockNumber();
            if (!groupID.equals(getGroupId())) {
                logger.error("Received groupID[{}] not match groupID[{}]", groupID, getGroupId());

                return;
            }

            SocketChannel socketChannel = (SocketChannel) ctx.channel();
            String hostAddress = socketChannel.remoteAddress().getAddress().getHostAddress();
            int port = socketChannel.remoteAddress().getPort();

            nodeToBlockNumberMap.put(hostAddress + port, blkNumber);
            // get max blockNumber to set blocklimit
            BigInteger maxBlockNumber = blkNumber;
            for (String key : nodeToBlockNumberMap.keySet()) {
                BigInteger blockNumber = nodeToBlockNumberMap.get(key);
                if (blockNumber.compareTo(maxBlockNumber) >= 0) {
                    maxBlockNumber = blockNumber;
                }
            }
            if (maxBlockNumber.compareTo(getNumber()) > 0) {
                setNumber(maxBlockNumber);

                if (null != getBlockNotifyCallBack()) {
                    if (null == getThreadPool()) {
                        // Thread pool does not exist, the current thread executes the callback
                        // function directly
                        getBlockNotifyCallBack().onBlockNotify(getGroupId(), maxBlockNumber);
                    } else {
                        // Execute the callback function in the thread pool
                        final BigInteger maxBlkNumber = maxBlockNumber;
                        getThreadPool()
                                .execute(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                getBlockNotifyCallBack()
                                                        .onBlockNotify(getGroupId(), maxBlkNumber);
                                            }
                                        });
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Block notify error", e);
        }
    }

    public void sendSDKChannelHighestSupportedMessage(ChannelHandlerContext ctx)
            throws JsonProcessingException {
        // Exchange channel protocol with the underlying node
        Message message = new Message();
        message.setResult(0);
        message.setType((short) 0x14);
        message.setSeq(UUID.randomUUID().toString().replaceAll("-", ""));

        SDKVersion version = new SDKVersion();
        message.setData(objectMapper.writeValueAsBytes(version));

        logger.debug(
                "connection established，send sdk channel supported highest protocol to the connection , seq:{}, protocol version:{}",
                message.getSeq(),
                version);

        ByteBuf out = ctx.alloc().buffer();
        message.writeHeader(out);
        message.writeExtra(out);

        ctx.writeAndFlush(out);
    }

    public void sendHeartbeatMessage(ChannelHandlerContext ctx) {

        Message message = new BcosMessage();
        message.setSeq(UUID.randomUUID().toString().replaceAll("-", ""));
        message.setResult(0);
        message.setType((short) 0x13);

        HeartBeatParser heartBeatParser = new HeartBeatParser(getUsageChlVersion(ctx));

        try {
            message.setData(heartBeatParser.encode("0"));
        } catch (JsonProcessingException e) {
            logger.error(" write json failed, message is {}", e.getMessage());
            return;
        }

        ByteBuf out = ctx.alloc().buffer();
        message.writeHeader(out);
        message.writeExtra(out);

        ctx.writeAndFlush(out);
    }

    public void onReceiveHeartbeat(ChannelHandlerContext ctx, Message msg) {

        String content = "";

        HeartBeatParser heartBeatParser = new HeartBeatParser(getUsageChlVersion(ctx));

        try {
            content = heartBeatParser.decode(msg.getData()).getHeartbeat();
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

            try {
                response.setData(heartBeatParser.encode("1"));
            } catch (JsonProcessingException e) {
                logger.error(" write json failed, message is {} ", e.getMessage());
                return;
            }

            ByteBuf out = ctx.alloc().buffer();
            response.writeHeader(out);
            response.writeExtra(out);

            ctx.writeAndFlush(out);
        } else if ("1".equals(content)) {
            logger.trace("heartbeat response");
        }
    }

    public org.fisco.bcos.channel.protocol.Version getUsageChlVersion(ChannelHandlerContext ctx) {
        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        String hostAddress = socketChannel.remoteAddress().getAddress().getHostAddress();
        int port = socketChannel.remoteAddress().getPort();

        String remoteEndPoint = hostAddress + ":" + port;
        AttributeKey<NodeVersion> attributeKey = AttributeKey.valueOf(remoteEndPoint);

        org.fisco.bcos.channel.protocol.Version version =
                org.fisco.bcos.channel.protocol.Version.VERSION_1;

        if (ctx.channel().hasAttr(attributeKey)) {
            NodeVersion nodeVersion = ctx.channel().attr(attributeKey).get();
            logger.trace(
                    " from socket channel get fisco channel protocol version : {} ", nodeVersion);
            version =
                    org.fisco.bcos.channel.protocol.Version.convert(
                            nodeVersion.getHighestSupported());
        }

        org.fisco.bcos.channel.protocol.Version highestVersion =
                org.fisco.bcos.channel.protocol.Version.getHighestSupported();

        logger.trace(
                " channel version is {}, highestVersion is {}",
                version.getVersionNumber(),
                highestVersion.getVersionNumber());

        if (version.getVersionNumber() > highestVersion.getVersionNumber()) {
            return highestVersion; //
        }

        return version;
    }

    public void onReceiveNodeChannelProtocolVersion(
            ChannelHandlerContext ctx, BcosMessage message) {
        String data = new String(message.getData());

        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        String hostAddress = socketChannel.remoteAddress().getAddress().getHostAddress();
        int port = socketChannel.remoteAddress().getPort();

        String remoteEndPoint = hostAddress + ":" + port;

        logger.info(
                "Receive node from host: {} ,channel protocol version: {}", remoteEndPoint, data);

        try {
            NodeVersion nodeVersion =
                    ObjectMapperFactory.getObjectMapper().readValue(data, NodeVersion.class);

            AttributeKey<NodeVersion> attributeKey = AttributeKey.valueOf(remoteEndPoint);

            if (!ctx.channel().hasAttr(attributeKey)) {
                ctx.channel().attr(attributeKey).set(nodeVersion);
            } else {
                NodeVersion oldNodeChannelVersion = ctx.channel().attr(attributeKey).get();
                logger.warn(
                        " sdk receive channel protocol version info from node again, value is {}",
                        oldNodeChannelVersion);
            }

        } catch (IOException e) {
            logger.error(
                    " receive node protocol version failed, remoteEndPoint is {}, error is {} ",
                    remoteEndPoint,
                    e.getMessage());
        }
    }

    public void onReceiveTransactionMessage(ChannelHandlerContext ctx, BcosMessage message) {
        TransactionSucCallback callback =
                (TransactionSucCallback) seq2TransactionCallback.get(message.getSeq());

        if (callback != null) {
            if (callback.getTimeout() != null) {
                // stop timer，avoid response more once
                callback.getTimeout().cancel();
            }

            try {
                TransactionReceipt receipt =
                        ObjectMapperFactory.getObjectMapper()
                                .readValue(message.getData(), TransactionReceipt.class);
                callback.onResponse(receipt);
            } catch (Exception e) {
                TransactionReceipt receipt = new TransactionReceipt();
                receipt.setStatus("Decode receipt error: " + e.getLocalizedMessage());

                callback.onResponse(receipt);
            }

            seq2TransactionCallback.remove(message.getSeq());
        }
    }

    public String newSeq() {
        String seq = UUID.randomUUID().toString().replaceAll("-", "");
        logger.debug("New Seq" + seq);
        return seq;
    }

    public Map<String, Object> getSeq2Callback() {
        return seq2Callback;
    }

    public void setSeq2Callback(Map<String, Object> seq2Callback) {
        this.seq2Callback = seq2Callback;
    }

    public ThreadPoolTaskExecutor getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPoolTaskExecutor threadPool) {
        this.threadPool = threadPool;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public BigInteger getNumber() {
        return number;
    }

    public void setNumber(BigInteger number) {
        this.number = number;
    }
}
