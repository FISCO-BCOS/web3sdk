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
                            "event:{} connect{}:{} long time Inactive，disconnect",
                            e.state(),
                            host,
                            port);
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
            // connected，get ip info
            String host =
                    ((SocketChannel) ctx.channel()).remoteAddress().getAddress().getHostAddress();
            Integer port = ((SocketChannel) ctx.channel()).remoteAddress().getPort();

            logger.debug(
                    "success,connected["
                            + host
                            + "]:["
                            + String.valueOf(port)
                            + "],"
                            + String.valueOf(ctx.channel().isActive()));

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

        } catch (Exception e) {
            logger.error("error", e);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        try {
            logger.debug("disconnect");
            // lost the connection, get ip info
            String host =
                    ((SocketChannel) ctx.channel()).remoteAddress().getAddress().getHostAddress();
            Integer port = ((SocketChannel) ctx.channel()).remoteAddress().getPort();

            logger.debug(
                    "disconnect "
                            + host
                            + ":"
                            + String.valueOf(port)
                            + " ,"
                            + String.valueOf(ctx.channel().isActive()));

            // set the connection disabled
            // connections.setNetworkConnection(host, port, null)

            connections.getCallback().onDisconnect(ctx);
        } catch (Exception e) {
            logger.error("error ", e);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        final ChannelHandlerContext ctxF = ctx;
        final ByteBuf in = (ByteBuf) msg;
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
            logger.error("threadPool is full, reject to request", e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("network error ", cause);
        // lost the connection，get ip info
        String host = ((SocketChannel) ctx.channel()).remoteAddress().getAddress().getHostAddress();
        Integer port = ((SocketChannel) ctx.channel()).remoteAddress().getPort();

        logger.debug(
                "disconnect "
                        + host
                        + ":"
                        + String.valueOf(port)
                        + " ,"
                        + String.valueOf(ctx.channel().isActive()));

        // set the connection disabled
        // connections.setNetworkConnection(host, port, null);

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

    public ThreadPoolTaskExecutor getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPoolTaskExecutor threadPool) {
        this.threadPool = threadPool;

        logger.debug("set threadPool:{}", threadPool == null);
    }

    private ChannelConnections connections;
    private ThreadPoolTaskExecutor threadPool;
}
