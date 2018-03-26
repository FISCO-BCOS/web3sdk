package org.bcos.channel.proxy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.bcos.channel.handler.ChannelConnections;
import org.bcos.channel.handler.ConnectionInfo;
import org.bcos.channel.handler.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

public class Server {
	private static Logger logger = LoggerFactory.getLogger(Server.class);
	
	class ConnectionCallback implements ChannelConnections.Callback {
		public Server getServer() {
			return server;
		}

		public void setServer(Server server) {
			this.server = server;
		}

		public Boolean getFromRemote() {
			return fromRemote;
		}

		public void setFromRemote(Boolean fromRemote) {
			this.fromRemote = fromRemote;
		}

		@Override
		public void onMessage(ChannelHandlerContext ctx, ByteBuf message) {
			try {
				Message msg = new Message();
				msg.readHeader(message);
				msg.readExtra(message);

				logger.debug("收到Message type: {}", msg.getType());

				if(msg.getType() == 0x20 || msg.getType() == 0x21) {
					logger.debug("channel消息");
				}
				else if(msg.getType() == 0x30 || msg.getType() == 0x31) {
					logger.debug("channel2消息");
				}
				else if(msg.getType() == 0x32) {
					logger.debug("topic消息");

					onTopic(ctx, msg);
					return;
				}
				else if(msg.getType() == 0x12) {
					logger.debug("ethereum消息");
				}
				else if(msg.getType() == 0x13) {
					onHeartBeat(ctx, msg);
					return;
				}
				else {
					logger.error("未知消息类型:{}", msg.getType());
				}

				if(fromRemote) {
					logger.debug("远端消息");
					server.onRemoteMessage(ctx, msg);
				}
				else {
					logger.debug("本地消息");
					server.onLocalMessage(ctx, msg);
				}
			}
			finally {
				message.release();
			}
		}
		
		@Override
		public void onConnect(ChannelHandlerContext ctx) {
			//成功连接到新节点，发送topic
			if(fromRemote) {
				try {
					/*
					// 综合所有的topics
					Set<String> allTopics = new HashSet<String>();
					for (ConnectionInfo connectionInfo : localConnections.getConnections()) {
						allTopics.addAll(connectionInfo.getTopics());
					}
	
					Message message = new Message();
					message.setResult(0);
					message.setType((short)0x32); //topic设置topic消息0x32
					message.setSeq(UUID.randomUUID().toString().replaceAll("-", ""));
					
					logger.debug("已建立与节点的新连接，将topic发送到该连接:{}", message.getSeq());
	
					message.setData(objectMapper.writeValueAsBytes(allTopics.toArray()));
	
					logger.debug("topics: {}", new String(message.getData()));
	
					ByteBuf out = ctx.alloc().buffer();
					message.writeHeader(out);
					message.writeExtra(out);
					
					ctx.writeAndFlush(out);
					*/
					
					logger.debug("已建立与远端节点的新连接，将topic发送到该连接");
					
					broadcastTopic(ctx);
				} catch (Exception e) {
					logger.error("错误", e);
				}
			}
		}
		
		@Override
		public void onDisconnect(ChannelHandlerContext ctx) {
			//有sdk断开，需更新topic
			if(!fromRemote) {
				//需要清除该连接的信息
				logger.debug("SDK断连，更新topic信息并重新广播到节点");
				
				broadcastTopic();
			}
		}
		
		private Server server;
		private Boolean fromRemote = false;
	}
	
	public ChannelConnections getLocalConnections() {
		return localConnections;
	}

	public void setLocalConnections(ChannelConnections localConnections) {
		this.localConnections = localConnections;
	}

	public ChannelConnections getRemoteConnections() {
		return remoteConnections;
	}

	public void setRemoteConnections(ChannelConnections connections) {
		this.remoteConnections = connections;
	}
	
	public Map<String, ConnectionPair> getSeq2Connections() {
		return seq2Connections;
	}

	public void setSeq2Connections(Map<String, ConnectionPair> seq2Connections) {
		this.seq2Connections = seq2Connections;
	}

	public Integer getBindPort() {
		return bindPort;
	}

	public void setBindPort(Integer bindPort) {
		this.bindPort = bindPort;
	}

	public Timer getTimeoutHandler() {
		return timeoutHandler;
	}

