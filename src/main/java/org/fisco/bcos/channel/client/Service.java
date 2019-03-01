package org.fisco.bcos.channel.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.fisco.bcos.channel.dto.*;
import org.fisco.bcos.channel.handler.ChannelConnections;
import org.fisco.bcos.channel.handler.ConnectionCallback;
import org.fisco.bcos.channel.handler.ConnectionInfo;
import org.fisco.bcos.channel.handler.GroupChannelConnectionsConfig;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class Service {
  private static Logger logger = LoggerFactory.getLogger(Service.class);
  private Integer connectSeconds = 30;
  private Integer connectSleepPerMillis = 1;
  private String orgID;
  private GroupChannelConnectionsConfig allChannelConnections;
  private ChannelPushCallback pushCallback;
  private Map<String, Object> seq2Callback = new ConcurrentHashMap<String, Object>();
  private static int groupId;
  private static ObjectMapper objectMapper = new ObjectMapper();
  private BigInteger number = BigInteger.valueOf(0);

  /** add transaction seq callback */
  private Map<String, Object> seq2TransactionCallback = new ConcurrentHashMap<String, Object>();

  private Timer timeoutHandler = new HashedWheelTimer();
  private ThreadPoolTaskExecutor threadPool;

  private Set<String> topics = new HashSet<String>();

  public void setTopics(Set<String> topics) {
    try {
      this.topics = topics;
    } catch (Exception e) {
      logger.error("system error", e);
    }
  }

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

  public GroupChannelConnectionsConfig getAllChannelConnections() {
    return allChannelConnections;
  }

  public void setAllChannelConnections(GroupChannelConnectionsConfig allChannelConnections) {
    this.allChannelConnections = allChannelConnections;
  }

  public void run() throws Exception {
    logger.debug("init ChannelService");
    int flag = 0;
    for (ChannelConnections channelConnections : allChannelConnections.getAllChannelConnections()) {

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

            if (running || sleepTime > connectSeconds * 1000) {
              break;
            } else {
              Thread.sleep(connectSleepPerMillis);
              sleepTime += connectSleepPerMillis;
            }
          }

          if (!running) {
            logger.error("connectSeconds = " + connectSeconds);
            logger.error(
                "cannot not connect to node success, please checkout the node and the sdk config!");
            throw new Exception(
                "Init ChannelService fail!Please Refer To Link Below:https://github.com/FISCO-BCOS/web3sdk/wiki/web3sdk-debug");
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

  public FiscoResponse sendEthereumMessage(FiscoRequest request) {
    class Callback extends FiscoResponseCallback {
      Callback() {
        try {
          semaphore.acquire(1);

        } catch (InterruptedException e) {
          logger.error("error :", e);
          Thread.currentThread().interrupt();
        }
      }

      @Override
      public void onResponse(FiscoResponse response) {
        fiscoResponse = response;

        if (fiscoResponse != null && fiscoResponse.getContent() != null) {
          logger.debug("response: {}", response.getContent());
        } else {
          logger.error("fisco error");
        }

        semaphore.release();
      }

      public FiscoResponse fiscoResponse;
      public Semaphore semaphore = new Semaphore(1, true);
    };

    Callback callback = new Callback();

    asyncSendEthereumMessage(request, callback);
    try {
      callback.semaphore.acquire(1);
    } catch (InterruptedException e) {
      logger.error("system error:", e);
      Thread.currentThread().interrupt();
    }

    return callback.fiscoResponse;
  }

  public FiscoResponse sendEthereumMessage(
          FiscoRequest request, TransactionSucCallback transactionSucCallback) {
    class Callback extends FiscoResponseCallback {
      public FiscoResponse ethereumResponse;
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
      public void onResponse(FiscoResponse response) {
        ethereumResponse = response;

        logger.info("response: {}", response.getContent());

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
      FiscoRequest request,
      FiscoResponseCallback fiscoResponseCallback,
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

  public void asyncSendEthereumMessage(FiscoRequest request, FiscoResponseCallback callback) {
    logger.debug("fisco message: " + request.getMessageID());

    Boolean sended = false;

    FiscoMessage fiscoMessage = new FiscoMessage();

    fiscoMessage.setSeq(request.getMessageID());
    fiscoMessage.setResult(0);
    fiscoMessage.setType((short) 0x12);
    fiscoMessage.setData(request.getContent().getBytes());

    // 选取发送节点
    try {

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
        logger.error("not found:{}", orgID);

        throw new Exception("not found orgID");
      }

      ChannelHandlerContext ctx = fromChannelConnections.randomNetworkConnection();

      ByteBuf out = ctx.alloc().buffer();
      fiscoMessage.writeHeader(out);
      fiscoMessage.writeExtra(out);

      seq2Callback.put(request.getMessageID(), callback);

      if (request.getTimeout() > 0) {
        final FiscoResponseCallback callbackInner = callback; // ethereum名字可能会搞混，换成channel
        callback.setTimeout(
            timeoutHandler.newTimeout(
                new TimerTask() {
                  FiscoResponseCallback _callback = callbackInner;

                  @Override
                  public void run(Timeout timeout) throws Exception {
                    // 处理超时逻辑
                    _callback.onTimeout();
                  }
                },
                request.getTimeout(),
                TimeUnit.MILLISECONDS));
      }

      ctx.writeAndFlush(out);

      logger.debug(
          "send fisco message to "
              + ((SocketChannel) ctx.channel()).remoteAddress().getAddress().getHostAddress()
              + ":"
              + ((SocketChannel) ctx.channel()).remoteAddress().getPort()
              + " success");

      sended = true;
    } catch (Exception e) {
      logger.error("system error: " + e.getMessage());

      FiscoResponse response = new FiscoResponse();
      response.setErrorCode(-1);
      response.setErrorMessage(
          e.getMessage()
              + "   requset send failed! Please Refer To Link Below:https://github.com/FISCO-BCOS/web3sdk/wiki/web3sdk-debug");
      response.setContent("");
      response.setMessageID(request.getMessageID());

      if (callback.getTimeout() != null) {
        callback.getTimeout().cancel();
      }
      callback.onResponse(response);
    }
  }

  public void asyncSendChannelMessage2(ChannelRequest request, ChannelResponseCallback2 callback) {
    try {
      logger.debug("ChannelRequest: " + request.getMessageID());
      callback.setService(this);

      ChannelMessage2 channelMessage = new ChannelMessage2();

      channelMessage.setSeq(request.getMessageID());
      channelMessage.setResult(0);
      channelMessage.setType((short) 0x30); // 链上链下请求0x30
      channelMessage.setData(request.getContent().getBytes());
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
          logger.error("not found orgID:{}", orgID);

          throw new Exception("not found orgID");
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
    ChannelResponseCallback callback = (ChannelResponseCallback) seq2Callback.get(message.getSeq());
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

  public void onReceiveEthereumMessage(ChannelHandlerContext ctx, FiscoMessage message) {
    FiscoResponseCallback callback =
        (FiscoResponseCallback) seq2Callback.get(message.getSeq());
    logger.debug("FiscoResponse seq:{}", message.getSeq());

    if (callback != null) {
      logger.debug("found callback FiscoResponse");

      if (callback.getTimeout() != null) {
        callback.getTimeout().cancel();
      }

      FiscoResponse response = new FiscoResponse();
      if (message.getResult() != 0) {
        response.setErrorMessage("FiscoResponse error");
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

    if (message.getType() == 0x30) { // 链上链下请求
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
          push.setContent(new String(message.getData(), 0, message.getData().length));

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
      String data = new String(message.getData());
      logger.debug("Receive block notify: {}", data);
      String[] split = data.split(",");
      if (split.length != 2) {
        logger.error("Block notify format error: {}", data);
        return;
      }

      Integer groupID = Integer.parseInt(split[0]);

      if (!groupID.equals(getGroupId())) {
        logger.error("Received groupID[{}] not match groupID[{}]", groupID, getGroupId());

        return;
      }

      Integer number = Integer.parseInt(split[1]);

      if (number.compareTo(getNumber().intValue()) > 0) {
        setNumber(BigInteger.valueOf((long) number));
      }
    } catch (Exception e) {
      logger.error("Block notify error", e);
    }
  }

  public void onReceiveTransactionMessage(ChannelHandlerContext ctx, FiscoMessage message) {
    TransactionSucCallback callback =
        (TransactionSucCallback) seq2TransactionCallback.get(message.getSeq());
    logger.info("receive transaction success seq:{}", message.getSeq());

    if (callback != null) {
      logger.info("found callback transaction callback");

      if (callback.getTimeout() != null) {
        // 停止定时器，防止多响应一次
        callback.getTimeout().cancel();
      }

      try {
        TransactionReceipt receipt =
            objectMapper.readValue(message.getData(), TransactionReceipt.class);

        callback.onResponse(receipt);
      } catch (Exception e) {
        TransactionReceipt receipt = new TransactionReceipt();
        receipt.setStatus("Decode receipt error: " + e.getLocalizedMessage());

        callback.onResponse(receipt);
      }

      seq2TransactionCallback.remove(message.getSeq());
    } else {
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
