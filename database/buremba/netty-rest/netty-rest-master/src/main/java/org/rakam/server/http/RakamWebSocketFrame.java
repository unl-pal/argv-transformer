package org.rakam.server.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

public class RakamWebSocketFrame {
    private ChannelHandlerContext ctx;
    private WebSocketFrame frame;
    private WebSocketServerHandshaker handshaker;
}
