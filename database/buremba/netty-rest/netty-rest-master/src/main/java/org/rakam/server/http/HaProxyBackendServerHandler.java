package org.rakam.server.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.haproxy.HAProxyMessage;
import io.netty.util.AttributeKey;
import io.netty.util.internal.ConcurrentSet;

import java.util.List;

public class HaProxyBackendServerHandler extends HttpServerHandler {
    AttributeKey<String> CLIENT_IP = AttributeKey.valueOf("ip");
}
