package org.rakam.server.http;

import com.google.common.collect.ImmutableSet;
import io.airlift.log.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.*;
import java.util.function.Consumer;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.http.cookie.ServerCookieDecoder.STRICT;
import static io.netty.util.CharsetUtil.UTF_8;

public class RakamHttpRequest
        implements HttpRequest, Comparable {
    private final static Logger LOGGER = Logger.get(HttpServer.class);
    private final static InputStream REQUEST_DONE_STREAM = new InvalidInputStream();

    private final ChannelHandlerContext ctx;
    private final List<PostProcessorEntry> postProcessors;
    private HttpRequest request;
    private FullHttpResponse response;
    private Map<String, String> responseHeaders;
    private List<Cookie> responseCookies;

    private Consumer<InputStream> bodyHandler;
    private Set<Cookie> cookies;
    private InputStream body;
    private QueryStringDecoder qs;
    private String remoteAddress;

    public class StreamResponse {
        private final ChannelHandlerContext ctx;
        private ChannelFuture lastBufferData = null;
    }

    // if request.end() is called before Netty fetches the body data, handleBody()
    // will be called after request.end() and it needs to release the buffer immediately.
    // this class is used to identify if the request is ended.
    private static class InvalidInputStream
            extends InputStream {
    }
}
