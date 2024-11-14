package cruise.umple.umpr.core.repositories;

import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cruise.umple.compiler.UmpleImportType;
import cruise.umple.umpr.core.DiagramType;
import cruise.umple.umpr.core.DocumentFactory;
import cruise.umple.umpr.core.ImportAttrib;
import cruise.umple.umpr.core.License;
import cruise.umple.umpr.core.Repository;
import cruise.umple.umpr.core.entities.ImportEntity;
import cruise.umple.umpr.core.entities.ImportEntityFactory;
import cruise.umple.umpr.core.util.Networks;

import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple2;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * {@link Repository} representation for the Zoo repository on 
 * {@link http://www.emn.fr/z-info/atlanmod/index.php/Ecore AtlanMod}. 
 */
@Singleton
class AtlanZooRepository extends SimpleRepository implements Repository {

    @Remote
    private final static String REPO_URL = "http://www.emn.fr/z-info/atlanmod/index.php/Ecore";
    
    @Name
    private final static String REPO_NAME = "AtlanZooEcore";
    
    @Description
    private final static String REPO_DESC = "The Metamodel Zoos are a collaborative open source research effort intended to produce experimental "
        + "material that may be used by all in the domain of Model Driven Engineering.\n"
        + "This Repository uses the eCore version located at: http://www.emn.fr/z-info/atlanmod/index.php/Ecore.";

    @DType
    private final static DiagramType REPO_DTYPE = DiagramType.CLASS;
    
    @CLicense
    private final static License REPO_LICENSE = License.CC_ATTRIBUTION_4;
    
    private final Logger logger;
    private final DocumentFactory documentFactory;
    private final ImportEntityFactory entityFactory;

    
}
