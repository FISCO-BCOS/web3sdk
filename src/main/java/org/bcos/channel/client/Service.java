package org.bcos.channel.client;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.bcos.channel.dto.ChannelMessage;
import org.bcos.channel.dto.ChannelMessage2;
import org.bcos.channel.dto.ChannelPush;
import org.bcos.channel.dto.ChannelPush2;
import org.bcos.channel.dto.ChannelRequest;
import org.bcos.channel.dto.ChannelResponse;
import org.bcos.channel.dto.EthereumMessage;
import org.bcos.channel.dto.EthereumRequest;
import org.bcos.channel.dto.EthereumResponse;
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

public class Service {
	public Integer getConnectSeconds() {
		return connectSeconds;
	}

	public void setConnectSeconds(Integer connectSeconds) {
		this.connectSeconds = connectSeconds;
	}

	public Integer getConnectSleepPerMillis() {
		return connectSleepPerMillis;
	}

	public void setConnectSleepPerMillis(Integer connectSleepPerMillis) {
		this.connectSleepPerMillis = connectSleepPerMillis;
	}

	class ConnectionCallback implements ChannelConnections.Callback {
		public Service getChannelService() {
			return channelService;
		}

		public void setChannelService(Service channelService) {
			this.channelService = channelService;
		}

		@Override
		public void onConnect(ChannelHandlerContext ctx) {
			try {
				Message message = new Message();
				message.setResult(0);
				message.setType((short)0x32); //topic设置topic消息0x32
				message.setSeq(UUID.randomUUID().toString().replaceAll("-", ""));

				logger.debug("已建立连接，将topic发送到该连接:{}", message.getSeq());

				message.setData(objectMapper.writeValueAsBytes(topics.toArray()));

				logger.debug("topics: {}", new String(message.getData()));

				ByteBuf out = ctx.alloc().buffer();
				message.writeHeader(out);
				message.writeExtra(out);

				ctx.writeAndFlush(out);
			} catch (Exception e) {
				logger.error("错误", e);
			}
		}

		@Override
		public void onDisconnect(ChannelHandlerContext ctx) {
		}

		@Override
		public void onMessage(ChannelHandlerContext ctx, ByteBuf message) {
			try {
				Message msg = new Message();
				msg.readHeader(message);

				logger.trace("收到Message type: {}", msg.getType());

				if(msg.getType() == 0x20 || msg.getType() == 0x21) {
					logger.debug("channel消息");

					ChannelMessage channelMessage = new ChannelMessage(msg);
					channelMessage.readExtra(message);

					channelService.onReceiveChannelMessage(ctx, channelMessage);
				}
				else if(msg.getType() == 0x30 || msg.getType() == 0x31) {
					logger.debug("channel2消息");

					ChannelMessage2 channelMessage = new ChannelMessage2(msg);
					channelMessage.readExtra(message);

					channelService.onReceiveChannelMessage2(ctx, channelMessage);
				}
				else if(msg.getType() == 0x12) {
					logger.debug("ethereum消息");

					EthereumMessage ethereumMessage = new EthereumMessage(msg);
					ethereumMessage.readExtra(message);

					channelService.onReceiveEthereumMessage(ctx, ethereumMessage);
				}
                else if(msg.getType() == 0x13) {
                    msg.readExtra(message);

                    String content = "1";
                    try {
                        content = new String(msg.getData(), "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        logger.error("心跳包无法解析");
                    } catch (Exception e) {
                        logger.error("心跳包异常");
                    }

                    if(content.equals("0")) {
                        logger.trace("心跳请求，发送心跳响应");
                        Message response = new Message();

                        response.setSeq(msg.getSeq());
                        response.setResult(0);
                        response.setType((short) 0x13);
                        response.setData("1".getBytes());

                        ByteBuf out = ctx.alloc().buffer();
                        response.writeHeader(out);
                        response.writeExtra(out);

                        ctx.writeAndFlush(out);
                    }
                    else if(content.equals("1")) {
                        logger.trace("心跳响应");
                    }
                }
                else if (msg.getType() == 0x1000) {
                    //交易上链成功回调的消息
                    logger.debug("交易上链成功回调消息");
                    EthereumMessage ethereumMessage = new EthereumMessage(msg);
                    ethereumMessage.readExtra(message);
                    channelService.onReceiveTransactionMessage(ctx, ethereumMessage);
                }
                else {
                    logger.error("未知消息类型:{}", msg.getType());
                }
			}finally {
				message.release();
			}
		}

		private Service channelService;
	}

