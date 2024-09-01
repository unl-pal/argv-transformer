package pe.archety.handlers.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.Undertow;
import io.undertow.server.RoutingHandler;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.test.TestGraphDatabaseFactory;
import pe.archety.ArchetypeConstants;
import pe.archety.ArchetypeServer;
import pe.archety.Labels;
import pe.archety.Relationships;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static pe.archety.ArchetypeServer.JSON_UTF8;

public class GetLikesOrHatesHandlerTest {
    private static GraphDatabaseService db;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static Undertow undertow;
    private static final JerseyClient client = JerseyClientBuilder.createClient();

    public static final HashMap<String, Object> identity1 =
            new HashMap<String, Object>() {{
                put( "email", "maxdemarzi@gmail.com" );
            }};

    public static final ArrayList<HashMap<String, String>> likes1Response = new ArrayList<HashMap<String, String>>(){{
        add( new HashMap<String, String>() {{
                put( "title", "Neo4j");
                put( "url", "http://en.wikipedia.org/wiki/Neo4j" );
             }}
        );
    }};

    public static final ArrayList<HashMap<String, String>> hates1Response = new ArrayList<HashMap<String, String>>(){{
        add( new HashMap<String, String>() {{
                put( "title", "Mongodb");
                put( "url", "http://en.wikipedia.org/wiki/Mongodb" );
             }}
        );
    }};

    public static final HashMap<String, Object> identity2 =
            new HashMap<String, Object>() {{
                put( "phone", "+13125137509" );
            }};

    public static final ArrayList<HashMap<String, String>> likes2Response = new ArrayList<>();


    public static final HashMap<String, Object> identity3 =
            new HashMap<String, Object>() {{
                put( "email", "idont@exist.com" );
            }};

    public static final HashMap<String, Object> identity4 =
            new HashMap<String, Object>() {{
                put( "email", "max@maxdemarzi.com" );
            }};

    public static final ArrayList<HashMap<String, String>> likes3Response = new ArrayList<HashMap<String, String>>(){{
        add( new HashMap<String, String>() {{
                 put( "title", "Neo4j");
                 put( "url", "http://en.wikipedia.org/wiki/Neo4j" );
             }}
        );
        add( new HashMap<String, String>() {{
                 put( "title", "Mongodb");
                 put( "url", "http://en.wikipedia.org/wiki/Mongodb" );
             }}
        );

    }};

    public static final HashMap<String, Object> identityWithInvalidEmail =
            new HashMap<String, Object>() {{
                put( "email", "@boo" );
            }};

    public static final HashMap<String, Object> errorInvalidEmailResponse =
            new HashMap<String, Object>() {{
                put( "error", "Email not valid." );
            }};

    public static final HashMap<String, Object> identityWithInvalidEmail2 =
            new HashMap<String, Object>() {{
                put( "email", "booATboo.com" );
            }};

    public static final HashMap<String, Object> errorInvalidEmailResponse2 =
            new HashMap<String, Object>() {{
                put( "error", "Error Parsing Phone Number." );
            }};


    public static final HashMap<String, Object> identityWithInvalidPhoneNumber =
            new HashMap<String, Object>() {{
                put( "phone", "000000000" );
            }};

    public static final HashMap<String, Object> errorInvalidPhoneNumberResponse =
            new HashMap<String, Object>() {{
                put( "error", "Invalid Phone Number." );
            }};

}
