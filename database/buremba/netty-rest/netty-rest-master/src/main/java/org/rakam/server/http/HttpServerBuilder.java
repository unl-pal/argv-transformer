package org.rakam.server.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.util.PrimitiveType;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class HttpServerBuilder
{
    private Set<HttpService> httpServices;
    private Set<WebSocketService> websocketServices;
    private Swagger swagger;
    private EventLoopGroup eventLoopGroup;
    private ObjectMapper mapper;
    private Map<Class, PrimitiveType> overridenMappings;
    private Builder<PreprocessorEntry> jsonRequestPreprocessors = ImmutableList.builder();
    private Builder<PostProcessorEntry> postProcessorEntryBuilder = ImmutableList.builder();
    private boolean proxyProtocol;
    private ExceptionHandler exceptionHandler;
    private Map<String, IRequestParameterFactory> customRequestParameters;
    private BiConsumer<Method, Operation> swaggerOperationConsumer;
    private boolean useEpoll = true;
    private long maximumBodySize = -1;
    private boolean enableDebugMode;

    public interface ExceptionHandler
    {
    }

    public interface IRequestParameterFactory
    {
    }
}
