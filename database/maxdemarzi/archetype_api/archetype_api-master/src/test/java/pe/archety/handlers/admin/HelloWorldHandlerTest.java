package pe.archety.handlers.admin;

import io.undertow.Undertow;
import io.undertow.server.RoutingHandler;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class HelloWorldHandlerTest {
    private static Undertow undertow;
    private static final JerseyClient client = JerseyClientBuilder.createClient();
}