	public void setTimeoutHandler(Timer timeoutHandler) {
		this.timeoutHandler = timeoutHandler;
	}

	public void run() {
		logger.debug("初始化ProxyServer");
		
		try {
			ConnectionCallback localConnectionCallback = new ConnectionCallback();
			localConnectionCallback.setServer(this);
			localConnectionCallback.setFromRemote(false);
			
			ConnectionCallback remoteConnectionCallback = new ConnectionCallback();
			remoteConnectionCallback.setServer(this);
			remoteConnectionCallback.setFromRemote(true);
			
			localConnections.setCallback(localConnectionCallback);
			localConnections.startListen(bindPort);

			remoteConnections.setCallback(remoteConnectionCallback);
			remoteConnections.init();
			remoteConnections.setThreadPool(threadPool);
			remoteConnections.startConnect();
		} catch (Exception e) {
			logger.error("系统错误", e);
			
			throw e;
		}
	}
	public void broadcastTopic() {
		broadcastTopic(null);
	}
	
	public void broadcastTopic(ChannelHandlerContext ctx) {
		try {
			Message message = new Message();
			message.setResult(0);
			message.setType((short)0x32); //topic设置topic消息0x32
			message.setSeq(UUID.randomUUID().toString().replaceAll("-", ""));
			
			//综合所有的topics
			Set<String> allTopics = new HashSet<String>();
			for(ConnectionInfo connectionInfo: localConnections.getConnections()) {
				//有效的连接，才增加到全局topic
				ChannelHandlerContext localCtx = localConnections.getNetworkConnectionByHost(connectionInfo.getHost(), connectionInfo.getPort());
				
				if(localCtx != null && localCtx.channel().isActive()) {
					logger.debug("节点:{}:{} 关注topics: {}", connectionInfo.getHost(), connectionInfo.getPort(), connectionInfo.getTopics());
					allTopics.addAll(connectionInfo.getTopics());
				}
			}
			
			message.setData(objectMapper.writeValueAsBytes(allTopics.toArray()));
			
			logger.debug("全部topics: {}", new String(message.getData()));
			
			if(ctx == null) {
				//广播到所有远端节点
				for(String key: remoteConnections.getNetworkConnections().keySet()) {
					ChannelHandlerContext remoteCtx = remoteConnections.getNetworkConnections().get(key);
					
					if(remoteCtx != null && remoteCtx.channel().isActive()) {
						ByteBuf out = remoteCtx.alloc().buffer();
						message.writeHeader(out);
						message.writeExtra(out);
						
						if(remoteCtx != null && remoteCtx.channel().isActive()) {
							logger.debug("topic广播至 {}:{}", ((SocketChannel) remoteCtx.channel()).remoteAddress().getAddress().getHostAddress(),
									((SocketChannel) remoteCtx.channel()).remoteAddress().getPort());
							
							remoteCtx.writeAndFlush(out);
						}
					}
				}
			}
			else {
				//发送到指定远端节点
				logger.debug("topic发送至 {}:{}", ((SocketChannel) ctx.channel()).remoteAddress().getAddress().getHostAddress(),
						((SocketChannel) ctx.channel()).remoteAddress().getPort());
				
				ByteBuf out = ctx.alloc().buffer();
				message.writeHeader(out);
				message.writeExtra(out);
				
				ctx.writeAndFlush(out);
			}
		}
		catch(Exception e) {
			logger.error("错误", e);
		}
	}
	
