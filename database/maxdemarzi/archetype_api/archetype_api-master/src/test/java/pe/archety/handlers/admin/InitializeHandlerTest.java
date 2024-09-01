package pe.archety.handlers.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import pe.archety.Labels;
import pe.archety.Relationships;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class InitializeHandlerTest {
    private static GraphDatabaseService db;
    private static Undertow undertow;
    private static final JerseyClient client = JerseyClientBuilder.createClient();
}