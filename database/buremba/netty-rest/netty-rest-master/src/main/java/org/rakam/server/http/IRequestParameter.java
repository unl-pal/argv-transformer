package org.rakam.server.http;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.netty.handler.codec.http.cookie.Cookie;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Function;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;

public interface IRequestParameter<T>
{

    class HeaderParameter<T>
            implements IRequestParameter
    {
        public final String name;
        public final boolean required;
        private final Function<String, T> mapper;
    }

    class CookieParameter
            implements IRequestParameter<String>
    {
        public final String name;
        public final boolean required;
    }

    class QueryParameter<T>
            implements IRequestParameter<T>
    {
        public final String name;
        public final boolean required;
        private final Function<String, T> mapper;
    }

    class BodyParameter
            implements IRequestParameter
    {
        public final String name;
        public final JavaType type;
        public final boolean required;
        private final ObjectMapper mapper;
    }

    class FullBodyParameter
            implements IRequestParameter
    {
        public final JavaType type;
        private final ObjectMapper mapper;
    }
}
