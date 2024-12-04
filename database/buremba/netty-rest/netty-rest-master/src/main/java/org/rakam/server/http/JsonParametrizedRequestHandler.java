package org.rakam.server.http;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static java.lang.String.format;
import static org.rakam.server.http.HttpServer.*;

public class JsonParametrizedRequestHandler implements HttpRequestHandler {
    private final ObjectMapper mapper;
    private final List<IRequestParameter> bodyParams;
    private final MethodHandle methodHandle;
    private final HttpService service;
    private final boolean isAsync;
    private final List<RequestPreprocessor> jsonPreprocessors;
    private final boolean bodyExtractionRequired;

    static final String bodyError;
    static final ObjectNode emptyObject;

    static {
        try {
            bodyError = DEFAULT_MAPPER.writeValueAsString(new ErrorMessage(ImmutableList.of(JsonAPIError.title("Body must be an JSON object.")), null));
        } catch (JsonProcessingException e) {
            throw Throwables.propagate(e);
        }
        emptyObject = DEFAULT_MAPPER.createObjectNode();
    }

    private final HttpServer httpServer;
    private final boolean isJson;
}
