package pe.archety.handlers.admin;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.joda.time.DateTime;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.tooling.GlobalGraphOperations;
import pe.archety.*;

import java.net.URLEncoder;
import static pe.archety.ArchetypeConstants.URLPREFIX;
import static pe.archety.ArchetypeServer.TEXT_PLAIN;

public class WikipediaHandler implements HttpHandler {

    GraphDatabaseService db;
    private static final String URL = "url";
    private static final String TITLE = "title";
}
