package org.bcos.channel.proxy;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.bcos.channel.dto.ChannelResponse;
import org.bcos.channel.handler.ChannelConnections;
import org.bcos.channel.handler.ConnectionInfo;
import org.bcos.channel.handler.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

class ConnectionPair {
	private static Logger logger = LoggerFactory.getLogger(ConnectionPair.class);
	
	public ChannelHandlerContext localConnection; //SDK连接
	public ChannelHandlerContext remoteConnection; //节点连接
	public Timeout timeout;
	
	private Message message;
	private ConnectionInfo remoteConnectionInfo;
	private List<ConnectionInfo> remoteConnectionInfos;
	private ChannelConnections remoteChannelConnections;
	private Server server;
	
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public ConnectionInfo getRemoteConnectionInfo() {
		return remoteConnectionInfo;
	}

	public void setRemoteConnectionInfo(ConnectionInfo remoteConnectionInfo) {
		this.remoteConnectionInfo = remoteConnectionInfo;
	}

	public List<ConnectionInfo> getRemoteConnectionInfos() {
		return remoteConnectionInfos;
	}

	public void setRemoteConnectionInfos(List<ConnectionInfo> remoteConnectionInfos) {
		this.remoteConnectionInfos = remoteConnectionInfos;
	}

	public ChannelConnections getRemoteChannelConnections() {
		return remoteChannelConnections;
	}

	public void setRemoteChannelConnections(ChannelConnections remoteChannelConnections) {
		this.remoteChannelConnections = remoteChannelConnections;
	}
	
	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}
	
	public void init() {
		final ConnectionPair self = this;
		final String seq = message.getSeq();
		
		timeout = server.getTimeoutHandler().newTimeout(new TimerTask() {
			private ConnectionPair selfServer = self;
			private String selfSeq = seq;
			
			@Override
			public void run(Timeout timeout) throws Exception {
				//处理超时逻辑
				logger.trace("清理过期session:{}", selfSeq);
				
				selfServer.server.getSeq2Connections().remove(selfSeq);
			}
		}, 30000, TimeUnit.MILLISECONDS);
	}

	public void retrySendRemoteMessage() {
		Integer errorCode = 0;
		try {
			// 选取客户端节点
			logger.debug("远端节点数:{}", remoteConnectionInfos.size());

			remoteConnectionInfo = null;
			if (remoteConnectionInfos.size() > 0) {
				Random random = new Random();
				Integer index = random.nextInt(remoteConnectionInfos.size());

				logger.debug("选取:{}", index);

				remoteConnectionInfo = remoteConnectionInfos.get(index);

				remoteConnectionInfos.remove(remoteConnectionInfos.get(index));
			}

			if (remoteConnectionInfo == null) {
				// 所有节点已尝试，无法再重试了
				logger.error("发送消息失败，所有重试均失败");

				errorCode = 99;
				throw new Exception("发送消息失败，所有重试均失败");
			}
			
			ChannelHandlerContext ctx = remoteChannelConnections.getNetworkConnectionByHost(remoteConnectionInfo.getHost(), remoteConnectionInfo.getPort());
			remoteConnection = ctx;
			
			if (ctx != null && ctx.channel().isActive()) {
				ByteBuf out = ctx.alloc().buffer();
				message.writeHeader(out);
				message.writeExtra(out);

				ctx.writeAndFlush(out);

				logger.debug("发送消息至  " + remoteConnectionInfo.getHost() + ":" + String.valueOf(remoteConnectionInfo.getPort()) + " 成功");
			}
			else {
				logger.error("发送节点不可用");
				
				retrySendRemoteMessage();
			}
		} catch (Exception e) {
			logger.error("发送消息异常", e);

			ChannelResponse response = new ChannelResponse();
			response.setErrorCode(errorCode);
			response.setErrorMessage(e.getMessage());

			// 找不到连接，错误
			logger.error("发送连接异常，返回错误99");
			
			if(message.getType() == 0x20 || message.getType() == 0x21) {
				message.setType((short) 0x21);
			}
			else if(message.getType() == 0x30 || message.getType() == 0x31) {
				message.setType((short) 0x31);
			}
			else {
				//ethereum消息，不用改类型
			}
			
			message.setResult(99);

			ByteBuf out = localConnection.alloc().buffer();
			message.writeHeader(out);
			message.writeExtra(out);

			localConnection.writeAndFlush(out);

			//彻底失败后，删掉这个seq
			if(message.getSeq() != null) {
				server.getSeq2Connections().remove(message.getSeq());
			}
			
			if(timeout != null) {
				timeout.cancel();
			}
			
			return;
		}
	}
}