	private static Logger logger = LoggerFactory.getLogger(Service.class);

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public ChannelPushCallback getPushCallback() {
		return pushCallback;
	}

	public void setPushCallback(ChannelPushCallback pushCallback) {
		this.pushCallback = pushCallback;
	}

	public ConcurrentHashMap<String, ChannelConnections> getAllChannelConnections() {
		return allChannelConnections;
	}

	public void setAllChannelConnections(ConcurrentHashMap<String, ChannelConnections> keyID2connections) {
		this.allChannelConnections = keyID2connections;
	}


	public void run() throws Exception {
		logger.debug("初始化ChannelService");

		try {
			ConnectionCallback connectionCallback = new ConnectionCallback();
			connectionCallback.setChannelService(this);

			for(Map.Entry<String, ChannelConnections> entry: allChannelConnections.entrySet()) {
				entry.getValue().setCallback(connectionCallback);
				entry.getValue().init();
				entry.getValue().setThreadPool(threadPool);

				if(entry.getKey().equals(orgID)) {
					entry.getValue().startConnect();

					int sleepTime = 0;
					boolean running = false;
					while (true) {
						for (String key : entry.getValue().getNetworkConnections().keySet()) {
							if (entry.getValue().getNetworkConnections().get(key) != null
									&& entry.getValue().getNetworkConnections().get(key).channel().isActive()) {
								running = true;
								break;
							}
						}

						if (running || sleepTime > connectSeconds*1000)
						{
							break;
						}
						else
						{
							Thread.sleep(connectSleepPerMillis);
							sleepTime += connectSleepPerMillis;
						}
					}
					if(!running)
					{
						logger.error("connectSeconds = " + connectSeconds);
						logger.error("init ChannelService fail!");
						throw new Exception();
					}
				}
			}
		}
		catch (InterruptedException e) {
			logger.error("system error ", e);
		}
		catch (Exception e) {
			logger.error("system error ", e);
			throw e;
		}

	}

	public EthereumResponse sendEthereumMessage(EthereumRequest request) {
		class Callback extends EthereumResponseCallback  {
			Callback() {
				try {
					semaphore.acquire(1);

				} catch (InterruptedException e) {
					logger.error("错误:", e);
				}
			}

			@Override
			public void onResponse(EthereumResponse response) {
				ethereumResponse = response;

				if(ethereumResponse != null && ethereumResponse.getContent() != null) {
					logger.debug("收到响应: {}", response.getContent());
				}
				else {
					logger.error("ethereum错误");
				}

				semaphore.release();
			}

			public EthereumResponse ethereumResponse;
			public Semaphore semaphore = new Semaphore(1, true);
		};

		Callback callback = new Callback();

		asyncSendEthereumMessage(request, callback);
		try {
			callback.semaphore.acquire(1);
		} catch (InterruptedException e) {
			logger.error("系统错误:", e);
		}

		return callback.ethereumResponse;
	}

	public ChannelResponse sendChannelMessage(ChannelRequest request) {
		class Callback extends ChannelResponseCallback  {
			Callback() {
				try {
					semaphore.acquire(1);

				} catch (InterruptedException e) {
					logger.error("错误:", e);
				}
			}

			@Override
			public void onResponseMessage(ChannelResponse response) {
				channelResponse = response;


				logger.debug("收到响应: {}", response.getContent());

				semaphore.release();
			}

			public ChannelResponse channelResponse;
			public Semaphore semaphore = new Semaphore(1, true);

		};

		Callback callback = new Callback();

		asyncSendChannelMessage(request, callback);
		try {
			callback.semaphore.acquire(1);
		} catch (InterruptedException e) {
			logger.error("系统错误:", e);
		}

		return callback.channelResponse;
	}

