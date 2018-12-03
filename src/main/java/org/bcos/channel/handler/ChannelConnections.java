package org.bcos.channel.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bcos.channel.dto.EthereumMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

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
import io.netty.handler.timeout.IdleStateHandler;

public class ChannelConnections {
	private static Logger logger = LoggerFactory.getLogger(ChannelConnections.class);

	public String getCaCertPath() {
		return caCertPath;
	}

	public void setCaCertPath(String caCertPath) {
		this.caCertPath = caCertPath;
	}
	
	public InputStream getInputStream(String filePath) throws IOException{
	    if(filePath.startsWith("classpath") || filePath.startsWith("file:")){
	        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	        Resource caResource = resolver.getResource(filePath);
	        return caResource.getInputStream();
	    }
	    else{
            File file = new File(filePath); 
            InputStream inputStream = new FileInputStream(file);
            return inputStream;
	    }
	}

	public String getClientKeystorePath() {
		return clientKeystorePath;
	}

	public void setClientKeystorePath(String clientKeystorePath) {
		this.clientKeystorePath = clientKeystorePath;
	}

	public String getKeystorePassWord() {
		return keystorePassWord;
	}

	public void setKeystorePassWord(String keystorePassWord) {
		this.keystorePassWord = keystorePassWord;
	}

	public String getClientCertPassWord() {
		return clientCertPassWord;
	}

	public void setClientCertPassWord(String clientCertPassWord) {
		this.clientCertPassWord = clientCertPassWord;
	}

	public interface Callback {
		void onConnect(ChannelHandlerContext ctx);
		void onDisconnect(ChannelHandlerContext ctx);
		void onMessage(ChannelHandlerContext ctx, ByteBuf message);
	}

	private Callback callback;
	private List<String> connectionsStr;
	private String caCertPath = "classpath:ca.crt";
	private String clientKeystorePath = "classpath:client.keystore";
	private String keystorePassWord = "123456";
	private String clientCertPassWord = "123456";
	private List<ConnectionInfo> connections = new ArrayList<ConnectionInfo>();
	private Boolean running = false;
	private ThreadPoolTaskExecutor threadPool;
	private long idleTimeout = (long)10000;
	private long heartBeatDelay = (long)2000;
	
	public Map<String, ChannelHandlerContext> networkConnections = new HashMap<String, ChannelHandlerContext>();
	
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
	
	private Bootstrap bootstrap = new Bootstrap();
	ServerBootstrap serverBootstrap = new ServerBootstrap();

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

	public ChannelHandlerContext randomNetworkConnection() throws Exception {
		List<ChannelHandlerContext> activeConnections = new ArrayList<ChannelHandlerContext>();
		
		for(String key: networkConnections.keySet()) {
			if(networkConnections.get(key) != null && networkConnections.get(key).channel().isActive()) {
				activeConnections.add(networkConnections.get(key));
			}
		}
		
		if(activeConnections.isEmpty()) {
			logger.error("activeConnections isEmpty");
			throw new Exception("activeConnections isEmpty");
		}
		
		Random random = new Random();
		Integer index = random.nextInt(activeConnections.size());
		
		logger.debug("selected:{}", index);
		
		return activeConnections.get(index);
	}
	
