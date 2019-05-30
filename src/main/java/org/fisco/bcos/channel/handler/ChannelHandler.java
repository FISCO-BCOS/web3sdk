package org.fisco.bcos.channel.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import java.util.concurrent.RejectedExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {
  private static Logger logger = LoggerFactory.getLogger(ChannelHandler.class);

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    String host = ((SocketChannel) ctx.channel()).remoteAddress().getAddress().getHostAddress();
    Integer port = ((SocketChannel) ctx.channel()).remoteAddress().getPort();

    if (evt instanceof IdleStateEvent) {
      IdleStateEvent e = (IdleStateEvent) evt;
      switch (e.state()) {
        case READER_IDLE:
        case WRITER_IDLE:
        case ALL_IDLE:
          logger.error(
              "event:{} connect{}:{} long time Inactive，disconnect", e.state(), host, port);
          channelInactive(ctx);
          ctx.disconnect();
          ctx.close();
          break;
        default:
          break;
      }
    }
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    try {
      // 已连上，获取ip信息
      String host = ((SocketChannel) ctx.channel()).remoteAddress().getAddress().getHostAddress();
      Integer port = ((SocketChannel) ctx.channel()).remoteAddress().getPort();

      logger.debug(
          "success,connected["
              + host
              + "]:["
              + String.valueOf(port)
              + "],"
              + String.valueOf(ctx.channel().isActive()));

      if (isServer) {
        logger.debug("server accept new connect: {}:{}", host, port);
        // 将此新连接增加到connections
        ConnectionInfo info = new ConnectionInfo();
        info.setHost(host);
        info.setPort(port);

        connections.getConnections().add(info);
        connections.setNetworkConnectionByHost(info.getHost(), info.getPort(), ctx);
        connections.getCallback().onConnect(ctx);
      } else {
        // 更新ctx信息
        ChannelHandlerContext connection = connections.getNetworkConnectionByHost(host, port);
        if (connection != null && connection.channel().isActive()) {
          logger.debug("connect available, close reconnect: {}:{}", host, port);

          ctx.channel().disconnect();
          ctx.channel().close();
        } else {
          logger.debug("client connect success {}:{}", host, port);
          connections.setNetworkConnectionByHost(host, port, ctx);
          connections.getCallback().onConnect(ctx);
        }
      }
    } catch (Exception e) {
      logger.error("error", e);
    }
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    try {
      logger.debug("disconnect");
      // 已断连，获取ip信息
      String host = ((SocketChannel) ctx.channel()).remoteAddress().getAddress().getHostAddress();
      Integer port = ((SocketChannel) ctx.channel()).remoteAddress().getPort();

      logger.debug(
          "disconnect "
              + host
              + ":"
              + String.valueOf(port)
              + " ,"
              + String.valueOf(ctx.channel().isActive()));

      if (isServer) {
        // server模式下，移除该connectionInfo
        for (Integer i = 0; i < connections.getConnections().size(); ++i) {
          ConnectionInfo info = connections.getConnections().get(i);

          if (info.getHost().equals(host) && info.getPort().equals(port)) {
            connections.getConnections().remove(i);
          }
        }

        // 移除该networkConnection
        connections.removeNetworkConnectionByHost(host, port);
      } else {
        // 无需将连接置为null
        // connections.setNetworkConnection(host, port, null);
      }

      connections.getCallback().onDisconnect(ctx);
    } catch (Exception e) {
      logger.error("error ", e);
    }
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    String host = ((SocketChannel) ctx.channel()).remoteAddress().getAddress().getHostAddress();
    Integer port = ((SocketChannel) ctx.channel()).remoteAddress().getPort();

    final ChannelHandlerContext ctxF = ctx;
    final ByteBuf in = (ByteBuf) msg;

    logger.trace("receive，from" + host + ":" + port + " in:" + in.readableBytes());
    logger.trace("threadPool:{}", threadPool == null);

    try {
      if (threadPool == null) {
        connections.onReceiveMessage(ctx, in);
      } else {
        threadPool.execute(
            new Runnable() {
              @Override
              public void run() {
                connections.onReceiveMessage(ctxF, in);
              }
            });
      }
    } catch (RejectedExecutionException e) {
      logger.error("threadPool is full,reject to request", e);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    logger.error("network error ", cause);
    // 已断连，获取ip信息
    String host = ((SocketChannel) ctx.channel()).remoteAddress().getAddress().getHostAddress();
    Integer port = ((SocketChannel) ctx.channel()).remoteAddress().getPort();

    logger.debug(
        "disconnect "
            + host
            + ":"
            + String.valueOf(port)
            + " ,"
            + String.valueOf(ctx.channel().isActive()));

    if (isServer) {
      // server模式下，移除该connection
      connections.removeNetworkConnectionByHost(host, port);
    } else {
      // 将该连接置为不可用
      // connections.setNetworkConnection(host, port, null);
    }

    ctx.disconnect();
    ctx.close();
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
    channelRead(ctx, in);
  }

  public void checkAvailable(ChannelHandlerContext ctx) {}

  public ChannelConnections getConnections() {
    return connections;
  }

  public void setConnections(ChannelConnections connections) {
    this.connections = connections;
  }

  public Boolean getIsServer() {
    return isServer;
  }

  public void setIsServer(Boolean isServer) {
    this.isServer = isServer;
  }

  public ThreadPoolTaskExecutor getThreadPool() {
    return threadPool;
  }

  public void setThreadPool(ThreadPoolTaskExecutor threadPool) {
    this.threadPool = threadPool;

    logger.debug("set threadPool:{}", threadPool == null);
  }

  private ChannelConnections connections;
  private Boolean isServer = false;
  private ThreadPoolTaskExecutor threadPool;
}
