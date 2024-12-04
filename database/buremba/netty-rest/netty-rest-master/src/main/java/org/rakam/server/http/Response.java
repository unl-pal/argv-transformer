package org.rakam.server.http;

import com.google.common.collect.Lists;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;

import java.util.List;

public class Response<T> {
    private final T data;
    private final HttpResponseStatus status;
    private List<Cookie> cookies;
}
