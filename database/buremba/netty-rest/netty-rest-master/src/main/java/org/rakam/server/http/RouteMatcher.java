package org.rakam.server.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.AttributeKey;

import java.util.*;

public class RouteMatcher
{
    private HashMap<PatternBinding, HttpRequestHandler> routes = new HashMap();
    private HttpRequestHandler noMatch = request -> request.response("404", HttpResponseStatus.NOT_FOUND).end();
    static final AttributeKey<String> PATH = AttributeKey.valueOf("/path");
    private List<Map.Entry<PatternBinding, HttpRequestHandler>> prefixRoutes = new LinkedList<>();

    public static class PatternBinding
    {
        final HttpMethod method;
        final String pattern;
    }

    public static class MicroRouteMatcher
    {
        private final RouteMatcher routeMatcher;
        private String path;
    }
}