	public EthereumResponse sendEthereumMessage(EthereumRequest request, TransactionSucCallback transactionSucCallback) {
        class Callback extends EthereumResponseCallback  {
            Callback() {
                try {
                    semaphore.acquire(1);

                } catch (InterruptedException e) {
                    logger.error("错误:", e);
                }
            }

            @Override
            public void onResponse(EthereumResponse response) {
                ethereumResponse = response;

                logger.info("收到响应: {}", response.getContent());

                semaphore.release();
            }

            public EthereumResponse ethereumResponse;
            public Semaphore semaphore = new Semaphore(1, true);
        }

        Callback callback = new Callback();
        asyncSendEthereumMessage(request, callback, transactionSucCallback);
        try {
            callback.semaphore.acquire(1);
        } catch (InterruptedException e) {
            logger.error("系统错误:", e);
        }

        return callback.ethereumResponse;
    }

    public void asyncSendEthereumMessage(EthereumRequest request, EthereumResponseCallback ethereumResponseCallback, TransactionSucCallback transactionSucCallback) {
        this.asyncSendEthereumMessage(request, ethereumResponseCallback);
        if(request.getTimeout() > 0) {
            final TransactionSucCallback callbackInner = transactionSucCallback;
            callbackInner.setTimeout(timeoutHandler.newTimeout(new TimerTask() {
                @Override
                public void run(Timeout timeout) throws Exception {
                    //处理超时逻辑
                    callbackInner.onTimeout();
                    //timeout时清除map的数据,所以尽管后面有回包数据，也会找不到seq->callback的关系
                    seq2TransactionCallback.remove(request.getMessageID());
                }
            }, request.getTimeout(), TimeUnit.MILLISECONDS));
            this.seq2TransactionCallback.put(request.getMessageID(), callbackInner);
        } else {
            this.seq2TransactionCallback.put(request.getMessageID(), transactionSucCallback);
        }
    }

