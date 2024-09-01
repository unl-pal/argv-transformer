package pe.archety.handlers.admin;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.joda.time.DateTime;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.graphdb.schema.Schema;
import org.neo4j.helpers.collection.MapUtil;
import org.neo4j.tooling.GlobalGraphOperations;
import pe.archety.Labels;

import java.util.concurrent.TimeUnit;

import static pe.archety.ArchetypeServer.TEXT_PLAIN;

public class InitializeHandler implements HttpHandler {
    private static final String TITLE = "title";
    GraphDatabaseService db;
}
