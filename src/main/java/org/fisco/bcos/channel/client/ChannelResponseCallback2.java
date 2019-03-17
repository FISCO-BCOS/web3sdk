package org.fisco.bcos.channel.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Timeout;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import org.fisco.bcos.channel.dto.ChannelMessage2;
import org.fisco.bcos.channel.dto.ChannelResponse;
import org.fisco.bcos.channel.handler.ChannelConnections;
import org.fisco.bcos.channel.handler.ConnectionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ChannelResponseCallback2 {
  private static Logger logger = LoggerFactory.getLogger(ChannelResponseCallback2.class);
  private ConnectionInfo fromConnection;
  private List<ConnectionInfo> fromConnectionInfos;
  private ChannelConnections fromChannelConnections;
  private ChannelMessage2 message;
  private Service service;
  private Timeout timeout;

  public abstract void onResponseMessage(ChannelResponse response);

  public final void onResponse(ChannelResponse response) {
    if (response.getErrorCode() == 99) {
      logger.error("Local node error，try the next local nodec");

      retrySendMessage(); // 1表示客户端错误
    } else {
      try {
        onResponseMessage(response);
      } catch (Exception e) {
        logger.error("response package processing error:", e);
      }

      if (message.getSeq() != null) {
        service.getSeq2Callback().remove(message.getSeq());
      }

      if (timeout != null) {
        timeout.cancel();
      }
    }
  }

  public final void onTimeout() {
    logger.error("send message timeout:{}", message.getSeq());

    ChannelResponse response = new ChannelResponse();
    response.setErrorCode(102);
    response.setMessageID(message.getSeq());
    response.setErrorMessage("send message timeout");
    response.setContent("");

    try {
      onResponseMessage(response);
    } catch (Exception e) {
      logger.error("timeout processing error:", e);
    }

    service.getSeq2Callback().remove(message.getSeq());
    timeout.cancel();
  }

  public void retrySendMessage() {
    Integer errorCode = 0;
    try {
      // 选取客户端节点
      logger.debug("Number of local nodes:{}", fromConnectionInfos.size());

      setFromConnection(null);
      if (fromConnectionInfos.size() > 0) {
        Random random = new SecureRandom();
        Integer index = random.nextInt(fromConnectionInfos.size());

        logger.debug("selected:{}", index);

        setFromConnection(fromConnectionInfos.get(index));

        fromConnectionInfos.remove(fromConnectionInfos.get(index));
      }

      if (getFromConnection() == null) {
        // 所有节点已尝试，无法再重试了
        logger.error("Failed to send message,all retry failed");

        errorCode = 99;
        throw new Exception("Failed to send message,all retry failed");
      }

      ChannelHandlerContext ctx =
          fromChannelConnections.getNetworkConnectionByHost(
              getFromConnection().getHost(), getFromConnection().getPort());

      if (ctx != null && ctx.channel().isActive()) {
        ByteBuf out = ctx.alloc().buffer();
        message.writeHeader(out);
        message.writeExtra(out);

        ctx.writeAndFlush(out);

        logger.debug(
            "send message to  "
                + fromConnection.getHost()
                + ":"
                + String.valueOf(fromConnection.getPort())
                + " 成功");
      } else {
        logger.error("sending node unavailable");

        retrySendMessage();
      }
    } catch (Exception e) {
      logger.error("send message exception ", e);

      ChannelResponse response = new ChannelResponse();
      response.setErrorCode(errorCode);
      response.setErrorMessage(e.getMessage());

      try {
        onResponseMessage(response);
      } catch (Exception ee) {
        logger.error("onResponseMessage error:", ee);
      }

      // 彻底失败后，删掉这个seq
      if (message.getSeq() != null) {
        service.getSeq2Callback().remove(message.getSeq());
      }

      if (timeout != null) {
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
}
