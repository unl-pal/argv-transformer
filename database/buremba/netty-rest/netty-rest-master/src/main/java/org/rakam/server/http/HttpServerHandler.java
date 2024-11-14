package org.rakam.server.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ConcurrentSet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpResponseStatus.REQUEST_ENTITY_TOO_LARGE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpServerHandler
        extends ChannelInboundHandlerAdapter
{
    private static InputStream EMPTY_BODY = new ByteArrayInputStream(new byte[] {});

    private final HttpServer server;
    private final ConcurrentSet activeChannels;
    private final List<PostProcessorEntry> postProcessors;
    protected RakamHttpRequest request;
    private List<ByteBuf> body;

    private static class ReferenceCountedByteBufInputStream
            extends InputStream
    {

        private final ByteBuf buffer;
    }

    public static class ChainByteArrayInputStream
            extends InputStream
    {
        private final List<ByteBuf> arrays;
        private int position;
        private ByteBuf cursor;
        private int cursorPos;

        @Override
        public int read()
        {
            if (cursor.capacity() == position) {
                if (arrays.size() == cursorPos) {
                    return -1;
                }
                cursor = arrays.get(cursorPos++);
                position = 1;
                return cursor.getByte(0);
            }

            return cursor.getByte(position++);
        }
    }
}