	public void onLocalMessage(ChannelHandlerContext ctx, Message message) {
		try {
			logger.debug("处理来自SDK请求: " + message.getSeq());

			ChannelHandlerContext remoteCtx = null;

			ConnectionPair pair = seq2Connections.get(message.getSeq());
			
			if (pair != null) {
				// 已有这个seq，发往远端的响应
				logger.debug("已有seq");

				// 发送到远端的响应
				remoteCtx = pair.remoteConnection;

				if(message.getType() != 0x31) {
					pair.localConnection = ctx;
				}
				
				ByteBuf out = remoteCtx.alloc().buffer();
				message.writeHeader(out);
				message.writeExtra(out);

				logger.debug("消息发送至:{}:{}", ((SocketChannel) remoteCtx.channel()).remoteAddress().getAddress().getHostAddress(),
						((SocketChannel) remoteCtx.channel()).remoteAddress().getPort());
				remoteCtx.writeAndFlush(out);
			} else {
				pair = new ConnectionPair();
				pair.localConnection = ctx;
				pair.setServer(this);
				pair.setMessage(message);
				
				// 没有这个seq，可能是新发请求或者新收到的push
				// 本地发往远程的消息，如果是链上链下，需要按给定的nodeID发
				if (message.getType() == 0x20 || message.getType() == 0x21) {
					// 获取nodeID
					logger.debug("链上链下消息一期 指定目的地");
					if (message.getData().length < 256) {
						logger.error("错误的channel消息 长度不足256:{}", message.getData().length);
					}

					// 获取nodeID对应的连接，检查可用性
					String nodeID = new String(message.getData(), 128, 128);

					logger.debug("转发至:{}", nodeID, message.getData());
					for (ConnectionInfo conn : remoteConnections.getConnections()) {
						if (conn.getNodeID().equals(nodeID)) {
							remoteCtx = remoteConnections.getNetworkConnectionByHost(conn.getHost(), conn.getPort());
							pair.remoteConnection = remoteCtx;

							break;
						}
					}
					
					if (remoteCtx == null || !remoteCtx.channel().isActive()) {
						// 找不到连接，错误
						logger.error("发送连接异常，返回错误99");
						
						if(message.getType() == 0x20 || message.getType() == 0x21) {
							message.setType((short) 0x21);
						}
						else {
							message.setType((short) 0x31);
						}
						
						message.setResult(99);

						ByteBuf out = ctx.alloc().buffer();
						message.writeHeader(out);
						message.writeExtra(out);

						ctx.writeAndFlush(out);

						return;
					}

					ByteBuf out = remoteCtx.alloc().buffer();
					message.writeHeader(out);
					message.writeExtra(out);

					logger.debug("消息发送至:{}:{}", ((SocketChannel) remoteCtx.channel()).remoteAddress().getAddress().getHostAddress(),
							((SocketChannel) remoteCtx.channel()).remoteAddress().getPort());
					remoteCtx.writeAndFlush(out);
					pair.init();
				} else {
					logger.debug("其它消息，使用ConnectionPair逻辑");
					
					pair.setRemoteChannelConnections(remoteConnections);
					
					List<ConnectionInfo> remoteConnectionInfos = new ArrayList<ConnectionInfo>();
					remoteConnectionInfos.addAll(remoteConnections.getConnections());
					pair.setRemoteConnectionInfos(remoteConnectionInfos);
					
					seq2Connections.put(message.getSeq(), pair);
					
					pair.init();
					pair.retrySendRemoteMessage();
				}
			}
		} catch (Exception e) {
			logger.error("系统错误", e);
		}
	}
	
