package org.fisco.bcos.channel.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.ssl.SMSslClientContextFactory;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.NetUtil;
import io.netty.util.concurrent.Future;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Inet6Address;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLException;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
import org.fisco.bcos.web3j.utils.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ChannelConnections {
    private static Logger logger = LoggerFactory.getLogger(ChannelConnections.class);

    private Callback callback;
    private List<String> connectionsStr;

    /** SSL connection default configuration */
    private static final String CA_CERT = "classpath:ca.crt";

    private static final String SSL_CERT = "classpath:node.crt";
    private static final String SSL_KEY = "classpath:node.key";

    private Resource caCert;
    private Resource sslCert;
    private Resource sslKey;

    /** SM SSL default configuration */
    private static final String GM_CA_CERT = "classpath:gmca.crt";

    private static final String GM_SSL_CERT = "classpath:gmsdk.crt";
    private static final String GM_SSL_KEY = "classpath:gmsdk.key";
    private static final String GM_EN_SSL_CERT = "classpath:gmensdk.crt";
    private static final String GM_EN_SSL_KEY = "classpath:gmensdk.key";

    private Resource gmCaCert;
    private Resource gmSslCert;
    private Resource gmSslKey;
    private Resource gmEnSslCert;
    private Resource gmEnSslKey;

    private List<ConnectionInfo> connections = new ArrayList<ConnectionInfo>();
    private Boolean running = false;
    private ThreadPoolTaskExecutor threadPool;
    private long idleTimeout = (long) 10000;
    private long heartBeatDelay = (long) 2000;
    private long reconnectDelay = (long) 20000;
    private long connectTimeout = (long) 10000;
    private long sslHandShakeTimeout = (long) 10000;
    private boolean enableOpenSSL = true;

    private static final String helpInfo =
            "The reasons for failure may be: \n"
                    + "\t1. the configured certificate is not the same set of certificates as the node's certificate; \n"
                    + "\t2. the configured certificate is not issued by the same authority as the node's certificate. \n"
                    + "\tPlease refer to https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/sdk/java_sdk.html#id24 \n";

    public Map<String, ChannelHandlerContext> networkConnections =
            new ConcurrentHashMap<String, ChannelHandlerContext>();
    private int groupId;
    private Bootstrap bootstrap = new Bootstrap();
    ServerBootstrap serverBootstrap = new ServerBootstrap();

    private ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);

    public boolean isEnableOpenSSL() {
        return enableOpenSSL;
    }

    public void setEnableOpenSSL(boolean enableOpenSSL) {
        this.enableOpenSSL = enableOpenSSL;
    }

    public Resource getCaCert() {
        return caCert;
    }

    public void setCaCert(Resource caCert) {
        this.caCert = caCert;
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

        for (ChannelHandlerContext ctx : networkConnections.values()) {
            if (Objects.nonNull(ctx) && ChannelHandlerContextHelper.isChannelAvailable(ctx)) {
                activeConnections.add(ctx);
            }
        }

        if (activeConnections.isEmpty()) {
            logger.error(" no active connection is available, maybe network connection exception");
            throw new Exception(" no active connection available network exception");
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

    public Map<String, ChannelHandlerContext> getNetworkConnections() {
        return networkConnections;
    }

    public ChannelHandlerContext getNetworkConnectionByHost(String host, Integer port) {
        try {
            host = Inet6Address.getByName(host).getHostAddress();
        } catch (Exception e) {
            logger.error("invalid host string format, value: " + host);
        }
        String endpoint = host + ":" + port;
        return networkConnections.get(endpoint);
    }

    /**
     * description : Associates the specified IP:port with the ChannelHandlerContext object in the
     * map and return the previous value(if not have one ,null will be return)
     *
     * @param host remote ip
     * @param port remote port
     * @param ctx ChannelHandlerContext
     * @return
     */
    public ChannelHandlerContext setAndGetNetworkConnectionByHost(
            String host, Integer port, ChannelHandlerContext ctx) {
        try {
            host = Inet6Address.getByName(host).getHostAddress();
        } catch (Exception e) {
            logger.error("invalid host string format, value: " + host);
        }
        String endpoint = host + ":" + port;
        return networkConnections.put(endpoint, ctx);
    }

    public void removeNetworkConnectionByHost(
            String host, Integer port, ChannelHandlerContext ctx) {
        try {
            host = Inet6Address.getByName(host).getHostAddress();
        } catch (Exception e) {
            logger.error("invalid host string format, value: " + host);
        }
        String endpoint = host + ":" + port;
        Boolean result = networkConnections.remove(endpoint, ctx);
        if (logger.isDebugEnabled()) {
            logger.debug(
                    " result: {}, host: {}, port: {}, ctx: {}",
                    result,
                    host,
                    port,
                    System.identityHashCode(ctx));
        }
    }

    public void init() {
        logger.debug("init connections");

        if (Objects.isNull(connectionsStr) || connectionsStr.isEmpty()) {
            throw new IllegalArgumentException(
                    " Invalid configuration, the number of connected nodes is empty, please check \"connectionsStr\" field.");
        }

        for (String connectionStr : connectionsStr) {
            ConnectionInfo connection = new ConnectionInfo();

            int index = connectionStr.lastIndexOf(':');
            if (index == -1) {
                throw new IllegalArgumentException(
                        " Invalid configuration, the value should in IP:Port format(eg: 127.0.0.1:1111、[::1]:1111), value: "
                                + connectionStr);
            }

            String IP = connectionStr.substring(0, index);
            String port = connectionStr.substring(index + 1);

            if (!(NetUtil.isValidIpV4Address(IP) || NetUtil.isValidIpV6Address(IP))) {
                throw new IllegalArgumentException(
                        " Invalid configuration, invalid IP string format, value: " + IP);
            }

            try {
                IP = Inet6Address.getByName(IP).getHostAddress();
            } catch (Exception e) {
                throw new IllegalArgumentException(
                        " Invalid configuration, invalid IP string format, value: " + IP);
            }

            if (!Host.validPort(port)) {
                throw new IllegalArgumentException(
                        " Invalid configuration, tcp port should from 1 to 65535, value: " + port);
            }

            connection.setHost(IP);
            connection.setPort(Integer.parseInt(port));

            connections.add(connection);
        }

        logger.info(" all connections: {}", connections);

        // initDefaultCertConfig();
    }

    public void startConnect() throws Exception {
        if (running) {
            logger.debug("running");
            return;
        }

        logger.debug(" start connect. ");
        // init netty
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        // set connect timeout
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) connectTimeout);

        final ChannelConnections selfService = this;
        final ThreadPoolTaskExecutor selfThreadPool = threadPool;

        SslContext sslContext =
                (EncryptType.encryptType == EncryptType.ECDSA_TYPE)
                        ? initSslContext()
                        : initSMSslContext();

        SslContext finalSslContext = sslContext;
        bootstrap.handler(
                new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        /*
                         * Each connection is fetched from the socketChannel, using the new
                         * handler connection information
                         */
                        ChannelHandler handler = new ChannelHandler();
                        handler.setConnections(selfService);
                        handler.setThreadPool(selfThreadPool);

                        SslHandler sslHandler = finalSslContext.newHandler(ch.alloc());
                        /** set ssl handshake timeout */
                        sslHandler.setHandshakeTimeoutMillis(sslHandShakeTimeout);

                        ch.pipeline()
                                .addLast(
                                        sslHandler,
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

        List<Tuple3<String, Integer, ChannelFuture>> tuple3List = new ArrayList<>();
        // try to connect to all nodes
        for (ConnectionInfo connectionInfo : connections) {
            String IP = connectionInfo.getHost();
            Integer port = connectionInfo.getPort();

            ChannelFuture channelFuture = bootstrap.connect(IP, port);
            tuple3List.add(new Tuple3<>(IP, port, channelFuture));
        }

        boolean atLeastOneConnectSuccess = false;
        List<String> errorMessageList = new ArrayList<>();
        // Wait for all connection operations to complete
        for (Tuple3<String, Integer, ChannelFuture> tuple3 : tuple3List) {
            ChannelFuture connectFuture = tuple3.getValue3().awaitUninterruptibly();
            if (!connectFuture.isSuccess()) {
                logger.error(
                        " connect to {}:{}, error: {}",
                        tuple3.getValue1(),
                        tuple3.getValue2(),
                        connectFuture.cause().getMessage());

                String connectFailedMessage =
                        Objects.isNull(connectFuture.cause())
                                ? "connect to "
                                        + tuple3.getValue1()
                                        + ":"
                                        + tuple3.getValue2()
                                        + " failed"
                                : connectFuture.cause().getMessage();
                errorMessageList.add(connectFailedMessage);
            } else {
                // tcp connect success and waiting for SSL handshake
                logger.trace(" connect to {}:{} success", tuple3.getValue1(), tuple3.getValue2());

                SslHandler sslhandler = connectFuture.channel().pipeline().get(SslHandler.class);

                if (Objects.isNull(sslhandler)) {
                    String sslHandshakeFailedMessage =
                            " ssl handshake failed:/"
                                    + tuple3.getValue1()
                                    + ":"
                                    + tuple3.getValue2();
                    logger.debug(
                            " SslHandler is null, host: {}, port: {}",
                            tuple3.getValue1(),
                            tuple3.getValue2());
                    errorMessageList.add(sslHandshakeFailedMessage);
                    continue;
                }

                Future<Channel> sshHandshakeFuture =
                        sslhandler.handshakeFuture().awaitUninterruptibly();
                if (sshHandshakeFuture.isSuccess()) {
                    atLeastOneConnectSuccess = true;
                    logger.trace(
                            " ssl handshake success {}:{}", tuple3.getValue1(), tuple3.getValue2());
                } else {

                    String sslHandshakeFailedMessage =
                            " ssl handshake failed:/"
                                    + tuple3.getValue1()
                                    + ":"
                                    + tuple3.getValue2();

                    errorMessageList.add(sslHandshakeFailedMessage);
                }
            }
        }

        // All connections failed
        if (!atLeastOneConnectSuccess) {
            logger.error(" all connections have failed, " + errorMessageList.toString());
            throw new RuntimeException(
                    " Failed to connect to nodes: " + errorMessageList.toString() + helpInfo);
        }

        running = true;
        logger.debug(" start connect end. ");
    }

    public void startPeriodTask() {

        /** periodically send heartbeat message to all connected node, default period : 2s */
        scheduledExecutorService.scheduleAtFixedRate(
                () -> heartbeat(), 0, heartBeatDelay, TimeUnit.MILLISECONDS);

        /** periodically reconnected to a broken node, default period: 20s */
        scheduledExecutorService.scheduleAtFixedRate(
                () -> reconnect(), 0, reconnectDelay, TimeUnit.MILLISECONDS);
    }

    /**
     * init sm ssl context object
     *
     * @return
     * @throws IOException
     */
    public SslContext initSMSslContext()
            throws IOException, InvalidKeySpecException, CertificateException,
                    NoSuchAlgorithmException, NoSuchProviderException {

        // The client's SM SSL certificate exists
        if ((getGmCaCert() != null && getGmCaCert().exists())
                || (getGmEnSslCert() != null && getGmEnSslCert().exists())
                || (getGmEnSslKey() != null && getGmEnSslKey().exists())
                || (getGmSslCert() != null && getGmSslCert().exists())
                || (getGmSslKey() != null && getGmSslKey().exists())) {
            return SMSslClientContextFactory.build(
                    getGmCaCert().getInputStream(),
                    getGmEnSslCert().getInputStream(),
                    getGmEnSslKey().getInputStream(),
                    getGmSslCert().getInputStream(),
                    getGmSslKey().getInputStream());
        }

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource gmCaResource = resolver.getResource(GM_CA_CERT);
        Resource gmSslResource = resolver.getResource(GM_SSL_CERT);
        Resource gmSslKeyResource = resolver.getResource(GM_SSL_KEY);
        Resource gmEnSslResource = resolver.getResource(GM_EN_SSL_CERT);
        Resource gmEnSslKeyResource = resolver.getResource(GM_EN_SSL_KEY);

        // The client's SM SSL default certificate exists
        if (gmCaResource.exists()
                && gmSslResource.exists()
                && gmSslKeyResource.exists()
                && gmEnSslKeyResource.exists()
                && gmEnSslResource.exists()) {
            logger.info(" build SM ssl context by default certificate configuration ");
            return SMSslClientContextFactory.build(
                    gmCaResource.getInputStream(),
                    gmEnSslResource.getInputStream(),
                    gmEnSslKeyResource.getInputStream(),
                    gmSslResource.getInputStream(),
                    gmSslKeyResource.getInputStream());
        }

        logger.info(" there is no SM ssl certificate configuration ");

        // The client's SM SSL certificate not exists or not configured correctly
        return initSslContext();
    }

    private SslContext initSslContext() throws SSLException {
        SslContext sslCtx;
        try {

            if (!isEnableOpenSSL()) {

                String property = System.getProperty("jdk.tls.namedGroups", "");
                if (property == null || "".equals(property)) {
                    System.setProperty("jdk.tls.namedGroups", "secp256k1");
                    logger.info("jdk.tls.namedGroups has not been set, property: {}", property);
                } else if (!property.contains("secp256k1")) {
                    System.setProperty("jdk.tls.namedGroups", property + ",secp256k1");
                    logger.info(
                            "jdk.tls.namedGroups not including secp256k1 has been set, property: {}",
                            property);
                } else {
                    logger.info(
                            "jdk.tls.namedGroups including secp256k1 has been set, property: {}",
                            property);
                }
            }

            PathMatchingResourcePatternResolver resolver =
                    new PathMatchingResourcePatternResolver();

            // check ssl cert file
            Resource caResource = getCaCert();
            Resource keystorecaResource = getSslCert();
            Resource keystorekeyResource = getSslKey();

            // check if ca.crt exist
            if (Objects.isNull(caResource) || !caResource.exists()) {
                Resource resource = resolver.getResource(CA_CERT);
                if (Objects.nonNull(resource) && resource.exists()) {
                    caResource = resource;
                } else {
                    throw new RuntimeException(
                            (Objects.nonNull(caResource) ? "ca.crt" : caResource.getFilename())
                                    + " not exist ");
                }
            }

            // check if sdk.crt exist, if not , check the default value node.crt
            if (Objects.isNull(keystorecaResource) || !keystorecaResource.exists()) {
                Resource resource = resolver.getResource(SSL_CERT);
                if (Objects.nonNull(resource) && resource.exists()) {
                    keystorecaResource = resource;
                } else {
                    throw new RuntimeException(
                            (Objects.nonNull(keystorecaResource)
                                            ? "sdk.crt"
                                            : keystorecaResource.getFilename())
                                    + " not exist ");
                }
            }

            // check if sdk.key exist, if not, check the default value sdk.key
            if (Objects.isNull(keystorekeyResource) || !keystorekeyResource.exists()) {
                Resource resource = resolver.getResource(SSL_KEY);
                if (Objects.nonNull(resource) && resource.exists()) {
                    keystorekeyResource = resource;
                } else {
                    throw new RuntimeException(
                            (Objects.nonNull(keystorekeyResource)
                                            ? "sdk.key"
                                            : keystorekeyResource.getFilename())
                                    + " not exist ");
                }
            }

            logger.info(
                    " ca certificate: {}, sdk certificate: {}, sdk key: {}, enableOpenSsl: {}",
                    caResource.getFilename(),
                    keystorecaResource.getFilename(),
                    keystorekeyResource.getFilename(),
                    isEnableOpenSSL());

            sslCtx =
                    SslContextBuilder.forClient()
                            .trustManager(caResource.getInputStream())
                            .keyManager(
                                    keystorecaResource.getInputStream(),
                                    keystorekeyResource.getInputStream())
                            .sslProvider(isEnableOpenSSL() ? SslProvider.OPENSSL : SslProvider.JDK)
                            .build();
        } catch (Exception e) {
            logger.error(" Failed to initialize the SSLContext, e: {} ", e.getCause());
            throw new SSLException(" Failed to initialize the SSLContext: " + e.getMessage());
        }
        return sslCtx;
    }

    public void heartbeat() {

        List<Tuple2<String, ChannelHandlerContext>> tuple2List = new ArrayList<>();

        for (Map.Entry<String, ChannelHandlerContext> entry : networkConnections.entrySet()) {
            String peer = entry.getKey();
            ChannelHandlerContext ctx = entry.getValue();
            if (Objects.nonNull(ctx)
                    && ctx.channel().isActive()
                    && ChannelHandlerContextHelper.isChannelAvailable(ctx)) {
                tuple2List.add(new Tuple2<>(peer, ctx));
            }
        }

        for (Tuple2<String, ChannelHandlerContext> tuple2 : tuple2List) {
            logger.trace(" send heart beat to {}", tuple2.getValue1());
            callback.sendHeartbeat(tuple2.getValue2());
        }
    }

    public void reconnect() {

        List<ConnectionInfo> connectionInfoList = new ArrayList<>();
        int aliveConnectionCount = 0;
        for (ConnectionInfo connectionInfo : connections) {
            String peer = connectionInfo.getHost() + ":" + connectionInfo.getPort();
            ChannelHandlerContext ctx = networkConnections.get(peer);
            if (Objects.isNull(ctx) || !ctx.channel().isActive()) {
                connectionInfoList.add(connectionInfo);
            } else {
                aliveConnectionCount += 1;
            }
        }

        logger.trace(" Keep alive nodes count: {}", aliveConnectionCount);

        for (ConnectionInfo connectionInfo : connectionInfoList) {

            logger.debug(
                    " try reconnect to {}:{}", connectionInfo.getHost(), connectionInfo.getPort());

            bootstrap
                    .connect(connectionInfo.getHost(), connectionInfo.getPort())
                    .addListener(
                            (ChannelFutureListener)
                                    future -> {
                                        if (future.isSuccess()) {
                                            logger.trace(
                                                    " reconnect to {}:{} success",
                                                    connectionInfo.getHost(),
                                                    connectionInfo.getPort());
                                        } else {
                                            logger.error(
                                                    " reconnect to {}:{}, error: {}",
                                                    connectionInfo.getHost(),
                                                    connectionInfo.getPort(),
                                                    future.cause().getMessage());
                                        }
                                    });
        }
    }

    public void onReceiveMessage(ChannelHandlerContext ctx, ByteBuf message) {
        callback.onMessage(ctx, message);
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public long getSslHandShakeTimeout() {
        return sslHandShakeTimeout;
    }

    public void setSslHandShakeTimeout(long sslHandShakeTimeout) {
        this.sslHandShakeTimeout = sslHandShakeTimeout;
    }

    public Resource getGmCaCert() {
        return gmCaCert;
    }

    public void setGmCaCert(Resource gmCaCert) {
        this.gmCaCert = gmCaCert;
    }

    public Resource getGmSslCert() {
        return gmSslCert;
    }

    public void setGmSslCert(Resource gmSslCert) {
        this.gmSslCert = gmSslCert;
    }

    public Resource getGmSslKey() {
        return gmSslKey;
    }

    public void setGmSslKey(Resource gmSslKey) {
        this.gmSslKey = gmSslKey;
    }

    public Resource getGmEnSslCert() {
        return gmEnSslCert;
    }

    public void setGmEnSslCert(Resource gmEnSslCert) {
        this.gmEnSslCert = gmEnSslCert;
    }

    public Resource getGmEnSslKey() {
        return gmEnSslKey;
    }

    public void setGmEnSslKey(Resource gmEnSslKey) {
        this.gmEnSslKey = gmEnSslKey;
    }
}
