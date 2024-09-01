package pe.archety.handlers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.PathTemplateMatch;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.neo4j.graphdb.*;
import pe.archety.*;

import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.logging.Logger;

import static pe.archety.ArchetypeConstants.EMAIL_VALIDATOR;
import static pe.archety.ArchetypeConstants.PHONE_UTIL;
import static pe.archety.ArchetypeConstants.URLPREFIX;
import static pe.archety.ArchetypeConstants.HTTP_CLIENT;
import static pe.archety.ArchetypeConstants.BATCH_WRITER_SERVICE;

public class CreateLikesOrHatesHandler implements HttpHandler {
    private static final Logger logger = Logger.getLogger(CreateLikesOrHatesHandler.class.getName());
    private static GraphDatabaseService graphDB;
    private static ObjectMapper objectMapper;
    private String relationshipTypeName;

}