	public void onRemoteMessage(ChannelHandlerContext ctx, Message message) {
		try {
			logger.debug("处理请求: " + message.getSeq());

			ChannelHandlerContext localCtx = null;

			ConnectionPair pair = seq2Connections.get(message.getSeq());
			
			if(message.getType() == 0x30) {
				//链上链下二期，需要找到关注该topic的连接
				Short length = (short) message.getData()[0];
				String topic = new String(message.getData(), 1, length - 1);
				
				Set<ChannelHandlerContext> topicCtxs = new HashSet<ChannelHandlerContext>();
				for (ConnectionInfo connectionInfo : localConnections.getConnections()) {
					if(connectionInfo.getTopics().contains(topic)) {
						ChannelHandlerContext topicCtx = localConnections.getNetworkConnectionByHost(connectionInfo.getHost(), connectionInfo.getPort());
						
						if(topicCtx != null && topicCtx.channel().isActive()) {
							topicCtxs.add(topicCtx);
						}
					}
				}
				
				logger.debug("下发topic:{} 共{}个连接关注该topic", topic, topicCtxs.size());
				
				if(topicCtxs.isEmpty()) {
					// 找不到连接，错误
					logger.error("找不到可下发的连接，返回错误99");

					message.setType((short) 0x31);
					message.setResult(99);

					ByteBuf out = ctx.alloc().buffer();
					message.writeHeader(out);
					message.writeExtra(out);

					ctx.writeAndFlush(out);

					return;
				}
				
				//随机下发
				Random random = new Random();
				Integer index = random.nextInt(topicCtxs.size());
				ChannelHandlerContext target = (ChannelHandlerContext) topicCtxs.toArray()[index];
				
				logger.debug("下发给 {}:{}", ((SocketChannel) target.channel()).remoteAddress().getAddress().getHostAddress(),
						((SocketChannel) target.channel()).remoteAddress().getPort());
				
				localCtx = target;
				
				if(pair == null) {
					pair = new ConnectionPair();
					pair.localConnection = localCtx;
					pair.remoteConnection = ctx;
					pair.setServer(this);
					pair.setMessage(message);
					
					seq2Connections.put(message.getSeq(), pair);
					pair.init();
				}
				else {
					pair.remoteConnection = ctx;
				}
			}
			else {
				if (pair != null) {
					// 已有这个seq，可能是发送响应或者收到回包消息
					logger.debug("已有seq");
	
					// 收到来自远端的回包
					localCtx = pair.localConnection;
	
					if(message.getResult() != 0 && message.getType() == 0x31) {
						//链上链下二期错误时，执行retry
						logger.error("对端返回错误:{}，重试", message.getResult());
						
						pair.retrySendRemoteMessage();
						return;
					}
					
					pair.remoteConnection = ctx;
				} else {
					// 没有这个seq，可能是新发请求或者新收到的push
					
					//其他消息（链上链下一期），随机发
					localCtx = localConnections.randomNetworkConnection();
				}
			}
			
			if (localCtx == null || !localCtx.channel().isActive()) {
				// 找不到连接，错误
				logger.error("发送连接异常，该连接不可用，返回错误99");
				
				if(message.getType() == 0x20 || message.getType() == 0x21) {
					message.setType((short) 0x21);
				}
				else {
					message.setType((short) 0x31);
				}

				message.setResult(99);

				ByteBuf out = ctx.alloc().buffer();
				message.writeHeader(out);
				message.writeExtra(out);

				ctx.writeAndFlush(out);

				return;
			}

			ByteBuf out = localCtx.alloc().buffer();
			message.writeHeader(out);
			message.writeExtra(out);

			logger.debug("消息发送至:{}:{}", ((SocketChannel) localCtx.channel()).remoteAddress().getAddress().getHostAddress(),
					((SocketChannel) localCtx.channel()).remoteAddress().getPort());
			localCtx.writeAndFlush(out);
		} catch (Exception e) {
			logger.error("系统错误", e);
		}
	}
	
	public void onHeartBeat(ChannelHandlerContext ctx, Message message) {
		String content = "1";
		try {
			content = new String(message.getData(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("心跳包无法解析");
		} catch (Exception e) {
			logger.error("心跳包异常");
		}
		
		if(content.equals("0")) {
			Message response = new Message();
			
			response.setSeq(message.getSeq());
			response.setResult(0);
			response.setType((short) 0x13);
			response.setData("1".getBytes());
			
			ByteBuf out = ctx.alloc().buffer();
			response.writeHeader(out);
			response.writeExtra(out);
			
			ctx.writeAndFlush(out);
		}
		else if(content.equals("1")) {
		}
	}
	
	public void onTopic(ChannelHandlerContext ctx, Message message) {
		logger.debug("来自SDK的topics消息: {} {}", message.getSeq(), new String(message.getData()));
		String host = ((SocketChannel)ctx.channel()).remoteAddress().getAddress().getHostAddress();
		Integer port = ((SocketChannel)ctx.channel()).remoteAddress().getPort();//
		ConnectionInfo info = localConnections.getConnectionInfo(host, port);
		
		if(info != null) {
			try {
				List<String> topics = objectMapper.readValue(message.getData(), List.class);
				
				info.setTopics(topics);
				
				broadcastTopic();
			} catch (Exception e) {
				logger.error("解析topic错误", e);
			}
		}
	}
	
	public ThreadPoolTaskExecutor getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ThreadPoolTaskExecutor threadPool) {
		this.threadPool = threadPool;
	}

	//private String orgID;
	private ChannelConnections localConnections = new ChannelConnections();
	private ChannelConnections remoteConnections;
	private Map<String, ConnectionPair> seq2Connections = new ConcurrentHashMap<String, ConnectionPair>();
	private Integer bindPort = 8830;
	private ObjectMapper objectMapper = new ObjectMapper();
	private Timer timeoutHandler = new HashedWheelTimer();
	
	private ThreadPoolTaskExecutor threadPool;
}