	public ConnectionInfo getConnectionInfo(String host, Integer port) {
		for(ConnectionInfo info: connections) {
			if(info.getHost().equals(host) && info.getPort().equals(port)) {
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
	
	public void startListen(Integer port) {
		if(running) {
			logger.debug("running");
			return;
		}
		
		logger.debug("init connections listen");
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		final ChannelConnections selfService = this;
		final ThreadPoolTaskExecutor selfThreadPool = threadPool;
		
        try (InputStream clientKeystoreInputStream = getInputStream(getClientKeystorePath());
                InputStream caInputStream = getInputStream(getCaCertPath());){
			serverBootstrap.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 100)
            .handler(new LoggingHandler(LogLevel.INFO))
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    KeyStore ks = KeyStore.getInstance("JKS");
					ks.load(clientKeystoreInputStream, getKeystorePassWord().toCharArray());
                	
                	/*
                	 * 每次连接使用新的handler
                	 * 连接信息从socketChannel中获取
                	 */
                	ChannelHandler handler = new ChannelHandler();
                	handler.setConnections(selfService);
                	handler.setIsServer(true);
                	handler.setThreadPool(selfThreadPool);
                	
                	SslContext sslCtx = SslContextBuilder.forServer((PrivateKey)ks.getKey("client", getClientCertPassWord().toCharArray()), (X509Certificate)ks.getCertificate("client"))
                			.trustManager(caInputStream)
                			.build();
                	
                	ch.pipeline().addLast(
                			sslCtx.newHandler(ch.alloc()),
                			new LengthFieldBasedFrameDecoder(1024 * 1024 * 10, 0, 4, -4, 0),
                			new IdleStateHandler(idleTimeout, idleTimeout, idleTimeout, TimeUnit.MILLISECONDS),
                			handler
                	);
                }
            });
			
			ChannelFuture future = serverBootstrap.bind(port);
			future.get();
			
			running = true;
		}
		catch(Exception e) {
			logger.error("error ", e);
		}
	}
	
	public void init() {
		logger.debug("init connections");
		
		Set<String> hostSet = new HashSet<String>();
		
		// 初始化connections
		for (String conn : connectionsStr) {
			ConnectionInfo connection = new ConnectionInfo();
			
			String[] split1 = conn.split("@");
			connection.setNodeID(split1[0]);

			if (split1.length > 1) {
				hostSet.add(split1[1]);
				
				String[] split2 = split1[1].split(":");

				connection.setHost(split2[0]);
				connection.setPort(Integer.parseInt(split2[1]));
				
				networkConnections.put(split1[1], null);

				logger.debug("add direct node :[" + split1[0] + "]:[" + split1[1] + "]");
			} else {
				logger.debug("add undirected node:[" + split1[0] + "]");
			}
			
			connection.setConfig(true);
			connections.add(connection);
		}
	}
	
	public void startConnect() {
		if(running) {
			logger.debug("running");
			return;
		}
		
		logger.debug("init connections connect");
		//初始化netty
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		bootstrap.group(workerGroup);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		
		final ChannelConnections selfService = this;
		final ThreadPoolTaskExecutor selfThreadPool = threadPool;
		
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
            	KeyStore ks = KeyStore.getInstance("JKS");
            	InputStream clientKeystoreInputStream = getInputStream(getClientKeystorePath());
            	ks.load(clientKeystoreInputStream, 	getKeystorePassWord().toCharArray());
            	clientKeystoreInputStream.close();
				/*
				 * 每次连接使用新的handler 连接信息从socketChannel中获取
				 */
				ChannelHandler handler = new ChannelHandler();
				handler.setConnections(selfService);
				handler.setIsServer(false);
				handler.setThreadPool(selfThreadPool);
				
				InputStream caInputStream = getInputStream(getCaCertPath());
				SslContext sslCtx = SslContextBuilder.forClient().trustManager(caInputStream)
						.keyManager((PrivateKey) ks.getKey("client", getClientCertPassWord().toCharArray()),
								(X509Certificate) ks.getCertificate("client"))
						.build();
				caInputStream.close();
				ch.pipeline().addLast(sslCtx.newHandler(ch.alloc()),
						new LengthFieldBasedFrameDecoder(1024 * 1024 * 10, 0, 4, -4, 0),
						new IdleStateHandler(idleTimeout, idleTimeout, idleTimeout, TimeUnit.MILLISECONDS), handler);
            }
        });
		
		running = true;

		Thread loop = new Thread() {
			public void run(){
				try {
					while(true) {
						if(!running) {
							return;
						}
						
						//尝试重连
						
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
	
	public void reconnect() {
		for(Entry<String, ChannelHandlerContext> ctx: networkConnections.entrySet()) {
			if(ctx.getValue() == null || !ctx.getValue().channel().isActive()) {
				String[] split = ctx.getKey().split(":");
				
				String host = split[0];
				Integer port = Integer.parseInt(split[1]);
				logger.debug("try connect to: {}:{}", host, port);

				bootstrap.connect(host, port);
			}
			else {
				logger.trace("send heart beat to {}", ctx.getKey());
				//连接还在，发送心跳
				EthereumMessage ethereumMessage = new EthereumMessage();
				
				ethereumMessage.setSeq(UUID.randomUUID().toString().replaceAll("-", ""));
				ethereumMessage.setResult(0);
				ethereumMessage.setType((short) 0x13);
				ethereumMessage.setData("0".getBytes());
				
				ByteBuf out = ctx.getValue().alloc().buffer();
				ethereumMessage.writeHeader(out);
				ethereumMessage.writeExtra(out);
				
				ctx.getValue().writeAndFlush(out);
			}
		}
	}
	
	public void onReceiveMessage(ChannelHandlerContext ctx, ByteBuf message) {
		callback.onMessage(ctx, message);
	}
}
