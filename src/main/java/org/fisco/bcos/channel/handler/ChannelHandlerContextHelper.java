package org.fisco.bcos.channel.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;
import org.fisco.bcos.channel.protocol.ChannelProtocol;
import org.fisco.bcos.channel.protocol.EnumChannelProtocolVersion;
import org.fisco.bcos.channel.protocol.EnumSocketChannelAttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChannelHandlerContextHelper {

    private static Logger logger = LoggerFactory.getLogger(ChannelHandlerContextHelper.class);

    public static void setProtocolVersion(
            ChannelHandlerContext ctx, EnumChannelProtocolVersion version, String nodeVersion) {
        ChannelProtocol channelProtocol = new ChannelProtocol();
        channelProtocol.setProtocol(version.getVersionNumber());
        channelProtocol.setNodeVersion(nodeVersion);
        channelProtocol.setEnumProtocol(version);
        ctx.channel()
                .attr(
                        AttributeKey.valueOf(
                                EnumSocketChannelAttributeKey.CHANNEL_PROTOCOL_KEY.getKey()))
                .set(channelProtocol);
    }

    public static void setCtxAttibuteValue(ChannelHandlerContext ctx, String key, String value) {

        AttributeKey<String> attributeKey = AttributeKey.valueOf(key);
        ctx.channel().attr(attributeKey).set(value);
    }

    public static EnumChannelProtocolVersion getProtocolVersion(ChannelHandlerContext ctx) {

        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        String hostAddress = socketChannel.remoteAddress().getAddress().getHostAddress();
        int port = socketChannel.remoteAddress().getPort();

        String host = hostAddress + ":" + port;
        AttributeKey<ChannelProtocol> attributeKey =
                AttributeKey.valueOf(EnumSocketChannelAttributeKey.CHANNEL_PROTOCOL_KEY.getKey());

        if (ctx.channel().hasAttr(attributeKey)) {
            ChannelProtocol channelProtocol = ctx.channel().attr(attributeKey).get();

            if (null != channelProtocol) {
                // logger.trace(" host: {}, channel protocol: {}", host, channelProtocol);

                return channelProtocol.getEnumProtocol();
            } else {
                logger.debug(" channel has attr but get null, host: {}", host);
            }
        }

        return null;
    }

    public static String getPeerHost(ChannelHandlerContext ctx) {

        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        String hostAddress = socketChannel.remoteAddress().getAddress().getHostAddress();
        int port = socketChannel.remoteAddress().getPort();

        return hostAddress + ":" + port;
    }

    public static boolean isChannelAvailable(ChannelHandlerContext ctx) {

        // return ctx.channel().isActive();
        return (null != ctx) && ctx.channel().isActive() && (null != getProtocolVersion(ctx));
    }
}