	public ChannelResponse sendChannelMessage2(ChannelRequest request) {
		class Callback extends ChannelResponseCallback2  {
			Callback() {
				try {
					semaphore.acquire(1);

				} catch (InterruptedException e) {
					logger.error("错误:", e);
				}
			}

			@Override
			public void onResponseMessage(ChannelResponse response) {
				channelResponse = response;

				logger.debug("收到响应: {}", response.getContent());

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
			logger.error("系统错误:", e);
		}

		return callback.channelResponse;
	}

	public void asyncSendEthereumMessage(EthereumRequest request, EthereumResponseCallback callback) {
		logger.debug("处理Ethereum请求: " + request.getMessageID());

		Boolean sended = false;

		EthereumMessage ethereumMessage = new EthereumMessage();

		ethereumMessage.setSeq(request.getMessageID());
		ethereumMessage.setResult(0);
		ethereumMessage.setType((short) 0x12);
		ethereumMessage.setData(request.getContent().getBytes());

		// 选取发送节点
		try {
			ChannelConnections fromChannelConnections = allChannelConnections.get(orgID);
			if (fromChannelConnections == null) {
				// 没有找到对应的链
				// 返回错误
				logger.error("没有找到本机构:{}", orgID);

				throw new Exception("未找到本机构");
			}


			ChannelHandlerContext ctx = fromChannelConnections.randomNetworkConnection();
			ByteBuf out = ctx.alloc().buffer();
			ethereumMessage.writeHeader(out);
			ethereumMessage.writeExtra(out);

			seq2Callback.put(request.getMessageID(), callback);

			if(request.getTimeout() > 0) {
				final EthereumResponseCallback callbackInner = callback; //ethereum名字可能会搞混，换成channel
				callback.setTimeout(timeoutHandler.newTimeout(new TimerTask() {
					EthereumResponseCallback _callback = callbackInner;

					@Override
					public void run(Timeout timeout) throws Exception {
						//处理超时逻辑
						_callback.onTimeout();
					}
				}, request.getTimeout(), TimeUnit.MILLISECONDS));
			}

			ctx.writeAndFlush(out);

			logger.debug("发送Ethereum消息至 " + ((SocketChannel)ctx.channel()).remoteAddress().getAddress().getHostAddress()
					+ ":"
					+ ((SocketChannel)ctx.channel()).remoteAddress().getPort() + " 成功");

			sended = true;
		} catch (Exception e) {
			logger.error("系统错误", e);

			EthereumResponse response = new EthereumResponse();
			response.setErrorCode(-1);
			response.setErrorMessage("系统错误");
			response.setContent("");
			response.setMessageID(request.getMessageID());

			if(callback.getTimeout() != null) {
				callback.getTimeout().cancel();
			}
			callback.onResponse(response);
		}
	}

	public void asyncSendChannelMessage(ChannelRequest request, ChannelResponseCallback callback) {
		try {
			logger.debug("处理链上链下请求: " + request.getMessageID());
			callback.setService(this);

			ChannelMessage channelMessage = new ChannelMessage();

			channelMessage.setSeq(request.getMessageID());
			channelMessage.setResult(0);
			channelMessage.setType((short) 0x20); //链上链下请求0x20
			channelMessage.setData(request.getContent().getBytes());

			try {
				List<ConnectionInfo> fromConnectionInfos = new ArrayList<ConnectionInfo>();
				List<ConnectionInfo> toConnectionInfos = new ArrayList<ConnectionInfo>();

				// 设置发送节点
				ChannelConnections fromChannelConnections = allChannelConnections.get(orgID);
				if (fromChannelConnections == null) {
					// 没有找到对应的链
					// 返回错误
					logger.error("没有找到本机构:{}", request.getFromOrg());

					throw new Exception("未找到本机构");
				}
				fromConnectionInfos.addAll(fromChannelConnections.getConnections());

				logger.debug("发送结构:{} 节点数:{}", request.getFromOrg(), fromChannelConnections.getConnections().size());

				callback.setFromChannelConnections(fromChannelConnections);
				callback.setFromConnectionInfos(fromConnectionInfos);

				//设置目的节点
				ChannelConnections toChannelConnections = allChannelConnections.get(request.getToOrg());
				if (toChannelConnections == null) {
					logger.error("未找到目的机构: {}", request.getToOrg());

					throw new Exception("未找到目标机构");
				}
				toConnectionInfos.addAll(toChannelConnections.getConnections());
				logger.debug("机构:{} 节点数:{}", request.getToOrg(), toChannelConnections.getConnections().size());

				callback.setToConnectionInfos(toConnectionInfos);

				//设置消息内容
				callback.setRequest(channelMessage);

				seq2Callback.put(request.getMessageID(), callback);

				if(request.getTimeout() > 0) {
					final ChannelResponseCallback callbackInner = callback;
					callback.setTimeout(timeoutHandler.newTimeout(new TimerTask() {
						ChannelResponseCallback _callback = callbackInner;

						@Override
						public void run(Timeout timeout) throws Exception {
							//处理超时逻辑
							_callback.onTimeout();
						}
					}, request.getTimeout(), TimeUnit.MILLISECONDS));
				}

				callback.retrySendMessage(0);
			} catch (Exception e) {
				logger.error("发送消息异常 消息未发出", e);

				ChannelResponse response = new ChannelResponse();
				response.setErrorCode(100);
				response.setMessageID(request.getMessageID());
				response.setErrorMessage(e.getMessage());
				response.setContent("");

				callback.onResponse(response);
				return;
			}
		} catch (Exception e) {
			logger.error("系统错误", e);
		}
	}

	public void asyncSendChannelMessage2(ChannelRequest request, ChannelResponseCallback2 callback) {
		try {
			logger.debug("处理链上链下请求: " + request.getMessageID());
			callback.setService(this);

			ChannelMessage2 channelMessage = new ChannelMessage2();

			channelMessage.setSeq(request.getMessageID());
			channelMessage.setResult(0);
			channelMessage.setType((short) 0x30); //链上链下请求0x30
			channelMessage.setData(request.getContent().getBytes());
			channelMessage.setTopic(request.getToTopic());

			try {
				List<ConnectionInfo> fromConnectionInfos = new ArrayList<ConnectionInfo>();

				// 设置发送节点
				ChannelConnections fromChannelConnections = allChannelConnections.get(orgID);
				if (fromChannelConnections == null) {
					// 没有找到对应的链
					// 返回错误
					logger.error("没有找到本机构:{}", orgID);

					throw new Exception("未找到本机构");
				}
				fromConnectionInfos.addAll(fromChannelConnections.getConnections());
				logger.debug("发送机构:{} 节点数:{}", request.getFromOrg(), fromChannelConnections.getConnections().size());

				callback.setFromChannelConnections(fromChannelConnections);
				callback.setFromConnectionInfos(fromConnectionInfos);

				//设置消息内容
				callback.setRequest(channelMessage);

				seq2Callback.put(request.getMessageID(), callback);

				if(request.getTimeout() > 0) {
					final ChannelResponseCallback2 callbackInner = callback;
					callback.setTimeout(timeoutHandler.newTimeout(new TimerTask() {
						ChannelResponseCallback2 _callback = callbackInner;

						@Override
						public void run(Timeout timeout) throws Exception {
							//处理超时逻辑
							_callback.onTimeout();
						}
					}, request.getTimeout(), TimeUnit.MILLISECONDS));
				}

				callback.retrySendMessage();
			} catch (Exception e) {
				logger.error("发送消息异常 消息未发出", e);

				ChannelResponse response = new ChannelResponse();
				response.setErrorCode(100);
				response.setMessageID(request.getMessageID());
				response.setErrorMessage(e.getMessage());
				response.setContent("");

				callback.onResponse(response);
				return;
			}
		} catch (Exception e) {
			logger.error("系统错误", e);
		}
	}

	public void setTopics(List<String> topics) {
		try {
			//设置到自身的topic
			this.topics = topics;

			/*
			Message message = new Message();
			message.setResult(0);
			message.setType((short)0x32); //topic设置topic消息0x32
			message.setSeq(newSeq());
			message.setData(objectMapper.writeValueAsBytes(topics));

			logger.debug("设置topic:{} {}", message.getSeq(), new String(message.getData()));

			//发送到所有前置
			ChannelConnections fromChannelConnections = allChannelConnections.get(orgID);
			if (fromChannelConnections == null) {
				// 没有找到对应的链
				// 返回错误
				logger.error("没有找到本机构:{}", orgID);

				throw new Exception("未找到本机构");
			}

			for(String key: fromChannelConnections.getNetworkConnections().keySet()) {
				ChannelHandlerContext ctx = fromChannelConnections.getNetworkConnections().get(key);

				if (ctx != null && ctx.channel().isActive()) {
					ByteBuf out = ctx.alloc().buffer();
					message.writeHeader(out);
					message.writeExtra(out);

					ctx.writeAndFlush(out);

					String host = ((SocketChannel)ctx.channel()).remoteAddress().getAddress().getHostAddress();
					Integer port = ((SocketChannel)ctx.channel()).remoteAddress().getPort();

					logger.debug("发送topic至  " + host + ":" + String.valueOf(port) + " 成功");
				}
			}
			*/
		}
		catch (Exception e) {
			logger.error("系统错误", e);
		}
	}

	public void sendResponseMessage(ChannelResponse response, ConnectionInfo info, ChannelHandlerContext ctx, String fromNode, String toNode, String seq) {
		try {
			ChannelMessage responseMessage = new ChannelMessage();

			responseMessage.setData(response.getContent().getBytes());
			responseMessage.setResult(response.getErrorCode());
			responseMessage.setSeq(seq);
			responseMessage.setType((short)0x21); //链上链下回包0x21
			responseMessage.setToNode(fromNode);
			responseMessage.setFromNode(toNode);

			ByteBuf out = ctx.alloc().buffer();
			responseMessage.writeHeader(out);
			responseMessage.writeExtra(out);

			ctx.writeAndFlush(out);

			logger.debug("发送回包成功 seq:{} 长度:{}", response.getMessageID(), out.readableBytes());
		} catch (Exception e) {
			logger.error("系统错误", e);
		}
	}

	//链上链下二期回包
	public void sendResponseMessage2(ChannelResponse response, ChannelHandlerContext ctx, String seq, String topic) {
		try {
			ChannelMessage2 responseMessage = new ChannelMessage2();

			responseMessage.setData(response.getContent().getBytes());
			responseMessage.setResult(response.getErrorCode());
			responseMessage.setSeq(seq);
			responseMessage.setType((short)0x31); //链上链下二期回包0x31
			responseMessage.setTopic(topic);

			ByteBuf out = ctx.alloc().buffer();
			responseMessage.writeHeader(out);
			responseMessage.writeExtra(out);

			ctx.writeAndFlush(out);

			logger.debug("发送回包成功 seq:{} 长度:{}", response.getMessageID(), out.readableBytes());
		} catch (Exception e) {
			logger.error("系统错误", e);
		}
	}

	public void onReceiveChannelMessage(ChannelHandlerContext ctx, ChannelMessage message) {
		ChannelResponseCallback callback = (ChannelResponseCallback)seq2Callback.get(message.getSeq());
		logger.debug("收到消息 seq:{}", message.getSeq());

		if(message.getType() == 0x20) { //链上链下请求
			logger.debug("channel请求消息 PUSH");
			if(callback != null) {
				//清空callback再处理
				logger.debug("seq已存在，清除:{}", message.getSeq());
				seq2Callback.remove(message.getSeq());
			}

			try {
				ChannelPush push = new ChannelPush();

				if(pushCallback != null) {
					push.setService(this);
					push.setCtx(ctx);
					push.setMessageID(message.getSeq());
					push.setFromNode(message.getFromNode());
					push.setToNode(message.getToNode());
					push.setSeq(message.getSeq());
					push.setMessageID(message.getSeq());

					push.setContent(new String(message.getData(), 0, message.getData().length));

					pushCallback.onPush(push);
				}
				else {
					logger.error("无法push消息，未设置push callback");
				}
			}
			catch(Exception e) {
				logger.error("处理PUSH消息失败:", e);
			}
		}
		else if(message.getType() == 0x21) { //链上链下回包
			logger.debug("channel回包消息:{}", message.getSeq());
			if(callback != null) {
				logger.debug("已找到callback 回包消息");

				ChannelResponse response = new ChannelResponse();
				if(message.getResult() != 0) {
					response.setErrorCode(message.getResult());
					response.setErrorMessage("回包错误");
				}

				response.setErrorCode(message.getResult());
				response.setMessageID(message.getSeq());
				if(message.getData() != null) {
					response.setContent(new String(message.getData()));
				}

				callback.onResponse(response);
			}
			else {
				logger.error("未找到回包callback，可能已超时:{}", message.getData());
				return;
			}
		}
	}

	public void onReceiveEthereumMessage(ChannelHandlerContext ctx, EthereumMessage message) {
		EthereumResponseCallback callback = (EthereumResponseCallback)seq2Callback.get(message.getSeq());
		logger.debug("收到ethereum消息 seq:{}", message.getSeq());

		if(callback != null) {
			logger.debug("已找到callback ethereum回包消息");

			if(callback.getTimeout() != null) {
				callback.getTimeout().cancel();
			}

			EthereumResponse response = new EthereumResponse();
			if(message.getResult() != 0) {
				response.setErrorMessage("回包错误");
			}

			response.setErrorCode(message.getResult());
			response.setMessageID(message.getSeq());
			response.setContent(new String(message.getData()));

			callback.onResponse(response);

			seq2Callback.remove(message.getSeq());
		}
		else {
			logger.debug("无callback push消息");
		}
	}

	public void onReceiveChannelMessage2(ChannelHandlerContext ctx, ChannelMessage2 message) {
		ChannelResponseCallback2 callback = (ChannelResponseCallback2)seq2Callback.get(message.getSeq());
		logger.debug("收到消息 seq:{}", message.getSeq());

		if(message.getType() == 0x30) { //链上链下请求
			logger.debug("channel请求消息 PUSH");
			if(callback != null) {
				//清空callback再处理
				logger.debug("seq已存在，清除:{}", message.getSeq());
				seq2Callback.remove(message.getSeq());
			}

			try {
				ChannelPush2 push = new ChannelPush2();

				if(pushCallback != null) {
					//pushCallback.setInfo(info);
					push.setSeq(message.getSeq());
					push.setService(this);
					push.setCtx(ctx);
					push.setTopic(message.getTopic());

					push.setSeq(message.getSeq());
					push.setMessageID(message.getSeq());
					push.setContent(new String(message.getData(), 0, message.getData().length));

					pushCallback.onPush(push);
				}
				else {
					logger.error("无法push消息，未设置push callback");
				}
			}
			catch(Exception e) {
				logger.error("处理PUSH消息失败:", e);
			}
		}
		else if(message.getType() == 0x31) { //链上链下回包
			logger.debug("channel回包消息:{}", message.getSeq());
			if(callback != null) {
				logger.debug("已找到callback 回包消息");

				ChannelResponse response = new ChannelResponse();
				if(message.getResult() != 0) {
					response.setErrorCode(message.getResult());
					response.setErrorMessage("回包错误");
				}

				response.setErrorCode(message.getResult());
				response.setMessageID(message.getSeq());
				if(message.getData() != null) {
					response.setContent(new String(message.getData()));
				}

				callback.onResponse(response);
			}
			else {
				logger.error("未找到回包callback，可能已超时:{}", message.getData());
				return;
			}
		}
	}

	public void onReceiveTransactionMessage(ChannelHandlerContext ctx, EthereumMessage message) {
        TransactionSucCallback callback = (TransactionSucCallback)seq2TransactionCallback.get(message.getSeq());
        logger.info("receive transaction success seq:{}", message.getSeq());

        if(callback != null) {
            logger.info("found callback transaction callback");

            if(callback.getTimeout() != null) {
                //停止定时器，防止多响应一次
                callback.getTimeout().cancel();
            }

            EthereumResponse response = new EthereumResponse();
            if(message.getResult() != 0) {
                response.setErrorMessage("回包错误");
            }

            response.setErrorCode(message.getResult());
            response.setMessageID(message.getSeq());
            response.setContent(new String(message.getData()));

            callback.onResponse(response);

            seq2TransactionCallback.remove(message.getSeq());
        }
        else {
            logger.info("callback is null");
        }
    }

	public String newSeq() {
		return UUID.randomUUID().toString().replaceAll("-", "");
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

	private Integer connectSeconds = 3;
	private Integer connectSleepPerMillis = 1;
	private String orgID;
	private ConcurrentHashMap<String, ChannelConnections> allChannelConnections;
	private ChannelPushCallback pushCallback;
	private Map<String, Object> seq2Callback = new ConcurrentHashMap<String, Object>();
	/**
     * add transaction seq callback
     */
    private Map<String, Object> seq2TransactionCallback = new ConcurrentHashMap<String, Object>();
	private Timer timeoutHandler = new HashedWheelTimer();
	private ThreadPoolTaskExecutor threadPool;
	private List<String> topics = new ArrayList<String>();
	private ObjectMapper objectMapper = new ObjectMapper();
}