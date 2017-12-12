package org.bcos.channel.client;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.bcos.channel.dto.ChannelMessage2;
import org.bcos.channel.dto.ChannelResponse;
import org.bcos.channel.handler.ChannelConnections;
import org.bcos.channel.handler.ConnectionInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Timeout;

public abstract class ChannelResponseCallback2 {
	private static Logger logger = LoggerFactory.getLogger(ChannelResponseCallback2.class);
	
	public abstract void onResponseMessage(ChannelResponse response);
	
	public final void onResponse(ChannelResponse response) {
		if(response.getErrorCode() == 99) {
			logger.error("本地节点错误，尝试下一个本地节点");
			
			retrySendMessage(); //1表示客户端错误
		}
		else {
			try {
				onResponseMessage(response);
			}
			catch(Exception e) {
				logger.error("回包处理错误:", e);
			}
			
			if(message.getSeq() != null) {
				service.getSeq2Callback().remove(message.getSeq());
			}
			
			if(timeout != null) {
				timeout.cancel();
			}
		}
	}
	
	public final void onTimeout() {
		logger.error("发送消息超时:{}", message.getSeq());

		ChannelResponse response = new ChannelResponse();
		response.setErrorCode(102);
		response.setMessageID(message.getSeq());
		response.setErrorMessage("发送消息超时");
		response.setContent("");

		try {
			onResponseMessage(response);
		}
		catch(Exception e) {
			logger.error("超时处理错误:", e);
		}
		
		service.getSeq2Callback().remove(message.getSeq());
		timeout.cancel();
	}
	
	public void retrySendMessage() {
		Integer errorCode = 0;
		try {
			// 选取客户端节点
			logger.debug("本地节点数:{}", fromConnectionInfos.size());

			setFromConnection(null);
			if (fromConnectionInfos.size() > 0) {
				Random random = new Random();
				Integer index = random.nextInt(fromConnectionInfos.size());

				logger.debug("选取:{}", index);

				setFromConnection(fromConnectionInfos.get(index));

				fromConnectionInfos.remove(fromConnectionInfos.get(index));
			}

			if (getFromConnection() == null) {
				// 所有节点已尝试，无法再重试了
				logger.error("发送消息失败，所有重试均失败");

				errorCode = 99;
				throw new Exception("发送消息失败，所有重试均失败");
			}
			
			ChannelHandlerContext ctx = fromChannelConnections.getNetworkConnectionByHost(getFromConnection().getHost(), getFromConnection().getPort());
			
			if (ctx != null && ctx.channel().isActive()) {
				ByteBuf out = ctx.alloc().buffer();
				message.writeHeader(out);
				message.writeExtra(out);

				ctx.writeAndFlush(out);

				logger.debug("发送消息至  " + fromConnection.getHost() + ":" + String.valueOf(fromConnection.getPort()) + " 成功");
			}
			else {
				logger.error("发送节点不可用");
				
				retrySendMessage();
			}
		} catch (Exception e) {
			logger.error("发送消息异常", e);

			ChannelResponse response = new ChannelResponse();
			response.setErrorCode(errorCode);
			response.setErrorMessage(e.getMessage());

			try {
				onResponseMessage(response);
			}
			catch(Exception ee) {
				logger.error("异常处理错误", ee);
			}
			
			//彻底失败后，删掉这个seq
			if(message.getSeq() != null) {
				service.getSeq2Callback().remove(message.getSeq());
			}
			
			if(timeout != null) {
				timeout.cancel();
			}
			
			return;
		}
	}
	
	public ConnectionInfo getFromConnection() {
		return fromConnection;
	}

	public void setFromConnection(ConnectionInfo fromConnection) {
		this.fromConnection = fromConnection;
	}

	public List<ConnectionInfo> getFromConnectionInfos() {
		return fromConnectionInfos;
	}

	public void setFromConnectionInfos(List<ConnectionInfo> fromConnectionInfos) {
		this.fromConnectionInfos = fromConnectionInfos;
	}

	public ChannelConnections getFromChannelConnections() {
		return fromChannelConnections;
	}

	public void setFromChannelConnections(ChannelConnections fromConnectionConnections) {
		this.fromChannelConnections = fromConnectionConnections;
	}

	public ChannelMessage2 getRequest() {
		return message;
	}

	public void setRequest(ChannelMessage2 message) {
		this.message = message;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}
	
	public Timeout getTimeout() {
		return timeout;
	}

	public void setTimeout(Timeout timeout) {
		this.timeout = timeout;
	}

	private ConnectionInfo fromConnection;
	private List<ConnectionInfo> fromConnectionInfos;
	private ChannelConnections fromChannelConnections;
	private ChannelMessage2 message;
	private Service service;
	private Timeout timeout;
}