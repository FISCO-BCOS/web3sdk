package org.fisco.bcos.channel.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLException;
import org.fisco.bcos.channel.protocol.EnumSocketChannelAttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ChannelConnections {
    private static Logger logger = LoggerFactory.getLogger(ChannelConnections.class);

    private Callback callback;
    private List<String> connectionsStr;

    private Resource caCertPath;
    private Resource sslCert;
    private Resource sslKey;
    private List<ConnectionInfo> connections = new ArrayList<ConnectionInfo>();
    private Boolean running = false;
    private ThreadPoolTaskExecutor threadPool;
    private long idleTimeout = (long) 10000;
    private long heartBeatDelay = (long) 2000;
    public Map<String, ChannelHandlerContext> networkConnections =
            new HashMap<String, ChannelHandlerContext>();
    private int groupId;
    private Bootstrap bootstrap = new Bootstrap();
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    
    public Resource getCaCert() {
		return caCertPath;
	}

	public void setCaCert(Resource caCertPath) {
		this.caCertPath = caCertPath;
	}

	public Resource getSslCert() {
		return sslCert;
	}

	public void setSslCert(Resource sslCert) {
		this.sslCert = sslCert;
	}

	public Resource getSslKey() {
		return sslKey;
	}

	public void setSslKey(Resource sslKey) {
		this.sslKey = sslKey;
	}

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public interface Callback {
        void onConnect(ChannelHandlerContext ctx);

        void onDisconnect(ChannelHandlerContext ctx);

        void onMessage(ChannelHandlerContext ctx, ByteBuf message);

        void sendHeartbeat(ChannelHandlerContext ctx);
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public List<String> getConnectionsStr() {
        return connectionsStr;
    }

    public void setConnectionsStr(List<String> connectionsStr) {
        this.connectionsStr = connectionsStr;
    }

    public List<ConnectionInfo> getConnections() {
        return connections;
    }

    public void setConnections(List<ConnectionInfo> connections) {
        this.connections = connections;
    }

    public ThreadPoolTaskExecutor getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPoolTaskExecutor threadPool) {
        this.threadPool = threadPool;
    }

    public long getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public long getHeartBeatDelay() {
        return heartBeatDelay;
    }

    public void setHeartBeatDelay(long heartBeatDelay) {
        this.heartBeatDelay = heartBeatDelay;
    }

    public ChannelHandlerContext randomNetworkConnection(
            ConcurrentHashMap<String, BigInteger> nodeToBlockNumberMap) throws Exception {
        List<ChannelHandlerContext> activeConnections = new ArrayList<ChannelHandlerContext>();

        for (String key : networkConnections.keySet()) {
            if (networkConnections.get(key) != null
                    && ChannelHandlerContextHelper.isChannelAvailable(
                            networkConnections.get(key))) {
                activeConnections.add(networkConnections.get(key));
            }
        }

        if (activeConnections.isEmpty()) {
            logger.error("activeConnections isEmpty");
            throw new Exception("activeConnections isEmpty");
        }
        // select maxBlockNumber node
        List<ChannelHandlerContext> maxBlockNumberConnections =
                new ArrayList<ChannelHandlerContext>();
        BigInteger maxBlockNumber = new BigInteger("0");
        if (nodeToBlockNumberMap != null) {
            for (String key : nodeToBlockNumberMap.keySet()) {
                BigInteger blockNumber = nodeToBlockNumberMap.get(key);
                if (blockNumber.compareTo(maxBlockNumber) >= 0) {
                    if (blockNumber.compareTo(maxBlockNumber) > 0) {
                        maxBlockNumberConnections.clear();
                    }

                    Optional<ChannelHandlerContext> optionalCtx =
                            activeConnections
                                    .stream()
                                    .filter(
                                            x ->
                                                    key.equals(
                                                            ((SocketChannel) x.channel())
                                                                            .remoteAddress()
                                                                            .getAddress()
                                                                            .getHostAddress()
                                                                    + ((SocketChannel) x.channel())
                                                                            .remoteAddress()
                                                                            .getPort()))
                                    .findFirst();
                    if (optionalCtx.isPresent()) {
                        ChannelHandlerContext channelHandlerContext = optionalCtx.get();
                        maxBlockNumberConnections.add(channelHandlerContext);
                        maxBlockNumber = blockNumber;
                    }
                }
            }
        }
        Random random = new SecureRandom();
        int selectNodeIndex = 0;
        ChannelHandlerContext selectedNodeChannelHandlerContext = null;
        if (!maxBlockNumberConnections.isEmpty()) {
            selectNodeIndex = random.nextInt(maxBlockNumberConnections.size());
            selectedNodeChannelHandlerContext = maxBlockNumberConnections.get(selectNodeIndex);
        } else {
            selectNodeIndex = random.nextInt(activeConnections.size());
            selectedNodeChannelHandlerContext = activeConnections.get(selectNodeIndex);
        }
        return selectedNodeChannelHandlerContext;
    }

    public ConnectionInfo getConnectionInfo(String host, Integer port) {
        for (ConnectionInfo info : connections) {
            if (info.getHost().equals(host) && info.getPort().equals(port)) {
                return info;
            }
        }

        return null;
    }

    public Map<String, ChannelHandlerContext> getNetworkConnections() {
        return networkConnections;
    }

    public ChannelHandlerContext getNetworkConnectionByHost(String host, Integer port) {
        String endpoint = host + ":" + port;

        return networkConnections.get(endpoint);
    }

    public void setNetworkConnectionByHost(String host, Integer port, ChannelHandlerContext ctx) {
        String endpoint = host + ":" + port;

        networkConnections.put(endpoint, ctx);
    }

    public void removeNetworkConnectionByHost(String host, Integer port) {
        String endpoint = host + ":" + port;

        networkConnections.remove(endpoint);
    }

    public void startListen(Integer port) throws SSLException {
        if (running) {
            logger.debug("running");
            return;
        }

        logger.debug("init connections listen");

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        final ChannelConnections selfService = this;
        final ThreadPoolTaskExecutor selfThreadPool = threadPool;

        SslContext sslCtx = initSslContextForListening();
        logger.debug("listening sslcontext init success");
        try {
            serverBootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(
                            new ChannelInitializer<SocketChannel>() {
                                @Override
                                public void initChannel(SocketChannel ch) throws Exception {
                                    /*
                                     * 每次连接使用新的handler
                                     * 连接信息从socketChannel中获取
                                     */
                                    ChannelHandler handler = new ChannelHandler();
                                    handler.setConnections(selfService);
                                    handler.setIsServer(true);
                                    handler.setThreadPool(selfThreadPool);

                                    ch.pipeline()
                                            .addLast(
                                                    sslCtx.newHandler(ch.alloc()),
                                                    new LengthFieldBasedFrameDecoder(
                                                            Integer.MAX_VALUE, 0, 4, -4, 0),
                                                    new IdleStateHandler(
                                                            idleTimeout,
                                                            idleTimeout,
                                                            idleTimeout,
                                                            TimeUnit.MILLISECONDS),
                                                    handler);
                                }
                            });

            ChannelFuture future = serverBootstrap.bind(port);
            future.get();

            running = true;
        } catch (Exception e) {
            logger.error("error ", e);
        }
    }

    public void init() {
        logger.debug("init connections");
        // 初始化connections
        for (String conn : connectionsStr) {
            ConnectionInfo connection = new ConnectionInfo();

            String[] split2 = conn.split(":");

            connection.setHost(split2[0]);
            connection.setPort(Integer.parseInt(split2[1]));

            networkConnections.put(conn, null);

            logger.debug("add direct node :[" + "]:[" + split2[1] + "]");

            connection.setConfig(true);
            connections.add(connection);
        }
    }

    public void startConnect() throws SSLException {
        if (running) {
            logger.debug("running");
            return;
        }

        logger.debug("init connections connect");
        // 初始化netty
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

        final ChannelConnections selfService = this;
        final ThreadPoolTaskExecutor selfThreadPool = threadPool;

        SslContext sslCtx = initSslContextForConnect();
        logger.debug(" connect sslcontext init success");

        bootstrap.handler(
                new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        /*
                         * 每次连接使用新的handler 连接信息从socketChannel中获取
                         */
                        ChannelHandler handler = new ChannelHandler();
                        handler.setConnections(selfService);
                        handler.setIsServer(false);
                        handler.setThreadPool(selfThreadPool);

                        ch.pipeline()
                                .addLast(
                                        sslCtx.newHandler(ch.alloc()),
                                        new LengthFieldBasedFrameDecoder(
                                                Integer.MAX_VALUE, 0, 4, -4, 0),
                                        new IdleStateHandler(
                                                idleTimeout,
                                                idleTimeout,
                                                idleTimeout,
                                                TimeUnit.MILLISECONDS),
                                        handler);
                    }
                });

        running = true;

        Thread loop =
                new Thread() {
                    public void run() {
                        try {
                            while (true) {
                                if (!running) {
                                    return;
                                }

                                // 尝试重连

                                reconnect();
                                Thread.sleep(heartBeatDelay);
                            }
                        } catch (InterruptedException e) {
                            logger.error("error", e);
                            Thread.currentThread().interrupt();
                        }
                    }
                };

        loop.start();
    }

    private SslContext initSslContextForConnect() throws SSLException {
        SslContext sslCtx;
        try {
            Resource caResource = getCaCert();
            InputStream caInputStream = caResource.getInputStream();
            Resource keystorecaResource = getSslCert();
            Resource keystorekeyResource = getSslKey();
            sslCtx =
                    SslContextBuilder.forClient()
                            .trustManager(caInputStream)
                            .keyManager(
                                    keystorecaResource.getInputStream(),
                                    keystorekeyResource.getInputStream())
                            .sslProvider(SslProvider.JDK)
                            .build();
        } catch (Exception e) {
            logger.debug("SSLCONTEXT ***********" + e.getMessage());
            throw new SSLException(
                    "Failed to initialize the client-side SSLContext: " + e.getMessage());
        }
        return sslCtx;
    }

    private SslContext initSslContextForListening() throws SSLException {
        SslContext sslCtx;
        try {
            Resource caResource = getCaCert();
            InputStream caInputStream = caResource.getInputStream();
            Resource keystorecaResource = getSslCert();
            Resource keystorekeyResource = getSslKey();
            sslCtx =
                    SslContextBuilder.forServer(
                                    keystorecaResource.getInputStream(),
                                    keystorekeyResource.getInputStream())
                            .trustManager(caInputStream)
                            .sslProvider(SslProvider.JDK)
                            .build();
        } catch (Exception e) {
            logger.debug("SSLCONTEXT ***********" + e.getMessage());
            throw new SSLException(
                    "Failed to initialize the client-side SSLContext, please checkout ca.crt File!",
                    e);
        }
        return sslCtx;
    }

    public void reconnect() {
        for (Entry<String, ChannelHandlerContext> ctx : networkConnections.entrySet()) {
            if (ctx.getValue() == null || !ctx.getValue().channel().isActive()) {
                String[] split = ctx.getKey().split(":");

                String host = split[0];
                Integer port = Integer.parseInt(split[1]);
                logger.debug("try connect to: {}:{}", host, port);

                bootstrap.connect(host, port);
                logger.debug("connect to: {}:{} success", host, port);
            } else {
                if (ChannelHandlerContextHelper.isChannelAvailable(ctx.getValue())) {
                    logger.trace("send heart beat to {}", ctx.getKey());
                    callback.sendHeartbeat(ctx.getValue());
                } else {
                    SocketChannel socketChannel = (SocketChannel) ctx.getValue().channel();
                    String hostAddress =
                            socketChannel.remoteAddress().getAddress().getHostAddress();
                    int port = socketChannel.remoteAddress().getPort();

                    AttributeKey<String> attributeKey =
                            AttributeKey.valueOf(
                                    EnumSocketChannelAttributeKey.CHANNEL_CONNECTED_KEY.getKey());
                    String connectTimePoint = ctx.getValue().channel().attr(attributeKey).get();

                    String host = hostAddress + ":" + port;
                    logger.debug(
                            " socket channel active, channel protocol handshake not finished, host: {}, connect time: {}",
                            host,
                            connectTimePoint);
                }
            }
        }
    }

    public void onReceiveMessage(ChannelHandlerContext ctx, ByteBuf message) {
        callback.onMessage(ctx, message);
    }
}
