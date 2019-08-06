package org.fisco.bcos.channel.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;
import org.fisco.bcos.channel.protocol.ChannelProtocol;
import org.fisco.bcos.channel.protocol.EnumChannelProtocolVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChannelHandlerContextHelper {

    private static Logger logger = LoggerFactory.getLogger(ChannelHandlerContextHelper.class);

    public static EnumChannelProtocolVersion getProtocolVersion(ChannelHandlerContext ctx) {

        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        String hostAddress = socketChannel.remoteAddress().getAddress().getHostAddress();
        int port = socketChannel.remoteAddress().getPort();

        String host = hostAddress + ":" + port;
        AttributeKey<ChannelProtocol> attributeKey = AttributeKey.valueOf(host);

        if (ctx.channel().hasAttr(attributeKey)) {
            ChannelProtocol channelProtocol = ctx.channel().attr(attributeKey).get();

            logger.trace(" host: {}, channel protocol: {}", host, channelProtocol);

            return channelProtocol.getEnumProtocol();

        } else { // default channel version
            return null;
        }
    }

    public static boolean isChannelAvailable(ChannelHandlerContext ctx) {

        // return ctx.channel().isActive();

        return (null != ctx) && ctx.channel().isActive() && (null != getProtocolVersion(ctx));
    }
}
