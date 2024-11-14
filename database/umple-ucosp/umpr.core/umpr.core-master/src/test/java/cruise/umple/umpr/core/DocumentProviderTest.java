package cruise.umple.umpr.core;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import cruise.umple.umpr.core.fixtures.MockModule;

import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

/**
 * Test {@link RealDocumentFactory}.
 */
@Guice(modules = MockModule.class)
public class DocumentProviderTest {

    @Inject
    private DocumentFactory docProv;

    private static final String ZOO_URL = "http://www.emn.fr/z-info/atlanmod/index.php/Ecore";
    private static File ZOO_FILE;
}
