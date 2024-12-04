import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import io.netty.channel.ChannelHandlerContext;
import org.junit.Test;
import org.rakam.server.http.*;
import org.rakam.server.http.annotations.*;

import javax.inject.Named;
import javax.ws.rs.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class HttpServerTest {

    //    @Test
    @Path("/")
    public static class ParameterHttpService extends HttpService {
    }

    public class SimpleWebhookService extends WebSocketService {
        private String id;
    }

    @Path("/")
    public static class SimpleHttpService extends HttpService {
        public static class DemoBean {
            public final String test;
        }
    }
}
