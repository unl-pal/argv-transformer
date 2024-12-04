package org.rakam.server.http;

import com.google.common.collect.ImmutableList;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.rakam.server.http.HttpServer.JsonAPIError;

import java.util.List;
import java.util.Map;

public class HttpRequestException extends RuntimeException {
    private final HttpResponseStatus statusCode;
    private final Map<String, Object> meta;
    private final List<JsonAPIError> errors;
}