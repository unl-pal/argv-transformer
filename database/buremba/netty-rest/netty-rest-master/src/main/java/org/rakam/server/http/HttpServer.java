package org.rakam.server.http;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import io.airlift.log.Logger;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.haproxy.HAProxyMessageDecoder;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.ConcurrentSet;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.util.Json;
import io.swagger.util.PrimitiveType;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.jvm.ReflectJvmMapping;
import org.rakam.server.http.HttpServerBuilder.IRequestParameterFactory;
import org.rakam.server.http.IRequestParameter.BodyParameter;
import org.rakam.server.http.IRequestParameter.HeaderParameter;
import org.rakam.server.http.annotations.ApiParam;
import org.rakam.server.http.annotations.BodyParam;
import org.rakam.server.http.annotations.CookieParam;
import org.rakam.server.http.annotations.HeaderParam;
import org.rakam.server.http.annotations.JsonRequest;
import org.rakam.server.http.annotations.QueryParam;
import org.rakam.server.http.util.Lambda;

import javax.inject.Named;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.ws.rs.Path;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.invoke.MethodHandle;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaders.Names.SET_COOKIE;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpMethod.POST;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.http.cookie.ServerCookieEncoder.STRICT;
import static io.netty.util.CharsetUtil.UTF_8;
import static java.lang.String.format;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Objects.requireNonNull;
import static org.rakam.server.http.SwaggerReader.getActualType;
import static org.rakam.server.http.util.Lambda.produceLambdaForFunction;

public class HttpServer
{
    private final static Logger LOGGER = Logger.get(HttpServer.class);
    static final ObjectMapper DEFAULT_MAPPER;
    private static BytecodeReadingParanamer PARAMETER_LOOKUP = new BytecodeReadingParanamer();

    public final RouteMatcher routeMatcher;
    private final Swagger swagger;

    private final ObjectMapper mapper;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private final List<PreprocessorEntry> preProcessors;
    private final boolean proxyProtocol;
    private final List<PostProcessorEntry> postProcessors;
    final HttpServerBuilder.ExceptionHandler uncaughtExceptionHandler;
    private final Map<String, IRequestParameterFactory> customParameters;
    private final BiConsumer<Method, Operation> swaggerOperationConsumer;
    private final boolean useEpoll;
    protected final Map<RakamHttpRequest, Long> processingRequests;
    protected final long maximumBodySize;
    private final boolean debugMode;
    private Channel channel;

    private final ImmutableMap<Class, PrimitiveType> swaggerBeanMappings = ImmutableMap.<Class, PrimitiveType>builder()
            .put(LocalDate.class, PrimitiveType.DATE)
            .put(Duration.class, PrimitiveType.STRING)
            .put(Instant.class, PrimitiveType.DATE_TIME)
            .put(ObjectNode.class, PrimitiveType.OBJECT)
            .build();
    protected ConcurrentSet activeChannels;

    static {
        DEFAULT_MAPPER = new ObjectMapper();
    }

    private static final ImmutableMap<Class<?>, Function<String, ?>> PRIMITIVE_MAPPER = ImmutableMap.<Class<?>, Function<String, ?>>builder()
            .put(int.class, Integer::parseInt)
            .put(long.class, Long::parseLong)
            .put(double.class, Double::parseDouble)
            .put(boolean.class, Boolean::parseBoolean)
            .put(float.class, Float::parseFloat)
            .put(Integer.class, Integer::parseInt)
            .put(Long.class, Long::parseLong)
            .put(Double.class, Double::parseDouble)
            .put(Boolean.class, Boolean::parseBoolean)
            .put(Float.class, Float::parseFloat).build();

    public static class ErrorMessage
    {
        public final List<JsonAPIError> errors;
        public final Map<String, Object> meta;
    }

    public static class JsonAPIError
    {
        public final String id;
        public final List<String> links;
        public final Integer status;
        public final String code;
        public final String title;
        public final String detail;
        public final Map<String, Object> meta;
    }
}