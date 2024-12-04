package org.rakam.server.http;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.rakam.server.http.util.Lambda;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static java.lang.String.format;
import static org.rakam.server.http.HttpServer.returnError;

public class JsonBeanRequestHandler implements HttpRequestHandler {
    private final ObjectMapper mapper;
    private final JavaType jsonClazz;
    private final boolean isAsync;
    private final HttpService service;
    private final List<RequestPreprocessor> requestPreprocessors;
    private final HttpServer httpServer;
    private final BiFunction function;
}
