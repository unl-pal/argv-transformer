package pe.archety.handlers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.Undertow;
import io.undertow.server.RoutingHandler;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;
import pe.archety.ArchetypeServer;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static pe.archety.ArchetypeServer.JSON_UTF8;

public class CreateTokenHandlerTest {
    private static GraphDatabaseService db;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static Undertow undertow;
    private static final JerseyClient client = JerseyClientBuilder.createClient();

    public static final HashMap<String, Object> identity1 =
            new HashMap<String, Object>() {{
                put( "email", "maxdemarzi@gmail.com" );
            }};

    public static final HashMap<String, Object> identity1Response =
            new HashMap<String, Object>() {{
                put( "identity", "maxdemarzi@gmail.com" );
            }};

    public static final HashMap<String, Object> identity2 =
            new HashMap<String, Object>() {{
                put( "phone", "3125137509" );
            }};

    public static final HashMap<String, Object> identityWithTypo =
            new HashMap<String, Object>() {{
                put( "typoemail", "maxdemarzi@gmail.com" );
            }};

    public static final HashMap<String, Object> errorParsingJSONResponse =
            new HashMap<String, Object>() {{
                put( "error", "Error parsing JSON." );
            }};

    public static final HashMap<String, Object> errorEmailParameterResponse =
            new HashMap<String, Object>() {{
                put( "error", "Email parameter required." );
            }};

}