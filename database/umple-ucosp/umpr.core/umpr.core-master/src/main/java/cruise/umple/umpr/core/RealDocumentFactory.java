package cruise.umple.umpr.core;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.inject.Inject;

/**
 * Implementation of {@link DocumentFactory}.
 */
public class RealDocumentFactory implements DocumentFactory {

    @Inject
    private Logger logger;
}
