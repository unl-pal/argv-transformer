package pe.archety.handlers.admin;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import static pe.archety.ArchetypeServer.TEXT_PLAIN;

public class HelloNameHandler implements HttpHandler {
}


/*
        If you set the 'rewriteQueryParameters' option to true in the RoutingHandler
        then they will be available in the exchange query parameters.

        Otherwise they can be retrieved from the exchange as an attachment under
        the io.undertow.util.PathTemplateMatch.ATTACHMENT_KEY key.

*/
