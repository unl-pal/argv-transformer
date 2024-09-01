package pe.archety.handlers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.Undertow;
import io.undertow.server.RoutingHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;

import org.glassfish.jersey.client.*;
import pe.archety.ArchetypeServer;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.HashMap;

import static pe.archety.ArchetypeServer.JSON_UTF8;

import static org.junit.Assert.assertEquals;

public class CreateIdentityHandlerTest {
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

    public static final HashMap<String, Object> identity2Response =
            new HashMap<String, Object>() {{
                put( "identity", "+13125137509" );
            }};

    public static final HashMap<String, Object> identity3 =
            new HashMap<String, Object>() {{
                put( "phone", "3125137509" );
                put( "region", "US");
            }};

    public static final HashMap<String, Object> identity3Response =
            new HashMap<String, Object>() {{
                put( "identity", "+13125137509" );
            }};

    public static final HashMap<String, Object> identityWithTypo =
            new HashMap<String, Object>() {{
                put( "typoemail", "maxdemarzi@gmail.com" );
            }};

    public static final HashMap<String, Object> identityWithBadPhoneNumber =
            new HashMap<String, Object>() {{
                put( "phone", "garbage" );
            }};

    public static final HashMap<String, Object> identityWithInvalidPhoneNumber =
            new HashMap<String, Object>() {{
                put( "phone", "000000000" );
            }};

    public static final HashMap<String, Object> errorParsingJSONResponse =
            new HashMap<String, Object>() {{
                put( "error", "Error parsing JSON." );
            }};

    public static final HashMap<String, Object> errorBadParametersResponse =
            new HashMap<String, Object>() {{
                put( "error", "Parameters email or phone required." );
            }};

    public static final HashMap<String, Object> errorBadPhoneNumberResponse =
            new HashMap<String, Object>() {{
                put( "error", "Error Parsing Phone Number." );
            }};

    public static final HashMap<String, Object> errorInvalidPhoneNumberResponse =
            new HashMap<String, Object>() {{
                put( "error", "Invalid Phone Number." );
            }};
}
